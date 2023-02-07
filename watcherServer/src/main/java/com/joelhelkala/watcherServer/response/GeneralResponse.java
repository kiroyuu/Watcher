package com.joelhelkala.watcherServer.response;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class GeneralResponse implements IResponse {
    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public ResponseEntity<Object> getResponse() {
        Map<String, String> body = new HashMap<>();
        body.put("message", message);
        body.put("status", String.valueOf(httpStatus.value()));
        ResponseEntity<Object> response = new ResponseEntity<>(body, httpStatus);
        return response;
    }
}