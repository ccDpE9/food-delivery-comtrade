package com.delivery.dao;

import java.util.Set;

import com.delivery.domain.Category;

public interface CategoryDao {
	public Set<Category> getAll(String type);
	public Category get(String category);
	public Category get(Category category);
}
