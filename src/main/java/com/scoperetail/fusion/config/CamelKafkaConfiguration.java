package com.scoperetail.fusion.config;

import org.apache.camel.component.kafka.serde.KafkaHeaderDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelKafkaConfiguration {

  @Bean
  public KafkaHeaderDeserializer byteToStringHeaderDeserializerCustom() {

    return new ByteToStringHeaderDeserializerCustom();
  }
}
