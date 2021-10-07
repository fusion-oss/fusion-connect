package com.scoperetail.camel.poc.route.validator;

import static org.apache.camel.LoggingLevel.DEBUG;
import static org.apache.camel.LoggingLevel.ERROR;
import org.apache.camel.ValidationException;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import com.networknt.schema.JsonSchemaException;

@Component
public class Validator extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("direct:validate")
        .log("VALIDATION START")
        .choice()
        .when(simple("${exchangeProperty.?validatorUri.isEmpty()}"))
        .log(DEBUG, "Validation URI is not provided so skipping validation")
        .otherwise()
        .doTry()
        .log(DEBUG, "Validating with the schema provided at path: ${exchangeProperty.validatorUri}")
        .toD("${exchangeProperty.validatorUri}")
        .log("Message Validation successful")
        .doCatch(ValidationException.class, JsonSchemaException.class)
        .setProperty("isValidMessage", constant(false))
        .log(ERROR, "Validation Failed - ${body}")
        .toD("${exchangeProperty.onValidationFailureUri}")
        .end()
        .end()
        .log("VALIDATION END");
  }
}
