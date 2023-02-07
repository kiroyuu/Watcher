package com.joelhelkala.watcherServer.node.nodeData;

import com.joelhelkala.watcherServer.exception.ApiRequestException;
import com.joelhelkala.watcherServer.node.Node;
import com.joelhelkala.watcherServer.node.NodeRepository;
import com.joelhelkala.watcherServer.response.GeneralResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service @AllArgsConstructor @Slf4j
public class NodeDataService {
    private final NodeDataRepository nodeDataRepository;
    private final NodeRepository nodeRepository;

    /*
        Checks if parent node exists and then creates the dataset into the database
     */
    public ResponseEntity<Object> addData(Float temperature, Integer humidity, Float luminosity, Long parent_id) {
        log.info("adding sensor data for node with id: {}", parent_id);

        Node node = nodeRepository.findById(parent_id).orElseThrow(
                () -> new ApiRequestException("Parent node not found with id: " + parent_id));

        LocalDateTime time = LocalDateTime.now();
        NodeData data = new NodeData(temperature, humidity, luminosity, node, time);
        nodeDataRepository.save(data);
        return new GeneralResponse(HttpStatus.OK, "Node data added successfully.").getResponse();
    }

    // Checks if the parent node exists and then asks the database for all nodedata
    public Collection<NodeData> getNodeData(Long parent_id) {
        log.info("Getting all data history from node {}",parent_id);
        nodeRepository.findById(parent_id).orElseThrow(
                () -> new ApiRequestException("Parent node not found with id : " + parent_id));
        return nodeDataRepository.findAllByParent(parent_id);
    }
}
