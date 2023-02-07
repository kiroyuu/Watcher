package com.joelhelkala.watcherServer.response;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class RegistrationResponse implements IResponse{
    private final HttpStatus httpStatus;
    private final String name;
    private final String email;
    private final String token;
    private String message;

    public ResponseEntity<Object> getResponse() {
        Map<String, String> body = createBody();
        ResponseEntity<Object> response = new ResponseEntity<>(body, httpStatus);
        return response;
    }

    /*
        Create a map from user details
     */
    private Map<String, String> createBody() {
        Map<String, String> map = new HashMap<>();
        map.put("name", this.name);
        map.put("email", this.email);
        map.put("token", this.token);
        map.put("message", this.message);
        return map;
    }
}
