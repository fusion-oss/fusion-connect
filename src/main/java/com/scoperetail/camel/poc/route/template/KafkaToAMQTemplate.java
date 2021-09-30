package com.scoperetail.camel.poc.route.template;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class KafkaToAMQTemplate extends RouteBuilder {

  @PostConstruct
  public void test(){
    log.info("1");
  }
  @Override
  public void configure() throws Exception {
    // create a route template with the given name
    routeTemplate("k")
        // here we define the required input parameters (can have default values)
        .templateParameter("uri")
        .templateParameter("format")
        .templateParameter("eventTypePath")

        // Build Route Template
        .from("kafka:{{uri}}")
        .log("Message received from Kafka : ${body}")
        /*.choice()
        .when()
        .simple("{{format}} == 'json'")
        .setHeader("fusion.EVENT_TYPE")
        .jsonpath("{{eventTypePath}}")*/
        .log("Message Header  : ${headers}");

  }
}
