package com.joelhelkala.watcherServer.node;

import com.joelhelkala.watcherServer.response.NodeCreationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("api/v1/node")
public class NodeController {
    private final NodeService nodeService;

    @Autowired
    public NodeController(NodeService nodeService) { this.nodeService = nodeService; }

    // Endpoint to return all the nodes in database
    @GetMapping
    public List<Node> getNodes() { return nodeService.getNodes(); }

    // Endpoint to add a node to database
    @PostMapping
    public ResponseEntity<Object> addNode(@RequestBody Node node) {
        Node entity = nodeService.saveNode(node);
        return new NodeCreationResponse(HttpStatus.OK, entity.getId(), entity.getLocation(), entity.getDescription(), entity.getLatitude(), entity.getLongitude()).getResponse();
    }

    // Endpoint to update a node within the database
    @PutMapping(path = "{nodeId}")
    public void updateNode(@RequestBody Node node) {
        nodeService.updateNode(node);
    }

    @DeleteMapping(path = "{nodeId}")
    public void deleteNode(@PathVariable("nodeId") Long id) {
        nodeService.deleteNode(id);
    }
}
