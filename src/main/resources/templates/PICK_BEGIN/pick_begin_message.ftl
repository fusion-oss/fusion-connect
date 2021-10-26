<#assign size = MessageBody.orderBeginEvent.fulfillmentOrders.fulfillmentOrder?size>
{
  "size": ${size},
  "orders": [
<#if (size==3)>
    {
      "orderId": "${MessageBody.orderBeginEvent.fulfillmentOrders.fulfillmentOrder.orderNbr}",
  <#if MessageBody.orderBeginEvent.fulfillmentOrders.fulfillmentOrder.priorityCode=="1">
      "orderPriority": "REGULAR"
  <#else>
      "orderPriority": "EXPRESS"
  </#if>
    }
<#elseif (size==2)>
    <#list MessageBody.orderBeginEvent.fulfillmentOrders.fulfillmentOrder as order>
      {
      "orderId": "${order.orderNbr}",
        <#if order.priorityCode=="1">
          "orderPriority": "REGULAR"
        <#else>
          "orderPriority": "EXPRESS"
        </#if>
      }
        <#if order_has_next>,</#if>
    </#list>
</#if>
  ]
}