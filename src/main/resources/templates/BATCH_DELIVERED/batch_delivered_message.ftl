{
  "batchId": ${batchId},
  "dispenseTerminalId": "${dispenseTerminalId}",
  "doorId": "${doorId}",
  "containsReturns": ${containsReturns?c},
  "containers": [
  <#list containers as container>
    {
      "containerId": "${container.containerId}",
      "temperatureZone": "${container.temperatureZone}"
    }
  </#list>
  ]
}