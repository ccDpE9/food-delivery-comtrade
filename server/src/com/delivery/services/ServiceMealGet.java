package com.delivery.services;

import com.delivery.dao.MealDao;
import com.delivery.dao.MealDaoImpl;
import com.delivery.domain.Meal;
import com.delivery.domain.Request;
import com.delivery.domain.Response;

public class ServiceMealGet {
	public static void serve(Request request, Response response) {
		MealDao dao = new MealDaoImpl();
		Meal meal = dao.get(request.getParameter());
		response.setData(meal);
	}
}
