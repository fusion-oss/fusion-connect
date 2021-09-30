package com.scoperetail.camel.poc.route.builder;

import com.scoperetail.camel.poc.config.FusionConfig;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DynamicRouteBuilder {
  @Autowired private CamelContext camelContext;

  @Autowired private FusionConfig config;

  @PostConstruct
  public void init() throws Exception {
//    camelContext.addRoutes(new MyDynamcRouteBuilder(camelContext, config));
  }

  private static final class MyDynamcRouteBuilder extends RouteBuilder {
    private final FusionConfig config;

    public MyDynamcRouteBuilder(final CamelContext camelContext, final FusionConfig config) {
      super(camelContext);
      this.config = config;
    }

    @Override
    public void configure() throws Exception {
      from("kafka:test?brokers=localhost:9092").log("Message received from Kafka : ${body}");
    }
  }
}
