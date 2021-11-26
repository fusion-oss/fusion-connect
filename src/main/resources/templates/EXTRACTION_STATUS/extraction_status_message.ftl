{
  "batchCount": ${batchCount},
  "dispenseTerminalId": "${dispenseTerminalId}",
  "status": "${status}",
  <#if (eta?has_content)>
  "eta": ${eta},
  </#if>
  "containers": [
  <#list containers as container>
    "${container}" <#if container_has_next>,</#if>
  </#list>
  ]
}
