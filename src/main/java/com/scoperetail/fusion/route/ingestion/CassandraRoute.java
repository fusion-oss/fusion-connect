package com.scoperetail.fusion.route.ingestion;

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

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cassandra.CassandraConstants;
import org.springframework.stereotype.Component;

@Component
public class CassandraRoute extends RouteBuilder {
  @Override
  public void configure() throws Exception {

    from("direct:cassandraIngestion")
        .log("Ingestion START")
        .log("Populating CamelCqlQuery header with cql")
        .setHeader(CassandraConstants.CQL_QUERY, simple("${body}"))
        .log("Setting body to null, so that CQl can be read from CamelCqlQuery header")
        .setBody()
        .simple("${null}")
        .toD("${exchangeProperty.targetUri}")
        .log("Ingestion Completed")
        .stop();
  }
}
