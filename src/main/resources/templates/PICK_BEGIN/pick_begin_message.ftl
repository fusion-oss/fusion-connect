<#-- Check if is sequence (or hash) -->
<#assign isSequence = MessageBody.orderBeginEvent.fulfillmentOrders.fulfillmentOrder?is_sequence>
{
"orders": [
<#if isSequence >
<#-- If is sequence-->
    <#list MessageBody.orderBeginEvent.fulfillmentOrders.fulfillmentOrder as order>
      {
      "orderId": "${order.orderNbr}",
        <#if order.priorityCode=="1">
          "orderPriority": "REGULAR"
        <#else>
          "orderPriority": "EXPRESS"
        </#if>
      "userId": "${order.userId}"
      }<#if order_has_next>,</#if>
    </#list>
<#else>
<#-- If is not sequence (then is hash) -->
  {
  "orderId": "${MessageBody.orderBeginEvent.fulfillmentOrders.fulfillmentOrder.orderNbr}",
    <#if MessageBody.orderBeginEvent.fulfillmentOrders.fulfillmentOrder.priorityCode=="1">
      "orderPriority": "REGULAR"
    <#else>
      "orderPriority": "EXPRESS"
    </#if>
  "userId": "${MessageBody.orderBeginEvent.fulfillmentOrders.fulfillmentOrder.userId}"
  }
</#if>
]
}
