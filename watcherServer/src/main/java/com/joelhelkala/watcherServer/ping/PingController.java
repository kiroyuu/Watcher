package com.joelhelkala.watcherServer.ping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/ping")
public class PingController {

    @GetMapping
    public String ping() {
        return "it works";
    }
}
