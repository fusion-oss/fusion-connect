package com.scoperetail.camel.poc.route.builder;

import com.scoperetail.camel.poc.config.FusionConfig;
import com.scoperetail.camel.poc.config.Source;
import com.scoperetail.camel.poc.route.template.KafkaToAMQTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class DynamicRouteBuilder2 {
  @Autowired private CamelContext camelContext;

  @Autowired private FusionConfig config;
  @Autowired private KafkaToAMQTemplate template;

//  @PostConstruct
  public void test() {
    log.info("2");
  }

//  @PostConstruct
  public void init() throws Exception {
    camelContext.start();
    final List<Source> sources = config.getSources();
    for (Source source : sources) {
      Map<String, Object> params =
          Map.of(
              "uri",
              source.getUri(),
              "format",
              source.getSpec().getFormat(),
              "eventTypePath",
              source.getSpec().getEventTypePath());
      camelContext.addRouteFromTemplate("kafkaRouteId", "k", params);
      /* TemplatedRouteBuilder.builder(camelContext, "kafkaToAMQRouteTemplate")
      .parameter("uri", source.getUri())
      .parameter("format", source.getSpec().getFormat())
      .parameter("eventTypePath", source.getSpec().getEventTypePath())
      .add();*/
    }
  }
}
