package com.scoperetail.camel.poc.route.header;

import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.commons.lang3.StringUtils;
import com.scoperetail.camel.poc.config.Event;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BuildActionProfile {

  public void build(final Exchange exchange) {
    final String configLookupKey = exchange.getProperty("configLookupKey", String.class);
    final Event event = exchange.getProperty("event", Event.class);
    final Map<String, Map<String, Object>> configSpec = event.getConfigSpec();
    final Map<String, Object> actions = configSpec.get("default");
    if (StringUtils.isNotBlank(configLookupKey) && !"default".equals(configLookupKey)) {
      actions.putAll(configSpec.get(configLookupKey));
    }
    actions.forEach(exchange::setProperty);
  }
}
