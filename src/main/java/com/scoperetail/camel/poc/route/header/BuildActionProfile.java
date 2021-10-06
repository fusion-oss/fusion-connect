package com.scoperetail.camel.poc.route.header;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import com.scoperetail.camel.poc.config.FusionConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BuildActionProfile {
  @Autowired private FusionConfig config;
  @Autowired private Environment env;

  // We can Access Action profile from DB or any external source

  public void build(final Message message, final Exchange exchange) {
    //        final String configLookupKey = exchange.getProperty("configLookupKey", String.class);
    //        final String eventType = exchange.getProperty("fusion.EVENT_TYPE", String.class);
    //        final Event eventConfig = config.withEventType(eventType);
    //        // Check if there is a profile associated with configLookupKey, if not, use default
    //        log.info("eventConfig::{}", eventConfig);
    //        Map<String, Object> actionProfile = eventConfig.getSpec().get("configLookupKey");
    //        if (Objects.isNull(actionProfile)) {
    //          // get default
    //          actionProfile = eventConfig.getSpec().get("default");
    //        }
    //        log.info("actionProfile::{}", actionProfile);
    //        // Set in exchange
    //        actionProfile.forEach(exchange::setProperty);
    //        log.info("targetUri::{}", exchange.getProperty("targetUri", String.class));
  }
}
