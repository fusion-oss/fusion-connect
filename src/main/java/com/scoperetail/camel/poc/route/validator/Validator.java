package com.scoperetail.camel.poc.route.validator;

import com.scoperetail.camel.poc.config.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Slf4j
public class Validator {
  @Autowired private FusionConfig config;

  public void process(Message message, Exchange exchange) {
    final String configLookupKey = exchange.getProperty("configLookupKey", String.class);
    final String eventType = exchange.getProperty("fusion.EVENT_TYPE", String.class);
    Event eventConfig = config.withEventType(eventType);

    // TODO: Optimize data structure as Map for efficient lookup by key

    // Get Default Validate Action
    Action action = eventConfig.getSpec().get("default");
    Actions actions = action.getActions();
    Validate defaultValidateAction = actions.getValidate();

    // Get Validate Action against lookupkey
    Validate validateAction = eventConfig.getSpec().get(configLookupKey).getActions().getValidate();
    if (Objects.isNull(validateAction)) {
      // Try to fallback on default
      validateAction = eventConfig.getSpec().get("default").getActions().getValidate();
    }
    //Are there any Validate Action to be performed
    if (Objects.nonNull(validateAction)) {
      doValidation(validateAction, message.getBody().toString());
    }
  }

  private void doValidation(final Validate validateAction, final String toString) {
    //TODO:
    /**
     * Load XSD or JSD and perform corresponding validation
     */
  }
}
