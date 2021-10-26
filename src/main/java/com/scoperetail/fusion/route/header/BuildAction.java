package com.scoperetail.fusion.route.header;

import org.apache.camel.Exchange;
import org.apache.commons.lang3.StringUtils;

public class BuildAction {
  public void build(final Exchange exchange) {
    final String actions = exchange.getProperty("actions", String.class);
    int actionCount = 0;
    if (StringUtils.isNotBlank(actions)) {
      final String[] split = actions.split(",");
      actionCount = split.length;
      int index = 0;
      for (final String s : split) {
        exchange.setProperty("action_" + index++, s);
      }
    }
    exchange.setProperty("actionCount", actionCount);
  }
}
