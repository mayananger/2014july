package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {

    static ArrayList<Message> messages;
    public static void main(String[] args) {
        messages = new ArrayList<Message>();
        try {
            ServerSocket serverSocket = new ServerSocket(10000);
            while(true){
                System.out.println("waiting for incoming communication...");
                Socket client = serverSocket.accept();
                ClientThread clientThread = new ClientThread(client);
                clientThread.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
