package com.scoperetail.fusion.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class FilerCriteria {
  String expression;
  List<String> values;
}
