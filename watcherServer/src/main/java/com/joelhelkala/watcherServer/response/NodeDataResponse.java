package com.joelhelkala.watcherServer.response;

import com.joelhelkala.watcherServer.node.nodeData.NodeData;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

@AllArgsConstructor
public class NodeDataResponse implements IResponse{

    private final HttpStatus httpStatus;
    private final Collection<NodeData> data;

    // Create the response for client
    public ResponseEntity<Object> getResponse() {
        List<NodeData> body = createBody();
        ResponseEntity<Object> response = new ResponseEntity<>(body, httpStatus);
        return response;
    }

    // Make a list of the nodes in data
    private List<NodeData> createBody() {
        List<NodeData> nodes = new ArrayList<>();
        for(NodeData node : data) {
            nodes.add(node);
        }
        return nodes;
    }
}
