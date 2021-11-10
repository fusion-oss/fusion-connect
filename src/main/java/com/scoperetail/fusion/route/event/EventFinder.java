package com.scoperetail.fusion.route.event;

/*-
 * *****
 * fusion-connect
 * -----
 * Copyright (C) 2018 - 2021 Scope Retail Systems Inc.
 * -----
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * =====
 */

import static com.scoperetail.fusion.common.Format.JSON;
import static com.scoperetail.fusion.common.Format.PLAIN_TEXT;
import static com.scoperetail.fusion.common.Format.XML;
import java.util.Map;
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
    final Map<String, Object> headers = message.getHeaders();

    // Default values
    Boolean validHeaders = true;
    String[] mandatoryHeaders = {
            "eventType",
            "messageId",
            "correlationId",
            "facilityNum",
            "facilityCountryCode"
    };
    String defaultFacilityNum = "2142";
    String defaultFacilityCountryCode = "US";

    // Check mandatory headers
    for (String header : mandatoryHeaders) {
      if (!headers.containsKey(header)) {
        validHeaders = false;
        log.error("Missing header: {}", header);
      }
    }

    // Check supported values
    if (validHeaders) {
      String facilityNum = (String) headers.get("facilityNum");
      String facilityCountryCode = (String) headers.get("facilityCountryCode");

      if (!facilityNum.equals(defaultFacilityNum)) {
        log.error("Unsupported facilityNum: {}", facilityNum);
        validHeaders = false;
      }

      if (!facilityCountryCode.equals(defaultFacilityCountryCode)) {
        log.error("Unsupported facilityCountryCode: {}", facilityCountryCode);
        validHeaders = false;
      }
    }

    if (validHeaders) {
      final String payload = message.getBody(String.class).trim();
      final Format payloadFormat = getPayloadFormat(payload);
      final Source source = exchange.getProperty("source", Source.class);
      final Set<Event> events = fusionConfig.getEvents(source.getName(), payloadFormat.name());
      final Event event = eventMatcher.getEvent(headers, payload, payloadFormat, events);
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
