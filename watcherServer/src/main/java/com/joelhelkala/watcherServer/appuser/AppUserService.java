package com.joelhelkala.watcherServer.appuser;

import com.joelhelkala.watcherServer.exception.ApiRequestException;
import com.joelhelkala.watcherServer.registration.token.ConfirmationToken;
import com.joelhelkala.watcherServer.registration.token.ConfirmationTokenService;
import com.joelhelkala.watcherServer.response.UserType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

/*
    AppUserService handles the communication to the Repository,
    which contains database commands and calls.
 */
@Service
@AllArgsConstructor
@Slf4j
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND = "user with email %s not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }

    // Returns all the users in the database
    public List<UserType> getUsers() {
        log.info("Getting all users");
        List<AppUser> users = appUserRepository.getUsers();
        List<UserType> userTypeList = new ArrayList<>();
        for(AppUser user : users) {
            userTypeList.add(new UserType(user));
        }
        return userTypeList;
    }

    /*
        Create a new user into the database with unique email
        Generate a token for the user to verify via email
     */
    public String addNewUser(AppUser user) {
        log.info("Adding new user with name: {} to database", user.getName());

        boolean exists = appUserRepository.findByEmail(user.getEmail()).isPresent();
        if (exists) throw new ApiRequestException("email taken");
        String encoded_pass = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encoded_pass);
        appUserRepository.save(user);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15), user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    // Delete a user with given id
    public void deleteUser(Long id) {
        log.info("Deleting user with ID: " + id);

        boolean exists = appUserRepository.existsById(id);
        if(!exists) {
            throw new IllegalStateException(
                    "person with id " + id + " does not exist");
        }
        appUserRepository.deleteById(id);
    }

    // Update users info
    @Transactional
    public void updateUserAdmin(Long id, String name, String email, Boolean enabled, Boolean locked) {
        log.info("Updating user: " + email);

        AppUser person = appUserRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "person with id " + id + " not found" ));

        // Check if name isn't null/empty nor it isn't current name
        if(name != null && name.length() > 0 && !Objects.equals(person.getName(), name)) {
            person.setName(name);
        }

        // Check if email is given, isn't current email & isn't taken
        if(email != null && email.length() > 0 && !Objects.equals(person.getEmail(), email)) {
            Optional<AppUser> optionalPerson = appUserRepository.findByEmail(email);
            if(optionalPerson.isPresent()) throw new ApiRequestException("email taken");
            person.setEmail(email);
        }
        person.setEnabled(enabled);
        person.setLocked(locked);
    }

    // Sets user as enabled when user verifies the registration token
    public int enableUser(String email) {
        return appUserRepository.enableAppUser(email);
    }

    /*
     *  User updates its own details
     */
    @Transactional
    public ResponseEntity updateUser(Long id, String name, String email, String description) {
        log.info("updating user with id: ", id);

        AppUser appUser = appUserRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "person with id: "+ id + " not found" ));

        if (name != null && name.length() > 0 && !Objects.equals(appUser.getName(), name)) {
            appUser.setName(name);
        }

        if (email != null && email.length() > 0 && !Objects.equals(appUser.getEmail(), email)) {
            Optional<AppUser> existingUser = appUserRepository.findByEmail(email);
            if (existingUser.isPresent()) return new ResponseEntity<>(HttpStatus.CONFLICT);
            appUser.setEmail(email);
        }

        // TODO: set description
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
