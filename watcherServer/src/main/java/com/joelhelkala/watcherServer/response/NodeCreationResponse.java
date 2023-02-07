package com.joelhelkala.watcherServer.response;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class NodeCreationResponse implements IResponse{
    private final HttpStatus httpStatus;
    private final Long id;
    private final String location;
    private final String description;
    private final Float latitude;
    private final Float longitude;

    // Create the response for client
    public ResponseEntity<Object> getResponse() {
        Map<String, String> body = createBody();
        ResponseEntity<Object> response = new ResponseEntity<>(body, httpStatus);
        return response;
    }

    // Generate a map from node attributes
    private Map<String, String> createBody() {
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(this.id));
        map.put("location", this.location);
        map.put("description", this.description);
        map.put("latitude", String.valueOf(this.latitude));
        map.put("longitude", String.valueOf(this.longitude));
        return map;
    }
}
