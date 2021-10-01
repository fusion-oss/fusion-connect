package com.scoperetail.camel.poc.route.transform;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Transformer extends RouteBuilder {
  @Override
  public void configure() throws Exception {
    from("direct:transform")
        .choice()
        .when()
        .simple("${exchangeProperty.transformationRequired} == true")
        .choice()
        .when()
        .simple("${exchangeProperty.fusion.FORMAT} == 'json'")
        .to("direct:jsonTransformer")
        .when()
        .simple("${exchangeProperty.fusion.FORMAT} == 'xml'")
        .to("direct:xmlTransformer");

    from("direct:jsonTransformer")
        .unmarshal()
        .json(JsonLibrary.Jackson, Map.class)
        .toD("${exchangeProperty.transformerTemplateUri}")
        .log("Json Transformation Completed Successfully");
    // TODO - Add XML Transformation route
    from("direct:xmlTransformer").log("XML Transformation not available");
  }
}
