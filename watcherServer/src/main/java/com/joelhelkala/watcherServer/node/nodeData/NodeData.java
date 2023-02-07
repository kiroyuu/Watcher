package com.joelhelkala.watcherServer.node.nodeData;

import com.joelhelkala.watcherServer.node.Node;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @NoArgsConstructor
public class NodeData {

    @SequenceGenerator(
            name="nodeData_sequence",
            sequenceName = "nodeData_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "nodeData_sequence"
    )
    private Long id;
    private Float temperature;
    private Integer humidity;
    private Float luminosity;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "parent"
    )
    private Node parent;
    private LocalDateTime measured_at;

    // Constructor for nodeData
    public NodeData(Float temperature, Integer humidity, Float luminosity, Node parent, LocalDateTime time) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.luminosity = luminosity;
        this.parent = parent;
        this.measured_at = time;
    }

    public Float getTemperature() {
        return temperature;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public Float getLuminosity() {
        return luminosity;
    }

    public Node getParent() {
        return parent;
    }

    public LocalDateTime getMeasured_at() {
        return measured_at;
    }
}
