package com.joelhelkala.watcherServer.node;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NodeRepository extends JpaRepository<Node, Long> {

    @Query("SELECT n FROM Node n WHERE n.location = ?1")
    Optional<Object> findByLocation(String location);

    @Query("SELECT n FROM Node n WHERE n.id = ?1")
    Optional<Node> findById(Long id);
}
