package com.scoperetail.fusion.route.builder;

import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.scoperetail.fusion.config.FusionConfig;
import com.scoperetail.fusion.config.Source;
import com.scoperetail.fusion.route.event.EventFinder;
import com.scoperetail.fusion.route.header.BuildActionProfile;
import com.scoperetail.fusion.route.header.ComputeHeader;

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
          .setProperty("source", constant(source))
          .bean(EventFinder.class)
          .choice()
          .when(simple("${exchangeProperty.event} == null"))
          .log("Stopping the route as event not found")
          .toD("${exchangeProperty.source.bo}")
          .stop()
          .end()
          .bean(ComputeHeader.class)
          .bean(BuildActionProfile.class)
          .log("${exchangeProperty.actionExecution}")
          .choice()
          .when(simple("${exchangeProperty.actionExecution} == 'sequence'"))
          .log("Exceuting actions sequentially")
          .recipientList(simple("${exchangeProperty.actions}"))
          .aggregationStrategy(
              (oldExchange, newExchange) -> {
                if (oldExchange == null) {
                  return newExchange;
                }
                newExchange.getAllProperties().forEach(oldExchange::setProperty);
                return oldExchange;
              })
          .choice()
          .when(simple("${exchangeProperty.isValidMessage} == true"))
          .recipientList(simple("${exchangeProperty.targetUri}"))
          .end()
          .end();
    }
  }
}
