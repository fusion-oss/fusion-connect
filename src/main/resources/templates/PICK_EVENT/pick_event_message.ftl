{
    "pickEventActivities": [
    <#list pickEventActivities as activation>
        {
            "orderId": "${activation.orderId}",
            "lineNbr": ${activation.lineNbr},
            "pickByType": "${activation.pickByType}",

        <#if activation.pickByType == "PREFQTY">
            "lineFinalize": ${activation.lineFinalize?c},
        </#if>

            "pickGtin": "${activation.pickGtin}",
            "userId": "${activation.userId}",
            "pickDisplayTs": "${activation.pickDisplayTs}",
            "pickedTs": "${activation.pickedTs}",

        <#if activation.pickByType == "EACH" || activation.pickByType == "EACHWEIGHTED">
            <#if activation.pickEachQty?has_content>
            "pickEachUom": "${activation.pickEachUom}",
            </#if>
            "pickEachQty": ${activation.pickEachQty},
        </#if>

        <#if activation.pickByType == "WEIGHTED" || activation.pickByType == "EACHWEIGHTED" || activation.pickByType == "PREFQTY">
            <#if activation.pickWeightQty?has_content>
            "pickWeightUom": "${activation.pickWeightUom}",
            </#if>
            "pickWeightQty": ${activation.pickWeightQty},
        </#if>

        <#if activation.pickEachCurrency?has_content>
            "pickEachCurrency": "${activation.pickEachCurrency}",
        </#if>

        <#if activation.pickEachUnitPrice?has_content>
            "pickEachUnitPrice": ${activation.pickEachUnitPrice},
        </#if>

        <#if activation.pickWeightCurrency?has_content>
            "pickWeightCurrency": "${activation.pickWeightCurrency}",
        </#if>

        <#if activation.pickWeightUnitPrice?has_content>
            "pickWeightUnitPrice": ${activation.pickWeightUnitPrice},
        </#if>

            "containerId": "${activation.containerId}"

        <#if activation.pickLocation?has_content>
            ,"pickLocation": "${activation.pickLocation}"
        </#if>


        <#if activation.activations?has_content>
            ,"activations": [
            <#list activation.activations as subActivation>
                {
                    "activationType": "${subActivation.activationType}",
                    "activationValue": "${subActivation.activationValue}",
                }<#if subActivation_has_next>,</#if>
            </#list>
            ]
        </#if>

        <#if activation.pickGtinPrefRank?has_content>
            ,"pickGtinPrefRank": "${activation.pickGtinPrefRank}"
        </#if>

        }<#if activation_has_next>,</#if>
    </#list>
    ]
}
