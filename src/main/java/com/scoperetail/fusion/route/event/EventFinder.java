package com.scoperetail.fusion.route.event;

import static com.scoperetail.fusion.common.Format.JSON;
import static com.scoperetail.fusion.common.Format.PLAIN_TEXT;
import static com.scoperetail.fusion.common.Format.XML;
import java.util.Objects;
import java.util.Set;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.springframework.beans.factory.annotation.Autowired;
import com.scoperetail.fusion.common.Format;
import com.scoperetail.fusion.config.Event;
import com.scoperetail.fusion.config.FusionConfig;
import com.scoperetail.fusion.config.Source;
import com.scoperetail.fusion.route.event.matcher.EventMatcherHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventFinder {

  @Autowired private FusionConfig fusionConfig;
  @Autowired private EventMatcherHelper eventMatcher;

  public void process(final Message message, final Exchange exchange) {
    final String payload = message.getBody(String.class).trim();
    final Format payloadFormat = getPayloadFormat(payload);
    final Source source = exchange.getProperty("source", Source.class);
    final Set<Event> events = fusionConfig.getEvents(source.getName(), payloadFormat.name());
    final Event event = eventMatcher.getEvent(payload, payloadFormat, events);
    if (Objects.nonNull(event)) {
      log.debug(
          "Event found for source: {} eventType: {} format: {}",
          source.getName(),
          event.getEventType(),
          payloadFormat.name());
      exchange.setProperty("event", event);
      exchange.setProperty("event.format", event.getSpec().get("format"));
    }
  }

  private Format getPayloadFormat(final String payload) {
    final Character startChar = payload.length() > 0 ? payload.charAt(0) : ' ';
    Format payloadFormat = PLAIN_TEXT;
    switch (startChar) {
      case '{':
      case '[':
        payloadFormat = JSON;
        break;
      case '<':
        payloadFormat = XML;
        break;
      default:
        log.error("Unsupported payload format: {}", payloadFormat);
        break;
    }
    return payloadFormat;
  }
}
