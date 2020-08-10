package com.delivery.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.delivery.domain.Address;
import com.delivery.domain.Cuisine;
import com.delivery.domain.Request;
import com.delivery.domain.Restaurant;
import com.delivery.domain.RequestResource;
import com.delivery.domain.Response;
import com.delivery.domain.RequestMethod;
import com.delivery.transfer.Fetch;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Restaurants extends JFrame {
	DefaultListModel<Restaurant> dlmRestaurant = new DefaultListModel<>();
	DefaultListModel<String> dlmCuisine = new DefaultListModel<>();

	private JPanel contentPane;
	private JTextField searchTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Restaurants frame = new Restaurants();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws ClassNotFoundException 
	 */
	public Restaurants() throws ClassNotFoundException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 780, 381);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// --- Search component

		JLabel lblNewLabel = new JLabel("Search for restaurant");
		lblNewLabel.setBounds(171, 24, 186, 15);
		contentPane.add(lblNewLabel);

		searchTextField = new JTextField();
		searchTextField.setBounds(119, 51, 238, 19);
		contentPane.add(searchTextField);
		searchTextField.setColumns(10);

		JButton searchBtn = new JButton("Search");
		searchBtn.setBounds(378, 48, 98, 25);
		contentPane.add(searchBtn);
		
		// --- Restaurants component

		JLabel lblNewLabel_2 = new JLabel("Restaurants");
		lblNewLabel_2.setBounds(53, 130, 279, 15);
		contentPane.add(lblNewLabel_2);

		JList restaurantList = new JList();
		restaurantList.setBounds(53, 159, 423, 155);
		contentPane.add(restaurantList);

		JButton restaurantBtn = new JButton("Select Restaurant");
		restaurantBtn.setBounds(191, 332, 98, 25);
		contentPane.add(restaurantBtn);

		// --- Cuisines component

		JLabel lblNewLabel_1 = new JLabel("Cuisines");
		lblNewLabel_1.setBounds(525, 130, 107, 15);
		contentPane.add(lblNewLabel_1);

		JList cuisinesList = new JList();
		cuisinesList.setBounds(525, 159, 107, 154);
		contentPane.add(cuisinesList);

		JButton filterBtn = new JButton("Filter");
		filterBtn.setBounds(525, 332, 98, 25);
		contentPane.add(filterBtn);

		// --- Scripting

		Request request = new Request();
		request.setResource(RequestResource.RESTAURANT);
		request.setMethod(RequestMethod.GET);

		// I can do this by requesting all cuisines
		// But, i feel like this is better, because in real production, you would want to delegate as much of worload on the client as possible
		java.util.Set<String> cuisines = new LinkedHashSet<>();
		// Why LinkedHashSet? I want "Any" to come first, and LinkedHashSet perserves original order.
		cuisines.add("Any");

		Response response = Fetch.getInstance().send(request);
		List<Restaurant> restaurants = (List<Restaurant>) response.getData();

		for (Restaurant restaurant: restaurants) {
			dlmRestaurant.addElement(restaurant);
			cuisines.add(restaurant.getCuisine().getName());
		}

		for (String cuisine: cuisines) {
			dlmCuisine.addElement(cuisine);
		}

		restaurantList.setModel(dlmRestaurant);
		cuisinesList.setModel(dlmCuisine);

		restaurantBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// @TODO: redirect to RestaurantDetail page
				System.out.println(restaurantList.getSelectedValue().toString());
			}
		});

		filterBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String cuisine = cuisinesList.getSelectedValue().toString();
				dlmRestaurant.clear();

				for (Restaurant restaurant: restaurants) {
					if (restaurant.getCuisine().getName().equals(cuisine)) {
						dlmRestaurant.addElement(restaurant);
					} else if(cuisine.equals("Any")) {
						dlmRestaurant.addElement(restaurant);
					}
				}

				restaurantList.setModel(dlmRestaurant);
			}
		});
		
		searchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String term = searchTextField.getText();
				dlmRestaurant.clear();

				for (Restaurant restaurant: restaurants) {
					if (restaurant.getName().contains(term)) dlmRestaurant.addElement(restaurant);
				}
				restaurantList.setModel(dlmRestaurant);
			}
		});
	}
}
