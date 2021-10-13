package com.scoperetail.fusion.route.validator;

import static org.apache.camel.LoggingLevel.DEBUG;
import static org.apache.camel.LoggingLevel.ERROR;
import org.apache.camel.ValidationException;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Validator extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("direct:validate")
        .setProperty("isValidMessage", constant(false))
        .choice()
        .when(simple("${exchangeProperty.?validatorUri.isEmpty()}"))
        .log(DEBUG, "Validation URI is not provided so skipping validation")
        .otherwise()
        .doTry()
        .log(DEBUG, "Validating with the schema provided at path: ${exchangeProperty.validatorUri}")
        .toD("${exchangeProperty.validatorUri}")
        .setProperty("isValidMessage", constant(true))
        .log(DEBUG, "Message Validation successful")
        .doCatch(ValidationException.class)
        .log(
            ERROR,
            "Validation Failed - Sending message to URI: "
                + "${exchangeProperty.onValidationFailureUri}")
        .toD("${exchangeProperty.onValidationFailureUri}")
        .stop()
        .end()
        .end();
  }
}
