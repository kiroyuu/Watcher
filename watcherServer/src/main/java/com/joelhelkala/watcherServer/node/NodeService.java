package com.joelhelkala.watcherServer.node;

import com.joelhelkala.watcherServer.exception.ApiRequestException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class NodeService {
    private final NodeRepository nodeRepository;

    // Saves a given node to database
    public Node saveNode(Node node) {
        log.info("Adding new node with name : {}, to the database", node.getLocation());

        boolean exists = nodeRepository.findByLocation(node.getLocation()).isPresent();
        if (exists) throw new ApiRequestException("Node with name " + node.getLocation() + " already exists");
        Node entity = nodeRepository.save(node);
        return entity;
    }

    // Gets a node with given ID
    public Optional<Node> getNode(Long id) { return nodeRepository.findById(id); }

    // Gets all the nodes from database
    public List<Node> getNodes() {
        return nodeRepository.findAll();
    }

    // Update a node with given id
    @Transactional
    public void updateNode(Node node) {
        Long id = node.getId();
        if(id == null) throw new ApiRequestException("given id was null");
        log.info("updating node with id: {}", id);

        Node found_node = nodeRepository.findById(id).orElseThrow(() -> new ApiRequestException("Couldn't find node with id: " + id));
        found_node.setDescription(node.getDescription());
        found_node.setLocation(node.getLocation());
        found_node.setLatitude(node.getLatitude());
        found_node.setLongitude(node.getLongitude());
    }

    // Delete node with given id
    public void deleteNode(Long id) {
        log.info("Deleting node with id: {}",id);

        boolean exist = nodeRepository.existsById(id);
        if(!exist) {
            log.error("Cannot delete node. Node with id {} not found.", id);
            throw new ApiRequestException("Cannot find node with id: " + id);
        }
        nodeRepository.deleteById(id);
    }
}
