package com.delivery.services;

import java.util.List;

import com.delivery.dao.RestaurantDao;
import com.delivery.dao.RestaurantDaoImpl;
import com.delivery.domain.Request;
import com.delivery.domain.Response;
import com.delivery.domain.Restaurant;

public class ServiceRestaurantGetAll {
	public static void serve(Request request, Response response) {
		RestaurantDao dao = new RestaurantDaoImpl();
		List<Restaurant> restaurants = dao.get();
		
		response.setData(restaurants);
	}
}
