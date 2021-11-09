{
  "dispenseTerminalId": "${dispenseTerminalId}"
<#if doors??>
  , "doors": [
  <#list doors as doors>
    {
      "doorId": "${doors.doorId}",
      "available": ${doors.available?c}
    }<#if doors_has_next>,</#if>
  </#list>
  ]
</#if>
}
