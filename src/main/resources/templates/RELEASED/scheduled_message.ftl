{
  "orderHeader": {
     "orderId": ${orderHeader.orderId},
     "orderTypeCode": "${orderHeader.orderTypeCode}",
     "totalItemQuantity": ${orderHeader.orderTotal.totalItemQuantity},
     "originalTotalItemQuantity": ${orderHeader.orderTotal.originalTotalItemQuantity},
     "statusCode": "${orderHeader.statusCode}",
     "schedReleaseDate": "${orderHeader.schedReleaseDate?date.iso?string["yyyy-MM-dd"]}",
     "schedProcessDate": "${orderHeader.schedProcessDate?date.iso?string["yyyy-MM-dd"]}"
  },
  "lines": [
  <#list lines.line as l>
      {
         "lineNumber": "${l.lineNumber}",
         "lineStatusCode": "${l.lineStatusCode}",
         "productId": "${l.productId}",
         "adjItemQuantity": "${l.adjItemQuantity}"
       }<#if l?is_last == false>,</#if>
  </#list>
   ],
  "customer": {
    "corpId": "${customer.corpId}",
    "divisionId": ${customer.divisionId},
    "customerId": ${customer.customerId},
    "customerType": "${customer.customerType}"
  },
  "supplier": {
    "corpId": "${supplier.corpId}",
    "divisionId": ${supplier.divisionId},
    "supplierId": ${supplier.supplierId},
    "supplierType": "${supplier.supplierType}",
    "distCenterId": "${supplier.distCenterId}"
  }
}