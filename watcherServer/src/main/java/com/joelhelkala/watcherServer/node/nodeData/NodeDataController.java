package com.joelhelkala.watcherServer.node.nodeData;

import com.joelhelkala.watcherServer.response.NodeDataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController @RequestMapping("api/v1/nodeData")
public class NodeDataController {
    private final NodeDataService nodeDataService;

    @Autowired
    public NodeDataController(NodeDataService nodeDataService) { this.nodeDataService = nodeDataService; }

    /*
        Adds a measurement data for given parent node
     */
    @PostMapping
    public ResponseEntity<Object> addNodeData(@RequestParam Float temperature, @RequestParam Integer humidity,
                                              @RequestParam Float luminosity, @RequestParam Long parent_id) {
        return nodeDataService.addData(temperature, humidity, luminosity, parent_id);
    }

    /*
        Gets all the data history from given parent node
     */
    @GetMapping(path = "{parent_id}")
    public ResponseEntity<Object> getNodeData(@PathVariable("parent_id") Long parent_id) {
        Collection<NodeData> data = nodeDataService.getNodeData(parent_id);
        return new NodeDataResponse(HttpStatus.OK, data).getResponse();
    }
}
