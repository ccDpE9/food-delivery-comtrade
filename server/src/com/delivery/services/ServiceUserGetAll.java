package com.delivery.services;

import java.util.Set;

import com.delivery.dao.UserDao;
import com.delivery.dao.UserDaoImpl;
import com.delivery.domain.Request;
import com.delivery.domain.Response;
import com.delivery.domain.User;

public class ServiceUserGetAll extends TransactionTemplate {
	@Override
	public void execute(Request request, Response response) {
		UserDao dao = new UserDaoImpl();
		Set<User> users = dao.getAllCustomers();

		if (users != null) {
			response.setStatus(Response.OK);
			response.setData(users);
			System.out.println(users);
		}
		else {
			response.setStatus(Response.INTERNAL_SERVER_ERROR);
			response.setMessage("Sorry, there was an error trying to fulfill your request. Please try again.");
		}
	}
}
