package com.delivery.services;

import com.delivery.dao.AddressDao;
import com.delivery.dao.AddressDaoImpl;
import com.delivery.dao.CategoryDao;
import com.delivery.dao.CategoryDaoImpl;
import com.delivery.dao.RestaurantDao;
import com.delivery.dao.RestaurantDaoImpl;
import com.delivery.dao.UserDao;
import com.delivery.dao.UserDaoImpl;
import com.delivery.domain.Request;
import com.delivery.domain.Response;
import com.delivery.domain.Restaurant;

public class ServiceRestaurantPost extends TransactionTemplate {
	@Override
	public void execute(Request request, Response response) {
		Restaurant restaurant = (Restaurant) request.getData().get("restaurant");

		AddressDao addressDao = new AddressDaoImpl();
		addressDao.getOrSave(restaurant.getAddress());
		
		CategoryDao categoryDao = new CategoryDaoImpl();
		categoryDao.get(restaurant.getCategory());
		
		UserDao userDao = new UserDaoImpl();
		userDao.setAdmin(restaurant.getAdmin().getEmail());

		RestaurantDao restaurantDao = new RestaurantDaoImpl();
		restaurantDao.save(restaurant);
	}
}
