{
  "batchCount": ${batchCount},
  "dispenseTerminalId": "${dispenseTerminalId}",
  "status": "${status}",
  "containers": [
  <#list containers as container>
    "${container}" <#if container_has_next>,</#if>
  </#list>
  ]
}
