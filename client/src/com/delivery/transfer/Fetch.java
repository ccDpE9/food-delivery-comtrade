package com.delivery.transfer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.delivery.domain.Request;
import com.delivery.domain.Response;

public class Fetch {
	private static Fetch instance;
	private Socket socket;

	private Fetch() {
		try {
			socket = new Socket("127.0.0.1", 9000);
		}
		catch (Exception e) {}
	}

	public static Fetch getInstance() {
		if(instance == null) instance = new Fetch();

		return instance;
	}

	public Response send(Request request) throws ClassNotFoundException {
		Response response = null;
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(request);
			
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			response = (Response) objectInputStream.readObject();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return response;
	}
}