package com.delivery.services;

import com.delivery.dao.UserDao;
import com.delivery.dao.UserDaoImpl;
import com.delivery.domain.Request;
import com.delivery.domain.Response;
import com.delivery.domain.User;

public class ServiceUserLogin {
	public static void serve(Request request, Response response) {
		UserDao dao = new UserDaoImpl();
		String email = request.getSession().get("email");
		String password = request.getSession().get("password");
		User user = dao.get(email, password);

		if (user == null) {
			response.setStatus(Response.UNAUTHORIZED);
			response.setMessage("Invalid email/password.");
		}
		else {
			response.setData(user);
			response.setStatus(Response.OK);
		}
	}
}
