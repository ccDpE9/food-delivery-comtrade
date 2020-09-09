package com.delivery.services;

import java.util.Set;

import com.delivery.dao.CategoryDao;
import com.delivery.dao.CategoryDaoImpl;
import com.delivery.domain.Category;
import com.delivery.domain.Request;
import com.delivery.domain.Response;

public class ServiceCategoryGetAll extends TransactionTemplate {
	@Override
	public void execute(Request request, Response response) {
		CategoryDao dao = new CategoryDaoImpl();
		Set<Category> categories = dao.getAll(request.getParameter());

		response.setData(categories);
	}
}
