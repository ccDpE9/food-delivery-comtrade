package com.delivery.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.delivery.domain.Request;
import com.delivery.domain.Response;

public class FrontController {
	public static void main(String[] args) {
		try {
			ExecutorService executors = Executors.newCachedThreadPool();
			ServerSocket socket = new ServerSocket(9000);

			while(true) {
				Socket connection = socket.accept();

				Thread clientThread = new Thread(() -> {
					try {
						//ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
						//ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());

						while (true) {
							ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
							ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
							Request request = (Request) input.readObject();
							Response response = new Response();
							dispatchRequest(request, response);
							output.writeObject(response);
						}
					}
					catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
				});

				executors.execute(clientThread);
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void dispatchRequest(Request request, Response response) {
		Dispatcher.dispatch(request, response);
	}
}

