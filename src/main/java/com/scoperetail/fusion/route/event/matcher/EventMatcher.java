package com.scoperetail.fusion.route.event.matcher;

public interface EventMatcher {
  boolean match(final String eventType, final String expression, final String payload);
}
