package com.scoperetail.camel.poc.route.transform;

import java.util.Map;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Transformer extends RouteBuilder {
  Class<Map<String, Object>> clazz;

  @Override
  public void configure() throws Exception {
    from("direct:transform")
        .choice()
        .when()
        .simple("${exchangeProperty.transformationRequired} == true")
        .choice()
        .when()
        .simple("${exchangeProperty.event.format} == 'json'")
        .to("direct:jsonTransformer")
        .when()
        .simple("${exchangeProperty.event.format} == 'xml'")
        .to("direct:xmlTransformer");

    from("direct:jsonTransformer").log("json Transformation : Unsupported Operation");
    //        .log("JSON- Before unmarshal:" + "${body}")
    //        .unmarshal()
    //        .json(JsonLibrary.Jackson, Map.class)
    //        .log("JSON- After unmarshal:" + "${body}");
    // .toD("${exchangeProperty.transformerTemplateUri}")
    //.log("Json Transformation Completed Successfully");

    from("direct:xmlTransformer").log("xml Transformation : Unsupported Operation");
    //        .log("XML- Before unmarshal:" + "${body}")
    //        .unmarshal()
    //        .jacksonxml(clazz)
    //        .log("XML- After unmarshal:" + "${body}")
    // .toD("${exchangeProperty.transformerTemplateUri}")
    //.log("Json Transformation Completed Successfully");
  }
}
