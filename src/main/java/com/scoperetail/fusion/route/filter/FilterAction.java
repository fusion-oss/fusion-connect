package com.scoperetail.fusion.route.filter;

import com.scoperetail.fusion.common.Format;
import com.scoperetail.fusion.config.Event;
import com.scoperetail.fusion.config.FilerCriteria;
import com.scoperetail.fusion.route.event.matcher.EventMatcher;
import com.scoperetail.fusion.route.event.matcher.JsonEventMatcher;
import com.scoperetail.fusion.route.event.matcher.XmlEventMatcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@Slf4j
public class FilterAction {

  @Autowired private JsonEventMatcher jsonEventMatcher;
  @Autowired private XmlEventMatcher xmlEventMatcher;

  public boolean filter(final Exchange exchange) {
    boolean isMatched = true;
    final Event event = exchange.getProperty("event", Event.class);
    final String format = exchange.getProperty("event.format", String.class);
    final EventMatcher eventMatcher =
        format.equals(Format.JSON) ? jsonEventMatcher : xmlEventMatcher;

    if (Objects.nonNull(event.getFilters())) {
      for (FilerCriteria filterCriteria : event.getFilters()) {
        final List<String> filterValues = filterCriteria.getValues();
        isMatched =
            eventMatcher.contains(
                filterValues,
                filterCriteria.getExpression(),
                exchange.getIn().getBody().toString());
        log.info("Filter passed, criteria :: {}", filterValues);
        if (!isMatched) {
          log.info("Stopping the flow as filter criteria :: {} failed", filterValues);
          break;
        }
      }
    }
    return isMatched;
  }
}
