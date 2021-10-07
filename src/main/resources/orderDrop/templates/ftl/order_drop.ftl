<MessageBody>
    <RoutingInfo>
      <SourceNode>
        <location>
          <countryCode>${body.routingInfo}</countryCode>
        </location>
        <nodeID>${body.routingInfo.sourceNode.nodeId}</nodeID>
      </SourceNode>
    </RoutingInfo>
</MessageBody>
    