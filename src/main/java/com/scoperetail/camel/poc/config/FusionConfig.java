package com.scoperetail.camel.poc.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@ConfigurationProperties(prefix = "fusion")
@Getter
@Setter
@ToString
public class FusionConfig {
  private List<Source> sources;
  private List<Source> targets;
  private List<Event> events;
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private Map<String, Event> eventMap;

  @PostConstruct
  private void populateEventMap() {
    eventMap = events.stream().collect(Collectors.toMap(Event::getEventType, Function.identity()));
  }

  public Event withEventType(String eventType){
    return eventMap.get(eventType);
  }
}
