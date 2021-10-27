<#-- Check if is sequence (or hash) -->
<#assign isLinesSequence = MessageBody.PickOrderComplete.fulfillmentOrder.lines.line?is_sequence>
<#assign aDateTime = .now>
[
{
"pickCompleteDetails": [
{
"orderId": "${MessageBody.PickOrderComplete.fulfillmentOrder.orderNbr}",
"lines": [
<#if isLinesSequence >
<#list MessageBody.PickOrderComplete.fulfillmentOrder.lines.line as line>
  {
    "lineNbr": ${line.lineNbr},
    <#if line.lineFinalize?has_content>
    "lineFinalize": ${line.lineFinalize?string},
    </#if>
    <#list line.pickDetails as pickDetail>
      "pickDetails": [
      {
      "pickByType": "${pickDetail.pickUom}",
      "pickGtin": "${pickDetail.pickUpcNbr}",
      "pickedUser": "${pickDetail.pickedUser}",
      "pickDisplayTs": "${pickDetail.pickDisplayTs}",
      "pickedTs": "${pickDetail.pickedTs}",
      "pickEachUom": "${pickDetail.pickUom}",
      "pickEachQty": ${pickDetail.pickQty},
      "pickWeightUom": "",
      "pickWeightQty": 0.00,
      "pickEachCurrency": "USD",
      "pickEachUnitPrice": ${pickDetail.unitPrice},
      "pickWeightCurrency": "USD",
      "pickWeightUnitPrice": 0.00,
        <#if pickDetail.container??>
          "containers": [
          {
          "containerId": "${pickDetail.container.containerNbr}",
            <#if pickDetail.pickUom=="EACH" || pickDetail.pickUom=="EACHWEIGHTED">
              "pickQty": ${pickDetail.pickQty},
            </#if>
            <#if pickDetail.pickUom=="WEIGHTED" || pickDetail.pickUom=="EACHWEIGHTED" || pickDetail.pickUom=="PREFQTY">
              "pickWeight": 0.00
            </#if>
          }],
          "pickLocation": "${pickDetail.container.location}",
        <#else>
          "containers": [
          {
          "containerId": "",
          "pickQty": 0,
          "pickWeight": 0.00
          }],
          "pickLocation": "",
        </#if>
        <#if pickDetail.activations??>
          "activations": [
          {
          "activationType": "SERIAL",
          "activationValue": "31456125653"
          }
          ]
        </#if>
      }
        <#if pickDetail_has_next>,</#if>
    </#list>
  ]
  }
    <#if line_has_next>,</#if>
</#list>
</#if>
]
<#if MessageBody.PickOrderComplete.CarrierBags()??>
  ,"carrierBags":
  {
  "bagCountStartTime": "2021-04-01T12:22:32.000Z",
  "bagCountEndTime": "2021-04-01T12:22:32.000Z",
  "bags": [
  {
  "bagCount": 1,
  "containerId": "520032"
  },
  {
  "bagCount": 2,
  "containerId": "520033"
  }
  ]
  }
</#if>
}]
}
]