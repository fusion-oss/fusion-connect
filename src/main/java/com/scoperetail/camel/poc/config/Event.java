package com.scoperetail.camel.poc.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class Event {
  private String eventType;
  private Map<String, String> header;
//  private Map<String, Action> spec;
  private Map<String, Map<String, Object>> spec;
}
