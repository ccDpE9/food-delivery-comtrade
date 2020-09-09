package com.delivery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private Connection connection;
	private volatile static DBConnection instance;
	private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
	private static final String DB_URL = "jdbc:mariadb://localhost:3306/delivery_se";
	/*
	private static final int MAX_CON = 1;
	private static final Connection[] buffer = new Connection[MAX_CON];
	private int first = 0;
	private int last = 0;
	private int free = MAX_CON;
	
	private DBConnection() {
		try {
			Class.forName(JDBC_DRIVER);
			for (int i = 0; i < MAX_CON; i++) {
				try {
					buffer[i]= DriverManager.getConnection(DB_URL, "comm", "comm");
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static DBConnection getInstance() {
		if (instance == null) {
			instance = new DBConnection();
		}
		
		return instance;
	}
	
	public synchronized Connection getConnection() {
		if (this.free == 0) return null;
		
		this.free--;
		Connection conn = buffer[first];
		this.first = (this.first+1)%MAX_CON;
		return conn;
	}
	
	public synchronized void putConnection(Connection conn) {
		if (conn == null) return;
		
		this.free++;
		buffer[this.last] = conn;
		this.last = (this.last+1)%MAX_CON;
	}
	*/

	private DBConnection() {}

	public Connection getConnection() {
		return connection;
	}

	public static DBConnection getInstance() {
		if(instance == null) {
			synchronized (DBConnection.class) {
				if(instance == null) {
					instance = new DBConnection();
				}
			}
		}

		return instance;
	}

	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void rollbackTransaction() {
		try {
			connection.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void commitTransaction() {
		try {
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void startTransaction() {
		try {
			connection = DriverManager.getConnection(DBConnection.DB_URL, "comm", "comm");
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
