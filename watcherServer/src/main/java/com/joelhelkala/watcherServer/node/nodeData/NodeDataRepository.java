package com.joelhelkala.watcherServer.node.nodeData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface NodeDataRepository extends JpaRepository<NodeData, Long> {

    @Query(
            value = "SELECT * FROM node_data n WHERE n.parent = ?1",
            nativeQuery = true )
    Collection<NodeData> findAllByParent(Long parent_id);
}
