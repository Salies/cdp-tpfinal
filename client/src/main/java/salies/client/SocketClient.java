package salies.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketClient implements Runnable {
    private String serverAddress;
    private int serverPort;

    public SocketClient(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    @Override
    public void run() {
        try {
            Socket clientSocket = new Socket(serverAddress, serverPort);

            System.out.println("Connected to server: " + serverAddress + ":" + serverPort);

            // Handle client requests here
            // For example, you can send data to the server and read the response

            OutputStream outputStream = clientSocket.getOutputStream();
            InputStream inputStream = clientSocket.getInputStream();

            // Send a message to the server
            String message = "Hello, server!";
            outputStream.write(message.getBytes());
            outputStream.flush();

            // Read the response from the server
            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            String response = new String(buffer, 0, bytesRead);

            System.out.println("Server response: " + response);

            // Cleanup
            inputStream.close();
            outputStream.close();
            clientSocket.close();

            System.out.println("Disconnected from server: " + serverAddress + ":" + serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}