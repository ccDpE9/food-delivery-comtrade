package com.delivery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.delivery.database.DBConnection;
import com.delivery.domain.Meal;

public class MealDaoImpl implements MealDao {
	@Override
	public Meal get(String name) {
		Meal meal = new Meal(); 

		Connection conn = DBConnection.getInstance().getConnection();
		String sql = String.format("select * from meal where name=\"%s\";", name);
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				meal.setId(rs.getLong("id"));
				meal.setName(rs.getString("name"));
				meal.setDescription(rs.getString("description"));
				meal.setPrice(rs.getDouble("price"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return meal;
	}
}
