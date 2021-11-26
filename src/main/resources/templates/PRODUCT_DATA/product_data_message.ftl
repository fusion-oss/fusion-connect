<#assign TestUtils=statics['com.scoperetail.fusion.connect.core.common.util.TestUtil']>
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


