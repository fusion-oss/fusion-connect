package com.scoperetail.fusion.route.audit;

import org.apache.camel.LoggingLevel;
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
        .log(LoggingLevel.DEBUG, "Message sent to Auditor")
        .end();
  }
}
