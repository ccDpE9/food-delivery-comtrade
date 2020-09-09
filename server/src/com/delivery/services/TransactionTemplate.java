package com.delivery.services;

import com.delivery.database.DBConnection;
import com.delivery.domain.Request;
import com.delivery.domain.Response;

public abstract class TransactionTemplate {
	public void serve(Request request, Response response) {
		try {
			start();
			execute(request, response);
			commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		finally {
			close();
		}
	}

	public void close() {
		DBConnection.getInstance().closeConnection();
	}

	public void rollback() {
		DBConnection.getInstance().rollbackTransaction();
	}

	public void commit() {
		DBConnection.getInstance().commitTransaction();
	}

	public abstract void execute(Request request, Response response);

	public void start() {
		DBConnection.getInstance().startTransaction();
	}
}
