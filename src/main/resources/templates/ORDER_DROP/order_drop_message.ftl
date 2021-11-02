<#setting time_zone="${routingInfo.sourceNode.location.timezone}">
<PickingSubSystemOrderReleaseMessage xmlns="http://www.xmlns.walmartstores.com/SuppyChain/FulfillmentManagement/GlobalIntegeratedFulfillment/Picking/PickingSubSystemOrderReleaseMessage/1.0/" xmlns:hdr="http://www.xmlns.walmartstores.com/Header/datatypes/MessageHeader/1.4/">
  <hdr:MessageHeader>
    <hdr:SubId>SUB-EIC-UPDPICK-V1</hdr:SubId>
    <hdr:CnsmrId>CON-NODE-UPDPICK-V1</hdr:CnsmrId>
    <hdr:SrvcNm>UpdateFulfillmentPicking.subSystemOrderRelease</hdr:SrvcNm>
    <hdr:TranId>${order.orderId}</hdr:TranId>
    <hdr:Version>1.0</hdr:Version>
  </hdr:MessageHeader>
  <MessageBody>
    <RoutingInfo>
      <SourceNode>
        <location>
          <countryCode>${routingInfo.sourceNode.location.countryCode}</countryCode>
        </location>
        <nodeID>${routingInfo.sourceNode.nodeId}</nodeID>
      </SourceNode>
      <DestinationNode>
        <location>
          <countryCode>${routingInfo.destinationNode.location.countryCode}</countryCode>
        </location>
        <nodeID>${routingInfo.destinationNode.nodeId}</nodeID>
        <cNodeID>ALPHA</cNodeID>
      </DestinationNode>
    </RoutingInfo>
    <Order>
      <node>
        <nodeId>${routingInfo.destinationNode.nodeId}</nodeId>
        <countryCode>${routingInfo.destinationNode.location.countryCode}</countryCode>
      </node>
      <fulfillmentOrder>
        <orderNbr>${order.orderId}</orderNbr>
        <orderPriority>
          <#if order.orderPriority=="REGULAR">
          <code>1</code>
          <#else>
          <code>2</code>
          </#if>
          <description>Grocery Pickup Default</description>
        </orderPriority>
        <type code="6" name="${order.orderPriority}"/>
        <destinationBusinessUnit destBannerName="Walmart Grocery" destDivisonNumber="${order.divisionNbr}"/>
        <pickDueTime>${order.pickDueTime?datetime.iso?string["yyyy-MM-dd'T'HH:mm:ss.SSS Z"]}</pickDueTime>
        <expectedOrderPickupTime>${order.expectedOrderPickupTime?datetime.iso?string["yyyy-MM-dd'T'HH:mm:ss.SSS Z"]}</expectedOrderPickupTime>
          <#if order.earliestPickTime?has_content>
            <earliestPickTime>${order.earliestPickTime?datetime.iso?string["yyyy-MM-dd'T'HH:mm:ss.SSS Z"]}</earliestPickTime>
          </#if>
        <orderSequenceNumber>${order.osn}</orderSequenceNumber>
        <loadGroupNumber>${order.loadNbr}</loadGroupNumber>
        <carrierBagAllowed>${order.carrierBagAllowed?string}</carrierBagAllowed>
        <recordCarrierBagCount>${order.recordCarrierBagCount?string}</recordCarrierBagCount>

        <lines>
            <#list order.lines as line>
              <line>
                <lineNbr>${line.lineNbr}</lineNbr>
                <upc>${line.gtin}</upc>
                <#if line.pickByType=="WEIGHTED">
                <qty>${line.pickWeightQty}</qty>
                <#else>
                <qty>${line.pickEachQty}</qty>
                </#if>
                <#if line.pickWeightQty?has_content>
                <weight>${line.pickWeightQty}</weight>
                </#if>
                <pickByType>${line.pickByType}</pickByType>
                <substitutionAllowed></substitutionAllowed>
                <itemNbr>${line.gtin}</itemNbr>
                <#if line.pickByType=="WEIGHTED">
                <uom>${line.pickWeightUom}</uom>
                <#else>
                <uom>${line.pickEachUom}</uom>
                </#if>
              </line>
            </#list>
        </lines>
      </fulfillmentOrder>
    </Order>
  </MessageBody>
</PickingSubSystemOrderReleaseMessage>
