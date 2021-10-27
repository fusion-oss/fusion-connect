package com.scoperetail.fusion.route.validator;

/*-
 * *****
 * fusion-connect
 * -----
 * Copyright (C) 2018 - 2021 Scope Retail Systems Inc.
 * -----
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * =====
 */

import static org.apache.camel.LoggingLevel.DEBUG;
import static org.apache.camel.LoggingLevel.ERROR;
import static org.apache.camel.LoggingLevel.INFO;
import org.apache.camel.ValidationException;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Validator extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("direct:validate")
        .choice()
        .when(simple("${exchangeProperty.validatorUri} == null"))
        .log(ERROR, "Validation URI is not provided so stopping the routes.")
        .stop()
        .otherwise()
        .doTry()
        .log(
            DEBUG,
            "Validating message with the schema provided at path: ${exchangeProperty.validatorUri}")
        .toD("${exchangeProperty.validatorUri}")
        .log(DEBUG, "Message Validated successfully")
        .doCatch(ValidationException.class)
        .log(
            ERROR,
            "Validation Failed - Sending message to URI: "
                + "${exchangeProperty.onValidationFailureUri} ")
        .toD("${exchangeProperty.onValidationFailureUri}")
        .log(INFO, "Stopping the routes due to validation failure.")
        .stop()
        .end();
  }
}
