package com.delivery.services;

import java.util.HashMap;
import java.util.Set;

import com.delivery.dao.OrderDao;
import com.delivery.dao.OrderDaoImpl;
import com.delivery.dao.UserDao;
import com.delivery.dao.UserDaoImpl;
import com.delivery.domain.OrderItem;
import com.delivery.domain.Request;
import com.delivery.domain.Response;
import com.delivery.domain.User;

public class ServiceOrderPost {
	public static void serve(Request request, Response response) {
		OrderDao orderDao = new OrderDaoImpl();
		UserDao userDao = new UserDaoImpl();
		
		User user = userDao.get(request.getSession().get("email"), request.getSession().get("password"));
		if (user != null) {
			HashMap<String, Object> data = (HashMap<String, Object>) request.getData();
			Set<OrderItem> items = (Set<OrderItem>) data.get("items");
			orderDao.save(items, user.getId());
			response.setMessage("Your order has been created successfully.");
		}
		else {
			response.setMessage("Could not find a user with provided email/password.");
		}
	}
}
