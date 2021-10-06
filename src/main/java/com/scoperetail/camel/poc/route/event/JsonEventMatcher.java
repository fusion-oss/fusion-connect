package com.scoperetail.camel.poc.route.event;

import java.util.Objects;
import org.springframework.stereotype.Component;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

@Component
public class JsonEventMatcher {

  public boolean match(final String payload, final String expression) {
    final DocumentContext documentContext = JsonPath.parse(payload);
    final Object object = documentContext.read(expression);
    return Objects.nonNull(object);
  }
}
