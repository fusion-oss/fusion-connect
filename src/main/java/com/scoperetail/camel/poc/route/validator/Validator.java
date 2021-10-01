package com.scoperetail.camel.poc.route.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ValidationException;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Validator extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("direct:validate")
        .choice()
        .when()
        .simple("${exchangeProperty.validationEnabled} == true")
        // Do Validation
        .doTry()
        .toD("${exchangeProperty.validatorUri}")
        .doCatch(ValidationException.class)
        .log("Validation Failed - ${body}")
        .toD("${exchangeProperty.onValidationFailureUri}")
        .end();
  }
}
