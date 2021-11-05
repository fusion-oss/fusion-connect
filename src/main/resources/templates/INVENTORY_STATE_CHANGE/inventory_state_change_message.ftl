{
  "sourceCreationTimestamp": "${sourceCreationTimestamp}",
  <#if (containerId?has_content)>
  "containerId": "${containerId}",
  </#if>
  <#if (revision?has_content)>
  "revision": ${revision},
  </#if>
  "userId": "${userId}",
  "items": [
  <#list items as item >
    {
      "productId": "${item.productId}",
      "gtin": "${item.gtin}",
      "quantityUom": "${item.quantityUom}",
      "previousState": "${item.previousState}",
      "stockStateChange": [
      <#list item.stockStateChange as stock >
        {
          "currentState": "${stock.currentState}",
          "quantity": ${stock.quantity},
          "reasonCode": "${stock.reasonCode}",
          "reasonDesc": "${stock.reasonDesc}"
        }<#if stock_has_next>,</#if>
      </#list>
      ]
    }<#if item_has_next>,</#if>
  </#list>
  ]
}

