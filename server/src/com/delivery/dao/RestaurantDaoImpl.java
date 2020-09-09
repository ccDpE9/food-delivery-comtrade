package com.delivery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.delivery.database.DBConnection;
import com.delivery.domain.Address;
import com.delivery.domain.Category;
import com.delivery.domain.Meal;
import com.delivery.domain.Restaurant;

public class RestaurantDaoImpl implements RestaurantDao {

	@Override
	public void save(Restaurant restaurant) {
		Connection conn = DBConnection.getInstance().getConnection();
		String sql = "insert into restaurant(name, email, phone, deliveryTime, minimalDelivery, deliveryPrice, address_id, admin_id, category_id) values(?, ?, ?, ?, ?, ?, ?, ?, ?);";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, restaurant.getName());
			ps.setString(2, restaurant.getEmail());
			ps.setString(3, restaurant.getPhone());
			ps.setString(4, restaurant.getDeliveryTime());
			ps.setString(5, restaurant.getMinimalDelivery());
			ps.setString(6, restaurant.getDeliveryPrice());
			ps.setLong(7, restaurant.getAddress().getId());
			ps.setLong(8, restaurant.getAdmin().getId());
			ps.setInt(9, restaurant.getCategory().getId());
			ps.execute();
			
			conn.commit();
		} 
		catch (SQLException e) {
			try {
				e.printStackTrace();
				conn.rollback();
			}
			catch (SQLException err) {
				err.printStackTrace();
			}
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

				Category cuisine = new Category();
				cuisine.setName(rs.getNString("cuisine.name"));

				Restaurant restaurant = new Restaurant();
				restaurant.setName(rs.getString("name"));
				restaurant.setEmail(rs.getString("email"));
				restaurant.setPhone(rs.getString("phone"));
				restaurant.setDeliveryTime(rs.getString("deliveryTime"));
				restaurant.setAddress(address);
				restaurant.setCategory(cuisine);

				restaurants.add(restaurant);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return restaurants;
	}

	@Override
	public Restaurant get(String name) {
		Restaurant restaurant = new Restaurant(); 

		Connection conn = DBConnection.getInstance().getConnection();
		String sql = String.format("select * from restaurant "
				+ "inner join address on restaurant.address_id = address.id "
				+ "inner join cuisine on restaurant.cuisine_id = cuisine.id "
				+ "where restaurant.name = \"%s\";", name);
		String mealSql = String.format("select meal.name from meal "
				+ "inner join restaurant on restaurant.id = meal.restaurant_id "
				+ "where restaurant.name = \"%s\";", name);
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Address address = new Address();
				address.setCity(rs.getString("address.city"));
				address.setStreet(rs.getString("address.street"));
				address.setStreetNumber(rs.getString("address.streetNumber"));

				Category cuisine = new Category();
				cuisine.setName(rs.getNString("cuisine.name"));

				restaurant.setName(rs.getString("name"));
				restaurant.setEmail(rs.getString("email"));
				restaurant.setPhone(rs.getString("phone"));
				restaurant.setDeliveryTime(rs.getString("deliveryTime"));
				restaurant.setAddress(address);
				restaurant.setCategory(cuisine);

				List<Meal> meals = new ArrayList<>();
				ps = conn.prepareStatement(mealSql);
				rs = ps.executeQuery();
				while(rs.next()) {
					Meal meal = new Meal();
					meal.setName(rs.getString("meal.name"));
					meals.add(meal);
				}
				restaurant.setMeals(meals);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return restaurant;
	}
}