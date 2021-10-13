package com.scoperetail.fusion.route.dedupe;

import static org.apache.camel.support.builder.PredicateBuilder.and;
import static org.apache.camel.support.builder.PredicateBuilder.not;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class DeDupeRoute extends RouteBuilder {
  @Override
  public void configure() throws Exception {
    from("direct:dedupe")
        .log("DEDUPE START")
        .log("${exchangeProperty.idempotencyRequired}")
        .log("${exchangeProperty.isValidMessage}")
        .choice()
        .when(
            and(
                simple("${exchangeProperty.idempotencyRequired}"),
                exchangeProperty("isValidMessage")))
        .log("Checking for duplicate message")
        .bean(DeDupeFinder.class)
        .choice()
        .when(simple("${exchangeProperty.isDuplicate}"))
        .log("Duplicate message detected")
        .choice()
        .when(not(simple("${exchangeProperty.continueOnDuplicate}")))
        .log("Stopping message flow as continueOnDuplicate property is set to false")
        .stop()
        .end() //continue on duplicate
        .end()
        .log("DEDUPE END");
  }
}
