package com.scoperetail.camel.poc.route.validator;

import com.scoperetail.camel.poc.config.Action;
import com.scoperetail.camel.poc.config.Event;
import com.scoperetail.camel.poc.config.FusionConfig;
import com.scoperetail.camel.poc.config.Validate;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Slf4j
public class Validator {
  @Autowired private FusionConfig config;

//  public void process(Message message, Exchange exchange) {
//    final String configLookupKey = exchange.getProperty("configLookupKey", String.class);
//    final String eventType = exchange.getProperty("fusion.EVENT_TYPE", String.class);
//    Event eventConfig = config.withEventType(eventType);
//
//    // TODO: Optimize data structure as Map for efficient lookup by key
//    Validate validateAction = eventConfig.getSpec().get("default").getActions().getValidate();
//    // Get Validate Action against lookupkey
//    final Action action = eventConfig.getSpec().get(configLookupKey);
//    if (Objects.nonNull(action)
//        && Objects.nonNull(action.getActions())
//        && Objects.nonNull(action.getActions().getValidate())) {
//      //If there is specific defined then override default
//      validateAction = action.getActions().getValidate();
//    }
//
//    // Are there any Validate Action to be performed
//    if (Objects.nonNull(validateAction)) {
//      // Add Validation URI in Exchange
//      exchange.setProperty("fusion.VALIDATE_URI", validateAction.getSchema());
//      exchange.setProperty("fusion.VALIDATE_FAILURE_TARGET", validateAction.getOnFailureTarget());
//    }
//  }
}
