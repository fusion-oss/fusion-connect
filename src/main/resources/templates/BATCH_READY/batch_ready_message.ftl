{
  "batchId": ${batchId},
  "dispenseTerminalId": "${dispenseTerminalId}",
  "doorId": "${doorId}",
  "containers": [
  <#list containers as container>
    {
      "containerId": "${container.containerId}",
      "placementSequence": ${container.placementSequence},
      "temperatureZone": "${container.temperatureZone}"
    }
  </#list>
  ]
}