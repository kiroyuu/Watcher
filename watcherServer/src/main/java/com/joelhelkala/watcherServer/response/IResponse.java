package com.joelhelkala.watcherServer.response;

import org.springframework.http.ResponseEntity;

public interface IResponse {
    ResponseEntity<Object> getResponse();
}
