package org.example.infrastructure.red;

import org.example.core.builders.FrameBuilder;
import org.example.core.models.Frame;
import org.example.core.processor.MessageProcessor;
import org.example.core.service.Communicator;
import org.example.infrastructure.logger.Log;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CommunicatorTCP implements Communicator {

    private final int port;
    private ServerSocket server;
    private boolean active = false;

    public CommunicatorTCP(int port) {
        this.port = port;
    }

    @Override
    public void start() {
        try {
            server = new ServerSocket(port);
            active = true;
            Log.info("Server listening for port: " + port);

            while (active) {
                Socket client = server.accept();
                new Thread(() -> manageClient(client)).start();
            }
        } catch (IOException e) {
            Log.error("Error starting server TCP", e);
        }
    }

    @Override
    public void stop() {
        active = false;
        try {
            server.close();
        } catch (IOException ignored) {}
    }

    private void manageClient(Socket client) {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))
        ) {
            String lineaHex = reader.readLine();

            if (lineaHex == null || lineaHex.trim().isEmpty()) {
                Log.warn("An empty frame was received");
                writer.write(FrameBuilder.create("Error").toString());
                writer.flush();
                return;
            }

            try {

                Frame frame = FrameBuilder.createFromHex(lineaHex);

                String result = new MessageProcessor().process(FrameBuilder.getText(frame));

                Frame response = FrameBuilder.create(result);

                writer.write(response + "\n");
                writer.flush();

            } catch (Exception e) {
                Log.error("Error processing frame", e);
                writer.write(FrameBuilder.create("Error").toString());
                writer.flush();
            }

        } catch (IOException e) {
            Log.error("Error in communication with client", e);
        }
    }

}
