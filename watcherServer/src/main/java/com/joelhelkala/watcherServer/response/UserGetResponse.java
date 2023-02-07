package com.joelhelkala.watcherServer.response;

import com.joelhelkala.watcherServer.appuser.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserGetResponse implements IResponse {
    private final HttpStatus httpStatus;
    private List<AppUser> users;

    public UserGetResponse(HttpStatus status, List<AppUser> users) {
        this.users = users;
        httpStatus = status;
    }

    @Override
    public ResponseEntity<Object> getResponse() {
        List<UserType> body = createList();
        ResponseEntity<Object> response = new ResponseEntity<>(body, httpStatus);
        return response;
    }

    private List<UserType> createList() {
        List<UserType> userTypeList = new ArrayList<>();
        for(int i = 0; i < users.size(); i++) {
            UserType user = new UserType(users.get(i));
            userTypeList.add(user);
        }
        return userTypeList;
    }
}
