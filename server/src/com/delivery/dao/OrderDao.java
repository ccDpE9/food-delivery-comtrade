package com.delivery.dao;

import java.util.Set;

import com.delivery.domain.OrderItem;

public interface OrderDao {
	public void save(Set<OrderItem> items, long user);
}
