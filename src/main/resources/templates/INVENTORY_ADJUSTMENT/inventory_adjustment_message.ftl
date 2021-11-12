<#--  Check if any state is "RESERVED"  -->
<#assign reserved = false>
<#list items as item>
    <#if item.state == "RESERVED">
        <#assign reserved = true>
    </#if>
</#list>
{
  "sourceCreationTimestamp": "${sourceCreationTimestamp}",
<#if reserved>
  "containerId": "${containerId},
  "revision": ${revision},
</#if>
  "userId": "${userId}",
  "items": [
  <#list items as item>
    {
      "productId": "${item.productId}",
      "gtin": "${item.gtin}",
      "quantityUom": "${item.quantityUom}",
      "state": "${item.state}",
      "stockQtyChange": [
        <#list item.stockQtyChange as stock>
          {
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