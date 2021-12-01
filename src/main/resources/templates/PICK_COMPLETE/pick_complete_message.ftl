<#assign TestUtils=statics['com.scoperetail.fusion.plugin.TestUtil']>

{
  "pickCompleteDetails": [
    {
      "orderId": "881618855157723",
      "lines": [
    <#assign isLinesSequece = MessageBody.PickOrderComplete.fulfillmentOrder.lines?is_sequence>
    <#if isLinesSequece>
        <#list MessageBody.PickOrderComplete.fulfillmentOrder.lines as line >
        <#assign pickDetail = line.pickDetails>
        <#assign container = pickDetail.container>
          {
            "lineNbr": ${line.lineNbr},
            "pickDetails": [
              {
                "pickByType": "${pickDetail.pickUom}",
                "pickGtin": "${TestUtils.getGtinFromUpc(pickDetail.pickUpcNbr)}",
                "userId": "${pickDetail.pickedUser}",
                "pickDisplayTs": "${pickDetail.pickDisplayTs}",
                "pickedTs": "${pickDetail.pickedTs}",
                "pickEachUom": "EA",
                "pickEachQty": ${pickDetail.pickQty},
                "pickEachCurrency": "USD",
                "pickEachUnitOrdePrice": ${pickDetail.unitPrice},
                "containers": [
                  {
                    "pickQty": ${pickDetail.pickQty},
                    "containerId": "${container.containerNbr}"
                  }
                ],
                "pickLocation": "${container.location}"
              }
            ]
          }
        ]<#if line_has_next>,</#if>
        </#list>
    <#else>
        <#assign line = MessageBody.PickOrderComplete.fulfillmentOrder.lines.line>
        <#assign pickDetail = line.pickDetails>
        <#assign container = pickDetail.container>
        {
          "lineNbr": ${line.lineNbr},
          "pickDetails": [
            {
              "pickByType": "${pickDetail.pickUom}",
              "pickGtin": "${TestUtils.getGtinFromUpc(pickDetail.pickUpcNbr)}",
              "userId": "${pickDetail.pickedUser}",
              "pickDisplayTs": "${pickDetail.pickDisplayTs}",
              "pickedTs": "${pickDetail.pickedTs}",
              "pickEachUom": "EA",
              "pickEachQty": ${pickDetail.pickQty},
              "pickEachCurrency": "USD",
              "pickEachUnitPrice": ${pickDetail.unitPrice},
              "containers": [
                {
                  "pickQty": ${pickDetail.pickQty},
                  "containerId": "${container.containerNbr}"
                }
              ],
              "pickLocation": "${container.location}"
            }
          ]
        }
    </#if>
      ]
    }
  ]
}
