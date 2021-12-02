{
  "dispenseTerminalId": "${dispenseTerminalId}"
<#if doors??>
  , "doors": [
  <#list doors as door>
    {
      "doorId": "${door.doorId}",
      "available": ${door.available?c},
      "state": "${door.state}"
    }<#if door_has_next>,</#if>
  </#list>
  ]
</#if>
}
