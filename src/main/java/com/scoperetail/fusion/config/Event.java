package com.scoperetail.fusion.config;

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
  private Map<String, Object> headers;
  private Map<String, Map<String, Object>> configSpec;
}
