package com.scoperetail.camel.poc.route.validator;

import org.apache.camel.ValidationException;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Validator extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("direct:validate")
        .log("VALIDATION START")
        .choice()
        .when()
        .simple("${exchangeProperty.validationEnabled} == true")
        // Do Validation
        .doTry()
        .toD("${exchangeProperty.validatorUri}")
        .doCatch(ValidationException.class)
        .log("Validation Failed - ${body}")
        .toD("${exchangeProperty.onValidationFailureUri}")
        .end()
        .log("VALIDATION END");
  }
}
