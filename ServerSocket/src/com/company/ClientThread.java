package com.company;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by eladlavi on 12/22/14.
 */
public class ClientThread extends Thread {
    public static final byte SEND = 1;
    public static final byte CHECK_MESSAGES = 2;
    Socket client;

    public ClientThread(Socket client){
        this.client = client;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = client.getInputStream();
            outputStream = client.getOutputStream();
            //client.setSoTimeout(5000);

            byte[] bytes = new byte[1024];
            int actuallyRead;
            while ((actuallyRead = inputStream.read(bytes)) != -1){
                System.out.println(bytes[0]);
            }
            System.out.println("conversation is done.");
            /*
            while ((actuallyRead = inputStream.read(bytes)) != -1) {
                byte action = bytes[0];
                byte[] response = null;
                switch (action){
                    case SEND:
                        response = sendMessage(new String(bytes, 3, actuallyRead - 3), bytes[1], bytes[2]);
                        break;
                    case CHECK_MESSAGES:
                        response = checkForMessages(bytes[1]);
                        break;
                }
                outputStream.write(response);
            }
            */


        }catch(Exception ex){
            System.out.println("error: " + ex);
        }finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private byte[] sendMessage(String message, byte sender, byte recipient){
        Message msg = new Message(message, sender, recipient);
        Main.messages.add(msg);

        return new byte[]{14};
    }

    private byte[] checkForMessages(byte user){
        for(int i=0;i<Main.messages.size();i++){
            Message msg = Main.messages.get(i);
            if(msg.getRecipient()==user){
                String message = msg.getMessage();
                byte[] messageBytes = message.getBytes();
                byte[] bytes = new byte[messageBytes.length+1];
                bytes[0] = msg.getSender();
                for(int j=1;j<bytes.length;j++)
                    bytes[j] = messageBytes[j-1];
                Main.messages.remove(i);
                return bytes;
            }
        }
        return new byte[]{12};
    }
}
