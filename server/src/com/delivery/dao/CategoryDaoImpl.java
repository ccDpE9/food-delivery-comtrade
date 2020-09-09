package com.delivery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.delivery.database.DBConnection;
import com.delivery.domain.Category;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public Set<Category> getAll(String type) {
		Set<Category> categories = new HashSet<>();
		Connection conn = DBConnection.getInstance().getConnection();
		
		try {
			String sql = "select * from category where type = \"" + type + "\";";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(); 
			
			while (rs.next()) {
				Category category = new Category(rs.getInt("id"), rs.getString("type"), rs.getString("name"));
				categories.add(category);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return categories;
	}

	@Override
	public Category get(String category) {
		Category result = null;
		
		Connection conn = DBConnection.getInstance().getConnection();
		String sql = "select * from category where name = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, category);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				result = new Category(rs.getInt("id"), rs.getString("name"), rs.getString("type"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public Category get(Category category) {
		Connection conn = DBConnection.getInstance().getConnection();
		String sql = "select * from category where name = ? and type = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, category.getName());
			ps.setString(2, category.getType());
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				category.setId(rs.getInt("id"));
				category.setType(rs.getString("type"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return category;
	}
}
