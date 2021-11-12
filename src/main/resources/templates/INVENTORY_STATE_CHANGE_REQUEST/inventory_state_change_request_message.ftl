{
  <#if (productId?has_content)>
  "productId": "${productId}", 
  </#if>
  "gtin": "${gtin}",
  <#if (lotNumber?has_content)>
  "lotNumber": "${lotNumber}", 
  </#if>
  <#if (countryOfOrigin?has_content)>
  "countryOfOrigin": "${countryOfOrigin}",
  </#if>
  "currentState": "${currentState}",
  "newState": "${newState}"
}
