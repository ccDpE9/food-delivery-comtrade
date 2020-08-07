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

			while(true) {
				ServerSocket serverSocket = new ServerSocket(8000);
				Socket socket = serverSocket.accept();
				Request request = parse(socket);
				
				Thread clientThread = new Thread() {
				    public void run() {
				    	dispatchRequest(request, socket);
				    }
				};

				executors.execute(clientThread);
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static Request parse(Socket socket) {
		ObjectInputStream objectInputStream;
		Request request = null;
		
		try {
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			request = (Request) objectInputStream.readObject();
		}
		catch(ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		return request;
	}

	private static void dispatchRequest(Request request, Socket socket) {
		Response response = new Response();
		Dispatcher.dispatch(request, response);
		respond(response, socket);
	}
	
	private static void respond(Response response, Socket socket) {
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(response);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

	}
}

