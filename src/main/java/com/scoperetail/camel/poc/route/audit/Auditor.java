package com.scoperetail.camel.poc.route.audit;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Auditor extends RouteBuilder {
  @Override
  public void configure() throws Exception {
    from("direct:audit")
        .choice()
        .when()
        .simple("${exchangeProperty.auditEnabled} == true")
        // Do Audit
        .wireTap("${exchangeProperty.auditTargetUri}")
        .end();
  }
}
