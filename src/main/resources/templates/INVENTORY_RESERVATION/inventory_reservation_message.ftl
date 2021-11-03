{
  "orderId": "${orderId}",
  "allowPartialReservation": ${allowPartialReservation?c},
  "items": [
    <#list items as item>
      {
        "lineNbr": ${item.lineNbr},
        "gtins": [
        <#list item.gtins as gtin>
          "${gtin}"<#if gtin_has_next>,</#if>
        </#list>
        ],
      <#if item.uom == "EA">
        "quantity": ${item.quantity},
      </#if>
      <#if item.uom == "GR">
        "weight": ${item.weight},
      </#if>
        "uom": "${item.uom}"
      }<#if item_has_next>,</#if>
    </#list>
  ]
}
