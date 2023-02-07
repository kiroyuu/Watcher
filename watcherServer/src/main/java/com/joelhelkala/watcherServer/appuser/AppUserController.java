package com.joelhelkala.watcherServer.appuser;

import com.joelhelkala.watcherServer.registration.token.ConfirmationTokenService;
import com.joelhelkala.watcherServer.response.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/appuser")
public class AppUserController {

    private final AppUserService userService;
    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    public AppUserController(AppUserService userService, ConfirmationTokenService confirmationTokenService) {
        this.userService = userService;
        this.confirmationTokenService = confirmationTokenService;
    }

    // Endpoint for person GET request which returns all the users
    @GetMapping(path = "admin")
    public List<UserType> getUsers() {
        return userService.getUsers();
    }

    // Endpoint for person POST request which creates a new user
    @PostMapping
    public void registerNewUser(@RequestBody AppUser user) {
        userService.addNewUser(user);
    }

    // Endpoint to delete a user with given id
    @DeleteMapping(path = "admin/{personId}")
    public void deletePerson(@PathVariable("personId") Long id) {
        confirmationTokenService.deleteTokenByUser(id);
        userService.deleteUser(id);
    }

    // Endpoint to update a user with given id
    @PutMapping(path = "admin/{personId}")
    public void updateUser(@PathVariable("personId") Long id,
                             @RequestParam(required = false) String name,
                             @RequestParam(required = false) String email,
                             @RequestParam(required = false) Boolean enabled,
                             @RequestParam(required = false) Boolean locked) {
        userService.updateUserAdmin(id, name, email, enabled, locked);
    }

    @PutMapping(path = "{personId}")
    public ResponseEntity updateUser(@PathVariable("personId") Long id,
                                     @RequestParam() String name,
                                     @RequestParam() String email,
                                     @RequestParam() String description) {
        return userService.updateUser(id, name, email, description);
    }
}
