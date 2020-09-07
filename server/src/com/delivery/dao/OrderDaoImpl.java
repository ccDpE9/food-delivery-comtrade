package com.delivery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import com.delivery.database.DBConnection;
import com.delivery.domain.Order;
import com.delivery.domain.OrderItem;

public class OrderDaoImpl implements OrderDao {

	@Override
	public void save(Set<OrderItem> items, long user) {
		Connection conn = DBConnection.getInstance().getConnection();
		
		try {
			String sql = "insert into orders(user_id) values(?);";
			PreparedStatement ps = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, String.valueOf(user));
			ps.executeQuery();
			ResultSet generatedKeys = ps.getGeneratedKeys();
			if (generatedKeys.next()) {
				long orderId = generatedKeys.getLong(1);
				for (OrderItem item: items) {
					sql = "insert into order_item(order_id, meal_id, quantity) values(?, ?, ?);";
					ps = conn.prepareStatement(sql);
					ps.setLong(1, orderId);
					ps.setLong(2, item.getMeal().getId());
					ps.setInt(3, item.getQuantity());
					System.out.println("Meal id: " + item.getMeal().getId());
					ps.executeQuery();
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBConnection.getInstance().putConnection(conn);
		}
	}

}
