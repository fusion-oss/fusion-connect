<#assign TestUtils=statics['com.alertinnovation.fusion.plugin.TestUtil']>
<#--{-->
<#--  "products": [-->
<#--  <#list products as product>-->
<#--  ${TestUtils.hashToString(product)}<#if (product_has_next)>,</#if>-->
<#--  </#list>-->
<#--  ]-->
<#--}-->

{
"products": ${TestUtils.sequenceToString(products)}
}


