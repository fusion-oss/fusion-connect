{
  "products": [
  <#list products as product>
    {
      "action": "${product.action}",
      "productId": "${product.productId}"
      <#if (product.productName?has_content)>
      ,"productName": "${product.productName}"
      </#if>
      <#if (product.productShortDescription?has_content)>
      ,"productShortDescription": "${product.productShortDescription}"
      </#if>
      <#if (product.deptNbr?has_content)>
      ,"deptNbr": "${product.deptNbr}"
      </#if>
      <#if (product.deptCategoryNbr?has_content)>
      ,"deptCategoryNbr": "${product.deptCategoryNbr}",
      </#if>
      <#if (product.deptSubcatgNbr?has_content)>
      ,"deptSubcatgNbr": "${product.deptSubcatgNbr}"
      </#if>
      <#if (product.temperatureZones?has_content)>
      ,"temperatureZones": [
      <#list product.temperatureZones as temperatureZone>
        "${temperatureZone}" <#if (temperatureZone_has_next)>,</#if>
      </#list>
      ]
      </#if>
      <#if (product.segregationCode?has_content)>
      ,"segregationCode": "${product.segregationCode}"
      </#if>
      <#if (product.requiresContainerWashing?has_content)>
      ,"requiresContainerWashing": ${product.requiresContainerWashing?c}
      </#if>
      <#if (product.requiresInspectionWhileInStorage?has_content)>
      ,"requiresInspectionWhileInStorage": ${product.requiresInspectionWhileInStorage?c}
      </#if>
      <#if (product.imageUrl?has_content)>
      ,"imageUrl": "${product.imageUrl}",
      </#if>
      <#if (product.hasPriceEmbeddedBarcode?has_content)>
      ,"hasPriceEmbeddedBarcode": ${product.hasPriceEmbeddedBarcode?c}
      </#if>
      <#if (product.looseBulkMerchandise?has_content)>
      ,"looseBulkMerchandise": ${product.looseBulkMerchandise?c}
      </#if>
      <#if (product.hasSecurityTagInd?has_content)>
      ,"hasSecurityTagInd": ${product.hasSecurityTagInd?c}
      </#if>
      <#if (product.isExpirationDateTracked?has_content)>
      ,"isExpirationDateTracked": ${product.isExpirationDateTracked?c}
      </#if>
      <#if (product.storeMinimumLifeRemainingToReceiveQty?has_content)>
      ,"storeMinimumLifeRemainingToReceiveQty": "${product.storeMinimumLifeRemainingToReceiveQty}"
      </#if>
      <#if (product.storeMinimumLifeRemainingToReceiveUom?has_content)>
      ,"storeMinimumLifeRemainingToReceiveUom": "${product.storeMinimumLifeRemainingToReceiveUom}"
      </#if>
      <#if (product.containerType?has_content)>
      ,"containerType": "${product.containerType}",
      </#if>
      <#if (product.eachDimensions?has_content)>
      <#--  TODO: Handle commas -->
      ,"eachDimensions": {
        <#if (product.eachDimensions.height?has_content)>
        "height": ${product.eachDimensions.height}
        </#if>
        <#if (product.eachDimensions.width?has_content)>
        "width": ${product.eachDimensions.width}
        </#if>
        <#if (product.eachDimensions.depth?has_content)>
        "depth": ${product.eachDimensions.depth}
        </#if>
        <#if (product.eachDimensions.uom?has_content)>
        "uom": "${product.eachDimensions.uom}"
        </#if>
      }
      </#if>
      <#if (product.eachWeight?has_content)>
      ,"eachWeight": {
        <#--  TODO: Handle commas -->
        <#if (product.eachWeight.amount?has_content)>
        "amount": ${product.eachWeight.amount}
        </#if>
        <#if (product.eachWeight.uom?has_content)>
        "uom": "${product.eachWeight.uom}"
        </#if>
      }
      </#if>
      <#if (product.allowedPartitionsPerTote?has_content)>
      ,"allowedPartitionsPerTote": [
      <#list product.allowedPartitionsPerTote as partition>
        ${partition} <#if (partition_has_next)>,</#if>
      </#list>
      ]
      </#if>
      <#if (product.gtins?has_content)>
      ,"gtins": [
        <#list product.gtins as gtin>
        {
          <#--  TODO: Handle commas -->
          <#if (gtin.gtin?has_content)>
          "gtin": "${gtin.gtin}"
          </#if>
          <#if (gtin.caseConfigurations?has_content)>
          "caseConfigurations": [
          <#list gtin.caseConfigurations as config>
            {
              <#--  TODO: Handle commas -->
              <#if (config.activeConfiguration?has_content)>
              "activeConfiguration": ${config.activeConfiguration?c}
              </#if>
              <#if (config.orderablePackQty?has_content)>
              "orderablePackQty": ${config.orderablePackQty}
              </#if>
              <#if (config.orderablePackQtyUom?has_content)>
              "orderablePackQtyUom": "${config.orderablePackQtyUom}"
              </#if>
              <#if (config.caseDimensions?has_content)>
              "caseDimensions": {
              <#--  TODO: Handle commas -->
                <#if (config.caseDimensions.height?has_content)>
                "height": ${config.caseDimensions.height}
                </#if>
                <#if (config.caseDimensions.width?has_content)>
                "width": ${config.caseDimensions.width}
                </#if>
                <#if (config.caseDimensions.depth?has_content)>
                "depth": ${config.caseDimensions.depth}
                </#if>
                <#if (config.caseDimensions.uom?has_content)>
                "uom": "${config.caseDimensions.uom}"
                </#if>
              },
              </#if>
              <#if (config.caseWeight?has_content)>
              <#--  TODO: Handle commas -->
              "caseWeight": {
                <#if (config.caseWeight.amount?has_content)>
                "amount": ${config.caseWeight.amount}
                </#if>
                <#if (config.caseWeight.uom?has_content)>
                "uom": "${config.caseWeight.uom}"
                </#if>
              }
              </#if>
            }<#if (config_has_next)>,</#if>
          </#list>
          ]
          </#if>
        }<#if (gtin_has_next)>,</#if>
      </#list>
      ]
      </#if>
      <#if (product.deleteEffectiveDate?has_content)>
      ,"deleteEffectiveDate": "${product.deleteEffectiveDate}"
      </#if>
      <#if (product.pullDate?has_content)>
      ,"pullDate": "${product.pullDate}"
      </#if>
    }<#if (product_has_next)>,</#if>
  </#list>
  ]
}

