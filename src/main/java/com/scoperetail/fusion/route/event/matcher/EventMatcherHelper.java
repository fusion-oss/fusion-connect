package com.scoperetail.fusion.route.event.matcher;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.scoperetail.fusion.common.Format;
import com.scoperetail.fusion.config.Event;

@Component
public class EventMatcherHelper {

  @Autowired private JsonEventMatcher jsonEventMatcher;
  @Autowired private XmlEventMatcher xmlEventMatcher;

  public Event getEvent(final String payload, final Format payloadFormat, final Set<Event> events) {
    final EventMatcher eventMatcher =
        payloadFormat.equals(Format.JSON) ? jsonEventMatcher : xmlEventMatcher;
    boolean isMatch = false;
    Event matchedEvent = null;
    for (final Event event : events) {
      final String eventTypePath = event.getSpec().get("eventTypePath");
      isMatch = eventMatcher.match(event.getEventType(), eventTypePath, payload);
      if (isMatch) {
        matchedEvent = event;
        break;
      }
    }
    return matchedEvent;
  }
}
