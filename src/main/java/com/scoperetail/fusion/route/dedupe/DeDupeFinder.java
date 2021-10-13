package com.scoperetail.fusion.route.dedupe;

import org.apache.camel.Exchange;

public class DeDupeFinder {
    public void isDuplicate(Exchange exchange){
        //Get idempotencyKey from exchange and perform
        exchange.setProperty("isDuplicate", true);
    }
}
