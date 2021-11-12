{
  "orderId": "${orderId}",
  "items": [
  <#list items as item>
    {
      "lineNbr": ${item.lineNbr},
      "gtin": "${item.gtin}",
    <#if item.uom == "EA">
      "quantity": ${item.quantity},
    </#if>
    <#if item.uom == "GR">
      "weight": ${item.weight},
    </#if>
      "uom": "${item.uom}",
    }<#if item_has_next>,</#if>
  </#list>
  ]
}
