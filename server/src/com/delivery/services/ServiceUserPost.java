package com.delivery.services;

import com.delivery.dao.UserDao;
import com.delivery.dao.UserDaoImpl;
import com.delivery.domain.Request;
import com.delivery.domain.Response;
import com.delivery.domain.User;

public class ServiceUserPost {
	public static void serve(Request request, Response response) {
		java.util.HashMap<String, Object> data = (java.util.HashMap<String, Object>) request.getData();
		User user = (User) data.get("user");
		UserDao dao = new UserDaoImpl();
		dao.save(user);
		response.setStatus(Response.CREATED);
		response.setMessage("User successfully created.");
	}
}
