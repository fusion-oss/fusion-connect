package com.scoperetail.camel.poc.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
  private final Map<EventSourceAndFormat, Set<Event>> eventsBySourceAndFormatMap = new HashMap<>();

  @PostConstruct
  private void init() {
    initEventsBySourceAndFormatMap();
  }

  private void initEventsBySourceAndFormatMap() {
    for (final Event event : events) {
      final Map<String, String> spec = event.getSpec();
      final String sourcekey = spec.get("source");
      final String formatKey = spec.get("format").toUpperCase();
      final EventSourceAndFormat eventSourceAndFormatKey =
          new EventSourceAndFormat(sourcekey, formatKey);
      Set<Event> eventsBySourceAndFormat = eventsBySourceAndFormatMap.get(eventSourceAndFormatKey);
      if (eventsBySourceAndFormat == null) {
        eventsBySourceAndFormat = new HashSet<>();
        eventsBySourceAndFormatMap.put(eventSourceAndFormatKey, eventsBySourceAndFormat);
      }
      eventsBySourceAndFormat.add(event);
    }
  }

  public Set<Event> getEvents(final String sourceName, final String format) {
    return eventsBySourceAndFormatMap.getOrDefault(
        new EventSourceAndFormat(sourceName, format), Collections.emptySet());
  }

  @Data
  @EqualsAndHashCode
  @AllArgsConstructor
  class EventSourceAndFormat {
    private String source;
    private String format;
  }
}
