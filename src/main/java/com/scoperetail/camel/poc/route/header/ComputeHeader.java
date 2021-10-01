package com.scoperetail.camel.poc.route.header;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.scoperetail.camel.poc.config.Event;
import com.scoperetail.camel.poc.config.FusionConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ComputeHeader {
  @Autowired private FusionConfig config;

  public void process(Message message, Exchange exchange) {
    // Get Required Headers
    final String format = exchange.getProperty("fusion.FORMAT", String.class);
    final String eventType = exchange.getProperty("fusion.EVENT_TYPE", String.class);
    // TODO: If above is not found/empty/null throw exception and stop

    // load header definition from the config object
    // Step:1 find by eventType
    Event eventConfig = config.withEventType(eventType);
    Map<String, String> buildConfigLookupKey = new HashMap<>();
    // Add all headers
    if (format.equals("json")) {
      final DocumentContext documentContext = JsonPath.parse(message.getBody().toString());
      eventConfig
          .getHeader()
          .forEach(
              (k, v) -> {
                String value = v;
                if (v.startsWith("$")) {
                  value = documentContext.read(v);
                }
                exchange.setProperty(k, value);
//                buildConfigLookupKey.put(k, value);
              });
      //
      if (buildConfigLookupKey.containsKey("configLookupKey")) {
        // TODO: parse and build key at runtime, for now hard coding in yaml
        /**
         * in yaml define like this and parse and build on the fly configLookupKey:
         * const('orderDrop').header('nodeId')
         */
      }
    }
  }
}
