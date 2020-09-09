package com.delivery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.delivery.database.DBConnection;
import com.delivery.domain.Review;

public class ReviewDaoImpl implements ReviewDao {

	@Override
	public int save(Review review) {
		int result = 0;
		Connection conn = DBConnection.getInstance().getConnection();
		
		try {
			String sql = "insert into review(user_id, restaurant_id, quality, menu_prices, services, message) values (?, ?, ?, ?, ?, ?);";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, review.getUser().getId());
			ps.setLong(2, review.getRestaurent().getId());
			ps.setInt(3, review.getQuality());
			ps.setInt(4, review.getMenuPrices());
			ps.setInt(5, review.getServices());
			ps.setString(6, review.getMessage());
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) result = 1;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
