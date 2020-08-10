package com.Almeida;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static ArrayList<ServerWorker> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(500);



    public static void main(String[] args) throws IOException {
	// write your code here
        int PORT = 8818;
       try{
            //server socket
           ServerSocket serverSocket = new ServerSocket(PORT);

           while(true){

               System.out.println("Accepting client connection ");
               //acepts client and creates an object
               Socket clientSocket = serverSocket.accept();

               System.out.println("accepting connection from " + clientSocket);
               ServerWorker worker = new ServerWorker(clientSocket );

               clients.add(worker);//adding to the list of clients
               pool.execute(worker);

           }
       }catch ( IOException e){
           e.printStackTrace();
       }

    }
}
