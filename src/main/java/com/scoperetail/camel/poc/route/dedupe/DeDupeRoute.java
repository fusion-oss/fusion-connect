package com.scoperetail.camel.poc.route.dedupe;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class DeDupeRoute extends RouteBuilder {
  @Override
  public void configure() throws Exception {
    from("direct:dedupe")
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
        .end();
  }
}
