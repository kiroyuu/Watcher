package com.joelhelkala.watcherGui.User;

import com.joelhelkala.watcherGui.User.Roles.Role;

// Helper class for user details
public class User {
	
	private static String name;
	private static String email;
	private static String token;
	private static Role role;
	
	
	public User(String name, String email, String password) {
		User.name = name;
		User.email = email;
		User.token = null;
		User.role = null;
	}
	
	public static String getName() {
		return name;
	}
	
	public static void setName(String name) {
		User.name = name;
	}

	public static String getEmail() {
		return email;
	}

	public static void setEmail(String email) {
		User.email = email;
	}
	
	public static void setToken(String token) {
		User.token = token;
	}
	
	public static String getToken() {
		return token;
	}
	
	public static void clear() {
		User.name = null;
		User.email = null;
		User.token = null;
	}

	public static Role getRole() {
		return role;
	}

	public static void setRole(Role role) {
		User.role = role;
	}
}
