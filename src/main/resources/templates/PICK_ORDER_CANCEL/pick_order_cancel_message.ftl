{
    "orders": [
    <#list orders as order>
        {
            "orderId": "${order.orderId}",
            "reason": {
                    "code": "${order.reason.code}",
                    "description": "${order.reason.description}"
            },
            "lines": [
            <#list order.lines as line>
                {
                    "lineNbr": ${line.lineNbr},
                    "reason": "${line.reason}"
                }<#if line_has_next>,</#if>
            </#list>
            ]
        }<#if order_has_next>,</#if>
    </#list>
    ]
}
