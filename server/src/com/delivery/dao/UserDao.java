package com.delivery.dao;

import java.util.Set;

import com.delivery.domain.User;

public interface UserDao {
	public void save(User user);
	public Set<User> getAllCustomers();
	public User get(String email);
	public User get(String email, String password);
	public void setAdmin(String email);
}
