{
  "storageUtilization": [
  <#list storageUtilization as utilization>
    {
      "storageType": "${utilization.storageType}",
      "totalPositionQty": ${utilization.totalPositionQty},
      "totalOccupiedQty": ${utilization.totalOccupiedQty},
      "storageTemperatureZoneUtilization": [
      <#list utilization.storageTemperatureZoneUtilization as temperatureZoneUtilization>
        {
          "temperatureZone": "${temperatureZoneUtilization.temperatureZone}",
          "positionQty": ${temperatureZoneUtilization.positionQty},
          "occupiedQty": ${temperatureZoneUtilization.occupiedQty}
        }<#if temperatureZoneUtilization_has_next>,</#if>
      </#list>
      ]
    }<#if utilization_has_next>,</#if>
  </#list>
  ],
  "toteUtilization": [
  <#list toteUtilization as tote>
    {
      "toteType": "${tote.toteType}",
      "interiorDimensions": {
        "height": ${tote.interiorDimensions.height},
        "width": ${tote.interiorDimensions.width},
        "depth": ${tote.interiorDimensions.depth},
        "uom": "${tote.interiorDimensions.uom}"
      },
      "weight": {
        "amount": ${tote.weight.amount},
        "uom": "${tote.weight.uom}"
      },
      "maxContentsWeight": {
        "amount": ${tote.maxContentsWeight.amount},
        "uom": "${tote.maxContentsWeight.uom}"
      },
      "totalToteQty": ${tote.totalToteQty},
      "totalUsedQty": ${tote.totalUsedQty},
      "totalUnusableQty": ${tote.totalUnusableQty},
      <#if tote.totalMfcFulfilledQty??>
      "totalMfcFulfilledQty": ${tote.totalMfcFulfilledQty},
      </#if>
      <#if tote.totalStoreFulfilledQty??>
      "totalStoreFulfilledQty": ${tote.totalStoreFulfilledQty},
      </#if>
      "toteTemperatureZoneUtilization": [
      <#list tote.toteTemperatureZoneUtilization as toteTempZoneUtilization>
        {
          "temperatureZone": "${toteTempZoneUtilization.temperatureZone}",
          "toteQty": ${toteTempZoneUtilization.toteQty},
          "usedQty": ${toteTempZoneUtilization.usedQty},
          "unusableQty": ${toteTempZoneUtilization.unusableQty}
          <#if toteTempZoneUtilization.mfcFulfilledQty??>
          , "mfcFulfilledQty": ${toteTempZoneUtilization.mfcFulfilledQty}
          </#if>
          <#if toteTempZoneUtilization.storefulfilledQty??>
          , "storefulfilledQty": ${toteTempZoneUtilization.storefulfilledQty}
          </#if>
        }<#if toteTempZoneUtilization_has_next>,</#if>
      </#list>
      ],
    "partitionsAllowed": ${tote.partitionsAllowed?c}
    <#if tote.partitionsAllowed==true>
    , "partitionUtilization": [
      <#list tote.partitionUtilization as partitionUtilization>
        {
          "partitionsPerTote": ${partitionUtilization.partitionsPerTote},
          "partitionInteriorDimensions": {
            "height": ${partitionUtilization.partitionInteriorDimensions.height},
            "width": ${partitionUtilization.partitionInteriorDimensions.width},
            "depth": ${partitionUtilization.partitionInteriorDimensions.depth},
            "uom": "${partitionUtilization.partitionInteriorDimensions.uom}"
          },
          "totalPartitionQty": ${partitionUtilization.totalPartitionQty},
          "totalUsedPartitionQty": ${partitionUtilization.totalUsedPartitionQty},
          "totalUnusablePartitionQty": ${partitionUtilization.totalUnusablePartitionQty},
          "partitionTemperatureZoneUtilization": [
          <#list partitionUtilization.partitionTemperatureZoneUtilization as partTempZoneUtilization>
            {
              "temperatureZone": "${partTempZoneUtilization.temperatureZone}",
              "partitionQty": ${partTempZoneUtilization.partitionQty},
              "usedPartitionQty": ${partTempZoneUtilization.usedPartitionQty},
              "unusablePartitionQty": ${partTempZoneUtilization.unusablePartitionQty}
            }<#if partTempZoneUtilization_has_next>,</#if>
          </#list>
          ]
        }<#if partitionUtilization_has_next>,</#if>
      </#list>
    ]
    </#if>
    }<#if tote_has_next>,</#if>
  </#list>
  ]
}
