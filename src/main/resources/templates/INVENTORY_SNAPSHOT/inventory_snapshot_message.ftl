{
  "sourceCreationTimestamp": "${sourceCreationTimestamp}",
  "inventory": [
  <#list inventory as inv>
    {
    <#if inv.productId??>
      "productId": "${inv.productId}",
    </#if>
      "gtin": "${inv.gtin}",
      "quantityUom": "${inv.quantityUom}",
      "quantities": [
      <#list inv.quantities as qty>
        {
          "state": "${qty.state}",
          "quantity": ${qty.quantity}
        }<#if qty_has_next>,</#if>
      </#list>
      ]
    }<#if inv_has_next>,</#if>
  </#list>
  ]
}
