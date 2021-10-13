package com.scoperetail.fusion.route.event.matcher;

import org.springframework.stereotype.Component;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JsonEventMatcher implements EventMatcher {

  @Override
  public boolean match(final String eventType, final String expression, final String payload) {
    boolean isMatched = false;
    final DocumentContext documentContext = JsonPath.parse(payload);
    try {
      final Object object = documentContext.read(expression);
      isMatched = eventType.equalsIgnoreCase(object.toString());
    } catch (final PathNotFoundException e) {
      log.error("Unable to match eventType: {} expression: {}", eventType, expression);
    }
    return isMatched;
  }
}
