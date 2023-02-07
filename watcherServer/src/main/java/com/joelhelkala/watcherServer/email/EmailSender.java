package com.joelhelkala.watcherServer.email;

public interface EmailSender {
    void send(String recipient, String email);
}
