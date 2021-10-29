package com.scoperetail.fusion.config;

import java.nio.charset.StandardCharsets;
import org.apache.camel.component.kafka.serde.KafkaHeaderDeserializer;

public class ByteToStringHeaderDeserializerCustom implements KafkaHeaderDeserializer {
  public ByteToStringHeaderDeserializerCustom() {}

  @Override
  public Object deserialize(final String key, final byte[] value) {

    return new String(value, StandardCharsets.UTF_8);
  }
}
