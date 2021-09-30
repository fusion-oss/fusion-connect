package com.scoperetail.camel.poc.route.builder;

import com.scoperetail.camel.poc.config.FusionConfig;
import com.scoperetail.camel.poc.config.Source;
import com.scoperetail.camel.poc.route.header.ComputeHeader1;
import com.scoperetail.camel.poc.route.validator.Validator;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

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
          .log("countryCode  : ${exchangeProperty.countryCode}").bean(Validator.class);
    }
  }
}
