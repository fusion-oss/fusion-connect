{
  "sourceCreationTimestamp": "${sourceCreationTimestamp}",
  "containerId": "${containerId}",
  "revision": ${revision},
  "items": [
  <#list items as item>
    {
      "productId": "${item.productId}",
      "gtin": "${item.gtin}",
      "quantityUom": "${item.quantityUom}",
      "stockStatus": [
      <#list  item.stockStatus as stockStatus>
        {
          "status": "${stockStatus.status}",
          "quantity": ${stockStatus.quantity}
        <#if stockStatus.reasonCode??>
          ,"reasonCode": "${stockStatus.reasonCode}"
        </#if>
        <#if stockStatus.reasonDesc??>
          ,"reasonDesc": "${stockStatus.reasonDesc}"
        </#if>
        }<#if stockStatus_has_next>,</#if>
      </#list>
      ]
    }<#if item_has_next>,</#if>
  </#list>
  ]
}
