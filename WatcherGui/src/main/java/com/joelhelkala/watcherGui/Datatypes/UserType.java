package com.joelhelkala.watcherGui.Datatypes;


import com.joelhelkala.watcherGui.User.Roles.Role;

public class UserType {
	private String name;
	private String email;
	private Boolean enabled;
	private Boolean locked;
	private Role role;
	private Long id;
	
	public UserType(String name, String email, Boolean enabled, Boolean locked, Role role, Long id) {
		this.name = name;
		this.email = email;
		this.enabled = enabled;
		this.locked = locked;
		this.role = role;
		this.id = id;
	}
	
	// GETTERS
	public String getName() { return name; }
	public String getEmail() { return email; }
	public Boolean getEnabled() { return enabled; }
	public Boolean getLocked() { return locked; }
	public Role getRole() { return role; }
	public Long getId() { return id; }
}
