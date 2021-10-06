package com.scoperetail.camel.poc.route.event;

import static com.scoperetail.camel.poc.common.Format.JSON;
import static com.scoperetail.camel.poc.common.Format.XML;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.scoperetail.camel.poc.common.Format;
import com.scoperetail.camel.poc.config.Event;

@Component
public class EventMatcher {

  @Autowired private JsonEventMatcher jsonEventMatcher;
  @Autowired private XmlEventMatcher xmlEventMatcher;

  public Event getEvent(final String payload, final Format payloadFormat, final Set<Event> events) {
    boolean isMatch = false;
    Event matchedEvent = null;
    for (final Event event : events) {
      final String eventTypePath = event.getSpec().get("eventTypePath");
      if (JSON.equals(payloadFormat)) {
        isMatch = jsonEventMatcher.match(payload, eventTypePath);
      } else if (XML.equals(payloadFormat)) {
        isMatch = xmlEventMatcher.match(payload, eventTypePath);
      }
      if (isMatch) {
        matchedEvent = event;
        break;
      }
    }
    return matchedEvent;
  }
}
