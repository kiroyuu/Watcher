package com.joelhelkala.watcherServer.registration;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
    Endpoint for registering a new user into the database
    The user will need to have unique email
    Server will send a registration token for the user
    User will use this token to verify its email
    Once the user verifies via email, the user will be flagged as enabled
 */
@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    // Handles the first POST request to add the user
    @PostMapping
    public ResponseEntity<Object> register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    // Handles the email verification with given token
    @GetMapping(path = "confirm")
    public String confirm (@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
