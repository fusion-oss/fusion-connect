package com.scoperetail.camel.poc.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Source {
  private String name;
  private String type;
  private String uri;
  private SourceSpec spec;
}
