package com.delivery.services;

import com.delivery.dao.RestaurantDao;
import com.delivery.dao.RestaurantDaoImpl;
import com.delivery.domain.Request;
import com.delivery.domain.Response;
import com.delivery.domain.Restaurant;

public class ServiceRestaurantGet {
	public static void serve(Request request, Response response) {
		RestaurantDao dao = new RestaurantDaoImpl();
		Restaurant restaurant = dao.get(request.getParameter());
		response.setData(restaurant);
	}
}
