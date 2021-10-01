package com.scoperetail.camel.poc.route.builder;

import com.scoperetail.camel.poc.config.FusionConfig;
import com.scoperetail.camel.poc.config.Source;
import com.scoperetail.camel.poc.route.header.BuildActionProfile;
import com.scoperetail.camel.poc.route.header.ComputeHeader1;
import org.apache.camel.CamelContext;
import org.apache.camel.ValidationException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Component
public class DynamicRouteBuilder4 {
  @Autowired private CamelContext camelContext;

  @Autowired private FusionConfig config;

  @PostConstruct
  public void init() throws Exception {
    final List<Source> sources = config.getSources();
    for (Source source : sources) {
      camelContext.addRoutes(new MyDynamcRouteBuilder(camelContext, config, source));
    }
  }

  private static final class MyDynamcRouteBuilder extends RouteBuilder {
    private final FusionConfig config;
    private final Source source;

    public MyDynamcRouteBuilder(
        final CamelContext camelContext, final FusionConfig config, final Source source) {
      super(camelContext);
      this.config = config;
      this.source = source;
    }

    @Override
    public void configure() throws Exception {

      RouteDefinition rd = new RouteDefinition();
      if (source.getType().equals("kafka")) {
        rd = from(source.getUri()).log("Message received from Kafka : ${body}");
      }

      //
      if (source.getSpec().getFormat().equals("json")) {
        rd.setProperty("fusion.FORMAT", constant("json"))
            .setProperty("fusion.EVENT_TYPE")
            .jsonpath(source.getSpec().getEventTypePath())
            .log("fusion.FORMAT  : ${exchangeProperty.fusion.FORMAT}");
      } else if (source.getSpec().getFormat().equals("xml")) {
        // TODO
      }
      // Compute Header Keys after receiving message
      rd.bean(ComputeHeader1.class)
          .log("configLookupKey  : ${exchangeProperty.configLookupKey}")
          .log("nodeId  : ${exchangeProperty.nodeId}")
          .log("countryCode  : ${exchangeProperty.countryCode}")
          .bean(BuildActionProfile.class)
          .choice()
          .when()
          .simple("${exchangeProperty.validationEnabled} == true")
          // Do Validation
          // TODO: Check if validation is needed. choice/when or if like above
          .doTry()
          .toD("${exchangeProperty.validatorUri}")
          .doCatch(ValidationException.class)
          .log("Validation Failed - ${body}")
          .toD("${exchangeProperty.onValidationFailureUri}")
          .end()
          //          .endChoice()
          .choice()
          .when()
          .simple("${exchangeProperty.auditEnabled} == true")
          // Do Audit
          .wireTap("${exchangeProperty.auditTargetUri}")
          .end()
          .choice()
          .when()
          .simple("${exchangeProperty.idempotencyRequired} == true")
          .bean(DeDupeFinder.class)
          .choice()
          .when()
          .simple("${exchangeProperty.isDuplicate} == true")
          .log("Duplicate message Detected")
          .choice()
          .when()
          .simple("${exchangeProperty.continueOnDuplicate} == false")
          .log("Stopping message flow as continueOnDuplicate property is set to false")
          .stop()
          .end()
          .choice()
          .when()
          .simple("${exchangeProperty.transformationRequired} == true")
          .unmarshal()
          .json(JsonLibrary.Jackson, Map.class)
          .toD("${exchangeProperty.transformerTemplateUri}")
          .log("Transformation Completed Successfully")
          .endChoice()
          .recipientList(simple("${exchangeProperty.targetUri}"));
    }
  }
}
