package com.delivery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.delivery.database.DBConnection;
import com.delivery.domain.Address;
import com.delivery.domain.Cuisine;
import com.delivery.domain.Restaurant;

public class RestaurantDaoImpl implements RestaurantDao {

	@Override
	public void save(Restaurant restaurant) {
		// @TODO: AddressDao addressDao = new AddressDaoImpl().getOrSave(restaurt.getAddress());
		Connection conn = DBConnection.getInstance().getConnection();
		String sql = "insert into restaurant(name, email, phone, address, deliveryTime) values(?,?,?,?,?);";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, restaurant.getName());
			ps.setString(2, restaurant.getEmail());
			ps.setString(3, restaurant.getPhone());
			ps.setLong(4, restaurant.getAddress().getId());
			ps.setString(5, restaurant.getDeliveryTime());
			ps.execute();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBConnection.getInstance().putConnection(conn);
		}
	}

	@Override
	public List<Restaurant> get() {
		List<Restaurant> restaurants = new ArrayList<>();

		Connection conn = DBConnection.getInstance().getConnection();
		String sql = "select * from restaurant inner join address on restaurant.address_id = address.id inner join cuisine on restaurant.cuisine_id = cuisine.id;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Address address = new Address();
				address.setCity(rs.getString("city"));
				address.setStreet(rs.getString("street"));
				address.setStreetNumber(rs.getString("streetNumber"));
				
				Cuisine cuisine = new Cuisine();
				cuisine.setName(rs.getNString("cuisine.name"));

				Restaurant restaurant = new Restaurant();
				restaurant.setName(rs.getString("name"));
				restaurant.setEmail(rs.getString("email"));
				restaurant.setPhone(rs.getString("phone"));
				restaurant.setDeliveryTime(rs.getString("deliveryTime"));
				restaurant.setAddress(address);
				restaurant.setCuisine(cuisine);

				restaurants.add(restaurant);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBConnection.getInstance().putConnection(conn);
		}

		return restaurants;
	}
}
