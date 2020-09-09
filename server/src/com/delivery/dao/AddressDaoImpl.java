package com.delivery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.delivery.database.DBConnection;
import com.delivery.domain.Address;

public class AddressDaoImpl implements AddressDao {

	@Override
	public Address getOrSave(Address address) {
		Connection conn = DBConnection.getInstance().getConnection();
		try {
            String sql = "select * from address where city = ? and street = ? and streetNumber = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, address.getCity());
            ps.setString(2, address.getStreet());
            ps.setString(3, address.getStreetNumber());
            ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				sql = "insert into address(city, street, streetNumber) values(?, ?, ?);";
				ps = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, address.getCity());
				ps.setString(2, address.getStreet());
				ps.setString(3, address.getStreetNumber());
				ps.executeQuery();
				ResultSet generatedKeys = ps.getGeneratedKeys();
				if (generatedKeys.next()) {
					address.setId(generatedKeys.getLong(1));
				}
			}
			else {
				address.setId(rs.getLong("id"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return address;
	}

}
