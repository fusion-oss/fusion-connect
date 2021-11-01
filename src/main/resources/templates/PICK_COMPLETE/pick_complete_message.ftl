<#ftl strip_whitespace=true>
{
  "pickCompleteDetails": [
    {
      "orderId": "${MessageBody.PickOrderComplete.fulfillmentOrder.orderNbr}",
      "lines": [
      <#--Check if is sequence (or hash)-->
      <#assign isLinesSequece = MessageBody.PickOrderComplete.fulfillmentOrder.lines?is_sequence>
      <#if isLinesSequece>
      <#list MessageBody.PickOrderComplete.fulfillmentOrder.lines as line >
        {
          "lineNbr": ${line.lineNbr},

          <#if line.lineFinalize?has_content>
            "lineFinalize": ${line.lineFinalize?c},
          </#if>

          "pickDetails": [
          <#--Check if is sequence (or hash)-->
          <#assign isPickDetailsSequece = line.pickDetails?is_sequence>
          <#if isPickDetailsSequece>
          <#list line.pickDetails as pickDetail>
            {
              "pickByType": "${pickDetail.pickType}",
              "pickGtin": "${pickDetail.pickGtin}",
              "pickedUser": "${pickDetail.pickedUser}",
              "pickDisplayTs": "${pickDetail.pickDisplayTs}",
              "pickedTs": "${pickDetail.pickedTs}",

              <#-- EACH -->
              <#if pickDetail.pickEachUom?has_content>
              "pickEachUom": ${pickDetail.pickEachUom},
              </#if>

              <#if pickDetail.pickEachQty?has_content>
              "pickEachQty": ${pickDetail.pickEachQty},
              </#if>

              <#if pickDetail.pickEachCurrency?has_content>
              "pickEachCurrency": ${pickDetail.pickEachCurrency},
              </#if>

              <#if pickDetail.pickEachUnitPrice?has_content>
              "pickEachUnitPrice": ${pickDetail.pickEachUnitPrice},
              </#if>

              <#-- WEIGHT -->
              <#if pickDetail.pickWeightUom?has_content>
              "pickWeightUom": ${pickDetail.pickWeightUom},
              </#if>

              <#if pickDetail.pickWeightQty?has_content>
              "pickWeightQty": ${pickDetail.pickWeightQty},
              </#if>

              <#if pickDetail.pickWeightCurrency?has_content>
              "pickWeightCurrency": ${pickDetail.pickWeightCurrency},
              </#if>

              <#if pickDetail.pickWeightUnitPrice?has_content>
              "pickWeightUnitPrice": ${pickDetail.pickWeightUnitPrice},
              </#if>

            "containers": [
              <#--Check if is sequence (or hash)-->
              <#assign isContainersSequece = pickDetail.containers?is_sequence>
              <#if isContainersSequece>
              <#list pickDetail.containers as container>
                {
                  "containerId": "${container.containerId}",

                  <#if pickDetail.pickType == "EACH" || pickDetail.pickType == "EACHWEIGHTED">
                  "pickQty": ${container.pickQty},
                  </#if>

                  <#if pickDetail.pickType == "WEIGHTED" || pickDetail.pickType == "EACHWEIGHTED" || pickDetail.pickType == "PREFQTY">
                  "pickWeight": ${container.pickWeight}
                  </#if>

                }<#if container_has_next>,</#if>
              </#list>
              <#else>
                <#assign container = pickDetail.containers>
                {
                  "containerId": "${container.containerId}",

                  <#if pickDetail.pickType == "EACH" || pickDetail.pickType == "EACHWEIGHTED">
                    "pickQty": ${container.pickQty},
                  </#if>

                  <#if pickDetail.pickType == "WEIGHTED" || pickDetail.pickType == "EACHWEIGHTED" || pickDetail.pickType == "PREFQTY">
                    "pickWeight": ${container.pickWeight}
                  </#if>
                }
              </#if>
            ]
              <#if pickDetail.pickLocation?has_content>
              ,"pickLocation": "${pickDetail.pickLocation}"
              </#if>

              <#if pickDetail.activations?has_content>
              ,"activations": [
                <#--Check if is sequence (or hash)-->
                <#assign isActivationsSequece = pickDetail.activations?is_sequence>
                <#if isActivationsSequece>
                <#list pickDetail.activations as activation>
                  {
                    "activationType": "${activation.activationType}",
                    "activationValue": "${activation.activationValue}",
                  }<#if activation_has_next>,</#if>
                </#list>
                <#else>
                  <#assign activation = pickDetail.activations>
                  {
                    "activationType": "${activation.activationType}",
                    "activationValue": "${activation.activationValue}",
                  }
                </#if>
              ]
              </#if>
            }<#if pickDetail_has_next>,</#if>
          </#list>
          <#else>
              <#assign pickDetail = line.pickDetails>

            {
              "pickByType": "${pickDetail.pickType}",
              "pickGtin": "${pickDetail.pickGtin}",
              "pickedUser": "${pickDetail.pickedUser}",
              "pickDisplayTs": "${pickDetail.pickDisplayTs}",
              "pickedTs": "${pickDetail.pickedTs}",

              <#-- EACH -->
              <#if pickDetail.pickEachUom?has_content>
              "pickEachUom": ${pickDetail.pickEachUom},
              </#if>

              <#if pickDetail.pickEachQty?has_content>
              "pickEachQty": ${pickDetail.pickEachQty},
              </#if>

              <#if pickDetail.pickEachCurrency?has_content>
              "pickEachCurrency": ${pickDetail.pickEachCurrency},
              </#if>

              <#if pickDetail.pickEachUnitPrice?has_content>
              "pickEachUnitPrice": ${pickDetail.pickEachUnitPrice},
              </#if>

              <#-- WEIGHT -->
              <#if pickDetail.pickWeightUom?has_content>
              "pickWeightUom": ${pickDetail.pickWeightUom},
              </#if>

              <#if pickDetail.pickWeightQty?has_content>
              "pickWeightQty": ${pickDetail.pickWeightQty},
              </#if>

              <#if pickDetail.pickWeightCurrency?has_content>
              "pickWeightCurrency": ${pickDetail.pickWeightCurrency},
              </#if>

              <#if pickDetail.pickWeightUnitPrice?has_content>
              "pickWeightUnitPrice": ${pickDetail.pickWeightUnitPrice},
              </#if>

            "containers": [
            <#--Check if is sequence (or hash)-->
            <#assign isContainersSequece = pickDetail.containers?is_sequence>
            <#if isContainersSequece>
              <#list pickDetail.containers as container>
                {
                  "containerId": "${container.containerId}",

                  <#if pickDetail.pickType == "EACH" || pickDetail.pickType == "EACHWEIGHTED">
                    "pickQty": ${container.pickQty},
                  </#if>

                  <#if pickDetail.pickType == "WEIGHTED" || pickDetail.pickType == "EACHWEIGHTED" || pickDetail.pickType == "PREFQTY">
                    "pickWeight": ${container.pickWeight}
                  </#if>

                }<#if container_has_next>,</#if>
              </#list>
            <#else>
              <#assign container = pickDetail.containers>
              {
                "containerId": "${container.containerId}",

                <#if pickDetail.pickType == "EACH" || pickDetail.pickType == "EACHWEIGHTED">
                "pickQty": ${container.pickQty},
                </#if>

                <#if pickDetail.pickType == "WEIGHTED" || pickDetail.pickType == "EACHWEIGHTED" || pickDetail.pickType == "PREFQTY">
                "pickWeight": ${container.pickWeight}
                </#if>
              }
            </#if>
            ]
              <#if pickDetail.pickLocation?has_content>
                ,"pickLocation": "${pickDetail.pickLocation}"
              </#if>

              <#if pickDetail.activations?has_content>
                ,"activations": [
              <#--Check if is sequence (or hash)-->
                  <#assign isActivationsSequece = pickDetail.activations?is_sequence>
                  <#if isActivationsSequece>
                      <#list pickDetail.activations as activation>
                        {
                        "activationType": "${activation.activationType}",
                        "activationValue": "${activation.activationValue}",
                        }<#if activation_has_next>,</#if>
                      </#list>
                  <#else>
                      <#assign activation = pickDetail.activations>
                    {
                    "activationType": "${activation.activationType}",
                    "activationValue": "${activation.activationValue}",
                    }
                  </#if>
                ]
              </#if>
            }
          </#if>
          ]
        }<#if line_has_next>,</#if>
      </#list>
      <#else>
          <#assign line = MessageBody.PickOrderComplete.fulfillmentOrder.lines.line>
          {
            "lineNbr": ${line.lineNbr},

            <#if line.lineFinalize?has_content>
            "lineFinalize": ${line.lineFinalize?c},
            </#if>

            "pickDetails": [
            <#--Check if is sequence (or hash)-->
              <#assign isPickDetailsSequece = line.pickDetails?is_sequence>
              <#if isPickDetailsSequece>
                  <#list line.pickDetails as pickDetail>
                    {
                      "pickByType": "${pickDetail.pickType}",
                      "pickGtin": "${pickDetail.pickGtin}",
                      "pickedUser": "${pickDetail.pickedUser}",
                      "pickDisplayTs": "${pickDetail.pickDisplayTs}",
                      "pickedTs": "${pickDetail.pickedTs}",

                      <#-- EACH -->
                      <#if pickDetail.pickEachUom?has_content>
                      "pickEachUom": ${pickDetail.pickEachUom},
                      </#if>

                      <#if pickDetail.pickEachQty?has_content>
                      "pickEachQty": ${pickDetail.pickEachQty},
                      </#if>

                      <#if pickDetail.pickEachCurrency?has_content>
                      "pickEachCurrency": ${pickDetail.pickEachCurrency},
                      </#if>

                      <#if pickDetail.pickEachUnitPrice?has_content>
                      "pickEachUnitPrice": ${pickDetail.pickEachUnitPrice},
                      </#if>

                      <#-- WEIGHT -->
                      <#if pickDetail.pickWeightUom?has_content>
                      "pickWeightUom": ${pickDetail.pickWeightUom},
                      </#if>

                      <#if pickDetail.pickWeightQty?has_content>
                      "pickWeightQty": ${pickDetail.pickWeightQty},
                      </#if>

                      <#if pickDetail.pickWeightCurrency?has_content>
                      "pickWeightCurrency": ${pickDetail.pickWeightCurrency},
                      </#if>

                      <#if pickDetail.pickWeightUnitPrice?has_content>
                      "pickWeightUnitPrice": ${pickDetail.pickWeightUnitPrice},
                      </#if>

                    "containers": [
                    <#--Check if is sequence (or hash)-->
                      <#assign isContainersSequece = pickDetail.containers?is_sequence>
                      <#if isContainersSequece>
                      <#list pickDetail.containers as container>
                        {
                          "containerId": "${container.containerId}",

                          <#if pickDetail.pickType == "EACH" || pickDetail.pickType == "EACHWEIGHTED">
                          "pickQty": ${container.pickQty},
                          </#if>

                          <#if pickDetail.pickType == "WEIGHTED" || pickDetail.pickType == "EACHWEIGHTED" || pickDetail.pickType == "PREFQTY">
                          "pickWeight": ${container.pickWeight}
                          </#if>

                        }<#if container_has_next>,</#if>
                      </#list>
                      <#else>
                        <#assign container = pickDetail.containers>
                        {
                          "containerId": "${container.containerId}",

                          <#if pickDetail.pickType == "EACH" || pickDetail.pickType == "EACHWEIGHTED">
                          "pickQty": ${container.pickQty},
                          </#if>

                          <#if pickDetail.pickType == "WEIGHTED" || pickDetail.pickType == "EACHWEIGHTED" || pickDetail.pickType == "PREFQTY">
                          "pickWeight": ${container.pickWeight}
                          </#if>
                        }
                      </#if>
                    ]
                      <#if pickDetail.pickLocation?has_content>
                        ,"pickLocation": "${pickDetail.pickLocation}"
                      </#if>

                      <#if pickDetail.activations?has_content>
                        ,"activations": [
                          <#--Check if is sequence (or hash)-->
                          <#assign isActivationsSequece = pickDetail.activations?is_sequence>
                          <#if isActivationsSequece>
                          <#list pickDetail.activations as activation>
                            {
                              "activationType": "${activation.activationType}",
                              "activationValue": "${activation.activationValue}",
                            }<#if activation_has_next>,</#if>
                          </#list>
                          <#else>
                            <#assign activation = pickDetail.activations>
                            {
                              "activationType": "${activation.activationType}",
                              "activationValue": "${activation.activationValue}",
                            }
                          </#if>
                        ]
                      </#if>
                    }<#if pickDetail_has_next>,</#if>
                  </#list>
              <#else>
                  <#assign pickDetail = line.pickDetails>

                {
                "pickByType": "${pickDetail.pickType}",

                <#if pickDetail.pickGtin?has_content>
                  "pickGtin": "${pickDetail.pickGtin}",
                </#if>

                "pickedUser": "${pickDetail.pickedUser}",
                "pickDisplayTs": "${pickDetail.pickDisplayTs}",
                "pickedTs": "${pickDetail.pickedTs}",

              <#-- EACH -->
                  <#if pickDetail.pickEachUom?has_content>
                    "pickEachUom": ${pickDetail.pickEachUom},
                  </#if>

                  <#if pickDetail.pickEachQty?has_content>
                    "pickEachQty": ${pickDetail.pickEachQty},
                  </#if>

                  <#if pickDetail.pickEachCurrency?has_content>
                    "pickEachCurrency": ${pickDetail.pickEachCurrency},
                  </#if>

                  <#if pickDetail.pickEachUnitPrice?has_content>
                    "pickEachUnitPrice": ${pickDetail.pickEachUnitPrice},
                  </#if>

              <#-- WEIGHT -->
                  <#if pickDetail.pickWeightUom?has_content>
                    "pickWeightUom": ${pickDetail.pickWeightUom},
                  </#if>

                  <#if pickDetail.pickWeightQty?has_content>
                    "pickWeightQty": ${pickDetail.pickWeightQty},
                  </#if>

                  <#if pickDetail.pickWeightCurrency?has_content>
                    "pickWeightCurrency": ${pickDetail.pickWeightCurrency},
                  </#if>

                  <#if pickDetail.pickWeightUnitPrice?has_content>
                    "pickWeightUnitPrice": ${pickDetail.pickWeightUnitPrice},
                  </#if>

                "containers": [
               <#--Check if is sequence (or hash)-->
                  <#assign isContainersSequece = pickDetail.container?is_sequence>
                  <#if isContainersSequece>
                      <#list pickDetail.containers as container>
                        {
                        "containerId": "${container.containerId}",

                          <#if pickDetail.pickType == "EACH" || pickDetail.pickType == "EACHWEIGHTED">
                            "pickQty": ${container.pickQty},
                          </#if>

                          <#if pickDetail.pickType == "WEIGHTED" || pickDetail.pickType == "EACHWEIGHTED" || pickDetail.pickType == "PREFQTY">
                            "pickWeight": ${container.pickWeight}
                          </#if>

                        }<#if container_has_next>,</#if>
                      </#list>
                  <#else>
                      <#assign container = pickDetail.container>
                    {
                    "containerId": "${container.containerNbr}",

                      <#if (pickDetail.pickType == "EACH" || pickDetail.pickType == "EACHWEIGHTED") && container.pickQty?has_content>
                        "pickQty": ${container.pickQty},
                      </#if>

                      <#if (pickDetail.pickType == "WEIGHTED" || pickDetail.pickType == "EACHWEIGHTED" || pickDetail.pickType == "PREFQTY") && container.pickWeight?has_content>
                        "pickWeight": ${container.pickWeight}
                      </#if>
                    }
                  </#if>
                ]
                  <#if pickDetail.pickLocation?has_content>
                    ,"pickLocation": "${pickDetail.pickLocation}"
                  </#if>

                  <#if pickDetail.activations?has_content>
                    ,"activations": [
                  <#--Check if is sequence (or hash)-->
                      <#assign isActivationsSequece = pickDetail.activations?is_sequence>
                      <#if isActivationsSequece>
                          <#list pickDetail.activations as activation>
                            {
                            "activationType": "${activation.activationType}",
                            "activationValue": "${activation.activationValue}",
                            }<#if activation_has_next>,</#if>
                          </#list>
                      <#else>
                          <#assign activation = pickDetail.activations>
                        {
                        "activationType": "${activation.activationType}",
                        "activationValue": "${activation.activationValue}",
                        }
                      </#if>
                    ]
                  </#if>
                }
              </#if>
            ]
          }
      </#if>
      ]
      <#if MessageBody.PickOrderComplete.fulfillmentOrder.carrierBags?has_content>
      ,"carrierBags": {
        "bagCountStartTime": "${MessageBody.PickOrderComplete.fulfillmentOrder.carrierBags.bagCountStartTime}",
        "bagCountEndTime": "${MessageBody.PickOrderComplete.fulfillmentOrder.carrierBags.bagCountEndTime}",
        "bags": [
        <#--Check if is sequence (or hash)-->
        <#assign isBagsSequece = MessageBody.PickOrderComplete.fulfillmentOrder.carrierBags.bags?is_sequence>
        <#if isBagsSequece>
          <#list MessageBody.PickOrderComplete.fulfillmentOrder.carrierBags.bags as bag>
            {
              "bagCount": ${bag.bagCount},
              "containerId": "${bag.containerId}"
            }<#if bag_has_next>,</#if>
          </#list>
        <#else>
          <#assign bag = MessageBody.PickOrderComplete.fulfillmentOrder.carrierBags.bags>
          {
            "bagCount": ${bag.bagCount},
            "containerId": "${bag.containerId}"
          }
        </#if>
        ]
      }
      </#if>
    }
  ]
}