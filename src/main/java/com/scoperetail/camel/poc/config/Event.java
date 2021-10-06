package com.scoperetail.camel.poc.config;

import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "eventType")
public class Event {
  private String eventType;
  private Map<String, String> spec;
  private Map<String, String> header;
  private Map<String, Map<String, Object>> configSpec;
}
