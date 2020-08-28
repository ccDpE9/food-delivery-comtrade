package com.delivery.dao;

import com.delivery.domain.Address;

public interface AddressDao {
	public Address getOrSave(Address address);
}
