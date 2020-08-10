package com.Almeida;
import java.io.*;
import  java.net.Socket;
import java.util.*;

public class  ServerWorker extends Thread {

    private final Socket clientSocket;
    private String name;
    private Scanner in;
    private PrintWriter out;

    private Set<String> names = new HashSet<>();
    private Set<PrintWriter> writers = new HashSet<>();

    public ServerWorker(Socket clientSocket) {

        this.clientSocket = clientSocket;

    }

    @Override
    public void run() {
        try{
            handleClientSocket();
        }catch (IOException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }


    void handleClientSocket() throws IOException, InterruptedException {

        try {


            while (true) {

                in = new Scanner(clientSocket.getInputStream());
                out = new PrintWriter(clientSocket.getOutputStream(), true);


                out.println("SUBMIT NAME PLEASE ");

                name = in.nextLine();
                if (name == null) {
                    return;
                }
                synchronized (names) {
                    if (!name.isBlank() && !names.contains(name)) ;
                    {
                        names.add(name);
                        break;
                    }
                }
            }//end ofg while

            out.println(" name accepted " + name);
            for (PrintWriter writer : writers) {
                writer.println("message " + name + "has joined ");
            }

            writers.add(out);

            while (true) {
                String input = in.nextLine();

                for(PrintWriter writer : writers) {
                    writer.println("mesage " + name + " " + input);

                    if (input.toLowerCase().startsWith("quit")) ;
                {
                    return;
                }
                }


            }

        }catch( Exception e){
            System.out.println(e);

            }finally {
            if(out !=null){
                writers.remove(out);
            }
            if(name !=null){
                System.out.println(name + " is leaving the chat");
                names.remove(name);

                for(PrintWriter writer: writers){
                    writer.println("message " + name + "has leaft ");
                }
            }

        }

    }

}
