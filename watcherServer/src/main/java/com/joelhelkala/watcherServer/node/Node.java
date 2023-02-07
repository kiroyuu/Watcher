package com.joelhelkala.watcherServer.node;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @NoArgsConstructor
public class Node {
    @SequenceGenerator(
            name="node_sequence",
            sequenceName = "node_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "node_sequence"
    )
    private Long id;
    private String location;
    private String description;
    private Float latitude;
    private Float longitude;

    // Node constructor
    public Node(String location, Float latitude, Float longitude, String description) {
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public Long getId() { return id; }

    public void setDescription(String description) {
        this.description = description;
    }
}
