package com.scoperetail.camel.poc.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Actions {
  private Validate validate;
  private Audit audit;
  private Idempotence idempotence;
  private Transform transform;
}
