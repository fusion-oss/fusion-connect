<#assign TestUtils=statics['com.scoperetail.fusion.plugin.TestUtil']>
{
  "batchId": ${batchId},
  "dispenseTerminalId": "${dispenseTerminalId}",
  "doorId": "${doorId}",
  "containsReturns": ${containsReturns?c},
  <#if (containsReturns)>
  "temperatureZonesForReturns": ${TestUtils.sequenceToString(temperatureZonesForReturns)},
  </#if>
  "containers": [
  <#list containers as container>
    {
      "containerId": "${container.containerId}",
      "temperatureZone": "${container.temperatureZone}"
    }
  </#list>
  ]
}
