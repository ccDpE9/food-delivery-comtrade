package com.delivery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.delivery.database.DBConnection;
import com.delivery.domain.Address;
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
	}

	@Override
	public User get(String email) {
		User user = null;
		Connection conn = DBConnection.getInstance().getConnection();

		try {
			String sql = "select * from user where email = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				long id = rs.getLong("id");
				user = new User();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	@Override
	public User get(String email, String password) {
		User user = null; 

		Connection conn = DBConnection.getInstance().getConnection();
		try {
			String sql = "select u.id, u.name, u.password, u.email, u.phone, u.role, a.id, a.city, a.street, a.streetNumber from user as u inner join address as a on u.address_id = a.id where email = ? and password = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Address address = new Address(rs.getLong("a.id"), rs.getString("a.city"), rs.getString("a.street"), rs.getString("a.streetNumber"));
				user = new User(rs.getLong("u.id"), rs.getString("u.name"), rs.getString("u.password"), rs.getString("u.email"), rs.getString("u.phone"), address, rs.getString("u.role"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	@Override
	public void setAdmin(String email) {
		Connection conn = DBConnection.getInstance().getConnection();

		try {
			String sql = "update user set role = \"admin\" where email = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ps.executeQuery();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Set<User> getAllCustomers() {
		Set<User> users = new HashSet<>();
		Connection conn = DBConnection.getInstance().getConnection();

		try {
			String sql = "select * from user as u inner join address as a on u.address_id = a.id where role = \"Customer\";";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Address address = new Address(
						rs.getLong("u.id"),
						rs.getString("a.city"),
						rs.getString("a.street"),
						rs.getString("a.streetNumber")
						);

				User user = new User(
						rs.getLong("u.id"),
						rs.getString("u.name"),
						rs.getString("u.password"),
						rs.getString("u.email"),
						rs.getString("u.phone"),
						address,
						rs.getString("u.role")
						);

				users.add(user);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}
}