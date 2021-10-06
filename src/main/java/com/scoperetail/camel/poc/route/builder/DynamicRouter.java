package com.scoperetail.camel.poc.route.builder;

import static com.scoperetail.camel.poc.util.Constant.SOURCE_NAME;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.scoperetail.camel.poc.config.FusionConfig;
import com.scoperetail.camel.poc.config.Source;
import com.scoperetail.camel.poc.route.event.EventFinder;
import com.scoperetail.camel.poc.route.header.BuildActionProfile;
import com.scoperetail.camel.poc.route.header.ComputeHeader;

@Component
public class DynamicRouter {
  @Autowired private CamelContext camelContext;
  @Autowired private FusionConfig config;

  @PostConstruct
  public void init() throws Exception {
    final List<Source> sources = config.getSources();
    for (final Source source : sources) {
      camelContext.addRoutes(new DynamicRouteBuilder(camelContext, source));
    }
  }

  private static final class DynamicRouteBuilder extends RouteBuilder {
    private final Source source;

    public DynamicRouteBuilder(final CamelContext camelContext, final Source source) {
      super(camelContext);
      this.source = source;
    }

    @Override
    public void configure() {

      from(source.getUri())
          .setProperty(SOURCE_NAME, simple(source.getName()))
          .bean(EventFinder.class) //find event
          .bean(ComputeHeader.class) // compute header
          .bean(BuildActionProfile.class)
          .log("${exchangeProperty.actionExecution}")
          .choice()
          .when(simple("${exchangeProperty.actionExecution} == 'sequence'"))
          .log("Exceuting actions sequentially")
          .recipientList(simple("${exchangeProperty.actions}"))
          .end()
          .otherwise()
          .log("Exceuting actions in parallel")
          .multicast()
          .parallelProcessing()
          .recipientList(simple("${exchangeProperty.actions}"))
          .end();
    }
  }
}
