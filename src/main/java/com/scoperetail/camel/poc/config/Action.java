package com.scoperetail.camel.poc.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;


@Getter
@Setter
@ToString
public class Action {
//  private Actions actions;
  private Map<String, Object> actions;
}
