package com.delivery.domain;

public class User {
	private int id;
	private String name;
	private int phone;
	private Address address;
	private Role role;
	
	public User() {}
	
	public User(int id, String name, int phone, Address address, Role role) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
