package com.scoperetail.camel.poc.config;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Configuration
@ConfigurationProperties(prefix = "fusion")
@Getter
@Setter
@ToString
public class FusionConfig {
  private List<Source> sources;
  private List<Event> events;

  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private Map<String, Event> eventMap;

  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private final MultiKeyMap<String, Set<Event>> eventsBySourceAndFormatMap = new MultiKeyMap<>();

  @PostConstruct
  private void populateEventMap() {
    eventMap = events.stream().collect(Collectors.toMap(Event::getEventType, Function.identity()));
    for (final Event event : events) {
      final Map<String, String> spec = event.getSpec();
      final String sourcekey = spec.get("source");
      final String formatKey = spec.get("format").toUpperCase();
      Set<Event> eventsBySourceAndFormat =
          eventsBySourceAndFormatMap.get(sourcekey, formatKey, new HashSet<>());
      if (eventsBySourceAndFormat == null) {
        eventsBySourceAndFormat = new HashSet<>();
        eventsBySourceAndFormatMap.put(sourcekey, formatKey, eventsBySourceAndFormat);
      }
      eventsBySourceAndFormat.add(event);
    }
  }

  public Event withEventType(final String eventType) {
    return eventMap.get(eventType);
  }

  public Set<Event> getEvents(final String sourceName, final String format) {
    return eventsBySourceAndFormatMap.get(sourceName, format);
  }
}
