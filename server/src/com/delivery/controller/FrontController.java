package com.delivery.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FrontController {
	public static int PORT = 9000;
	private ExecutorService executors = Executors.newCachedThreadPool();
	
	public static void main(String[] args) {
		try {
			while(true) {
				ServerSocket serverSocket = new ServerSocket(PORT);
				Socket socket = serverSocket.accept();
				Request request = parse(socket);
				
				Thread clientThread = new Thread() {
				    public void run() {
				    	dispatchRequest(request, socket);
				    }
				};
				if(!list.contains(clijentTread)) {
					ControlerTread.getInstanca().addClient(clijentTread);
					executorService.execute(clijentTread);
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static Request parse(Socket socket) {
		ObjectInputStream objectInputStream;
		
		try {
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			Request request = (Request) objectInputStream.readObject();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	private static void dispatchRequest(Request request) {
		Dispatcher dispatcher = new Dispatcher(Request request, Socket socket);
		Response response = dispatcher.dispatch();
		response(response);
	}
	
	private static void respond(Response response) {
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(response);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

	}
}

