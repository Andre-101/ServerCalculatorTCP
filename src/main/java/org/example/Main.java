package org.example;

import org.example.core.service.Communicator;
import org.example.infrastructure.red.CommunicatorTCP;

public class Main {
    public static void main(String[] args) {
        Communicator server = new CommunicatorTCP(5000);
        server.start();
    }
}