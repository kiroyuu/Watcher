package com.joelhelkala.watcherServer.response;

import com.joelhelkala.watcherServer.appuser.AppUser;
import com.joelhelkala.watcherServer.appuser.AppUserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class UserType {
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private AppUserRole role;
    private Long id;
    private Boolean locked = false;
    private Boolean enabled = false;

    public UserType(AppUser user) {
        name = user.getName();
        email = user.getEmail();
        role = user.getRole();
        locked = user.getLocked();
        enabled = user.getEnabled();
        id = user.getId();
    }
}
