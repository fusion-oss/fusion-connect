package com.scoperetail.camel.poc.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Idempotence {
    private boolean enabled;
    private String keyExpression;
    private boolean continueOnDuplicate;
}
