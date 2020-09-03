package com.delivery.util;

import java.util.LinkedHashSet;

import com.delivery.domain.Meal;
import com.delivery.domain.OrderItem;

public class Cart {
	private static Cart instance;
	private LinkedHashSet<OrderItem> items;

	private Cart() {
		items = new LinkedHashSet<OrderItem>();
	}
	
	public static Cart getInstance() {
		if (instance == null) instance = new Cart();
		
		return instance;
	}
	
	public void add(Meal meal) {
		OrderItem orderItem = new OrderItem(meal, 1);
		items.add(orderItem);
	}

	public void remove(Meal meal) {
		for (OrderItem item: items) {
			// @TODO: equals vs ==
			if (item.getMeal() == meal) {
				items.remove(item);
				break;
			}
		}
	}

	public void increment(Meal meal) {
		for (OrderItem item: items) {
			if (item.getMeal() == meal) {
				item.setQuantity(item.getQuantity()+1);
				break;
			}
		}
	}
	
	public LinkedHashSet<OrderItem> getItems() {
		return items;
	}

	@Override
	public String toString() {
		String output = "";

		if (items.size() > 0) {
			for (OrderItem item: items) {
				output += 
						"Name: " + item.getMeal().getName() + "\n" +
						"Description: " + item.getMeal().getDescription() + "\n" +
						"Price: " + item.getMeal().getPrice() + "\n\n";
			}
		}
		
		return output;
	}
}