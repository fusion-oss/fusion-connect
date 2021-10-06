package com.scoperetail.camel.poc.route.event;

import static com.scoperetail.camel.poc.util.Constant.SOURCE_NAME;
import java.util.Objects;
import java.util.Set;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.springframework.beans.factory.annotation.Autowired;
import com.scoperetail.camel.poc.common.Format;
import com.scoperetail.camel.poc.config.Event;
import com.scoperetail.camel.poc.config.FusionConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventFinder {

  @Autowired private FusionConfig fusionConfig;
  @Autowired private EventMatcher eventMatcher;

  public void process(final Message message, final Exchange exchange) {
    final String payload = message.getBody(String.class).trim();
    final Character startChar = payload.length() > 0 ? payload.charAt(0) : ' ';
    Format payloadFormat = null;
    switch (startChar) {
      case '{':
      case '[':
        payloadFormat = Format.JSON;
        break;
      case '<':
        payloadFormat = Format.XML;
        break;
      default:
        //TODO : throw InvalidPayloadFormat exception and send message to errorQueue
        break;
    }
    final String sourceName = exchange.getProperty(SOURCE_NAME, String.class);
    final Set<Event> events = fusionConfig.getEvents(sourceName, payloadFormat.name());
    final Event event = eventMatcher.getEvent(payload, payloadFormat, events);
    if (Objects.nonNull(event)) {
      log.debug(
          "Setting the exchange property for the eventType: {}", payload, event.getEventType());
      exchange.setProperty("event", event);
    }
  }
}
