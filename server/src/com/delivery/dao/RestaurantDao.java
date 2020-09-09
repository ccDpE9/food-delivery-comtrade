package com.delivery.dao;

import java.util.List;

import com.delivery.domain.Restaurant;

public interface RestaurantDao {
	public void save(Restaurant restaurant);
	public List<Restaurant> get();
	public Restaurant get(String name);
}
