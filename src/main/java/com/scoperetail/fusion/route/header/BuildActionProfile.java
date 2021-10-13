package com.scoperetail.fusion.route.header;

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

import static com.scoperetail.fusion.util.Constant.UNDERSCORE;
import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.commons.lang3.StringUtils;
import com.scoperetail.fusion.config.Event;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BuildActionProfile {

  public void build(final Exchange exchange) {
    final String configLookupKey = exchange.getProperty("configLookupKey", String.class);
    final Event event = exchange.getProperty("event", Event.class);
    final Map<String, Map<String, Object>> configSpec = event.getConfigSpec();
    final Map<String, Object> actions = configSpec.get("default");
    if (StringUtils.isNotBlank(configLookupKey) && !"default".equals(configLookupKey)) {
      final String key = event.getEventType() + UNDERSCORE + configLookupKey;
      log.debug("Overriding default actions using the config look up key: {}", key);
      actions.putAll(configSpec.get(key));
    }
    actions.forEach(exchange::setProperty);
  }
}
