{
<#if (productIds?has_content)>
  "productIds": [
    <#list productIds as id>
      "${id}"<#if id_has_next>,</#if>
    </#list>
  ]
</#if>

<#if (gtins?has_content)>
  "gtins": [
    <#list gtins as gtin>
      "${gtin}"<#if gtin_has_next>,</#if>
    </#list>
  ]
</#if>
}
