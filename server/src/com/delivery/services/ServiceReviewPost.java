package com.delivery.services;

import com.delivery.dao.ReviewDao;
import com.delivery.dao.ReviewDaoImpl;
import com.delivery.domain.Request;
import com.delivery.domain.Response;
import com.delivery.domain.Review;

public class ServiceReviewPost {
	public static void serve(Request request, Response response) {
		ReviewDao dao = new ReviewDaoImpl();
		int result = dao.save((Review) request.getData().get("review"));

		if (result == 0) response.setMessage("There was an error saving your review, please try again.");
		else response.setMessage("Your review has been successfully saved.");
	}
}
