package com.delivery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.delivery.database.DBConnection;
import com.delivery.domain.User;

public class UserDaoImpl implements UserDao {
	@Override
	public void save(User user) {
		AddressDao dao = new AddressDaoImpl();
		// @TODO: This should be done in one Transaction
		// Now, my address gets saved, but user does not, since password was not provided correctly
		user.setAddress(dao.getOrSave(user.getAddress()));
		Connection conn = DBConnection.getInstance().getConnection();
		try {
			String sql = "insert into user(name, password, email, phone, address_id) values(?, ?, ?, ?, ?);";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getPhone());
			ps.setString(4, user.getEmail());
			ps.setLong(5, user.getAddress().getId());
			ps.executeQuery();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBConnection.getInstance().putConnection(conn);
		}
	}
}
