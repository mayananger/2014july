package com.company;

/**
 * Created by eladlavi on 12/25/14.
 */
public class Message {
    private String message;
    private byte sender;
    private byte recipient;
    public Message(String message, byte sender, byte recipient){
        this.message = message;
        this.sender = sender;
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public byte getSender() {
        return sender;
    }

    public byte getRecipient() {
        return recipient;
    }
}
