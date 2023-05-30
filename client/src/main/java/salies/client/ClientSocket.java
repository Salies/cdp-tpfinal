package salies.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ClientSocket extends Thread {
    private final String serverAddress;
    private final int serverPort;
    private final String message;

    public ClientSocket(String serverAddress, int serverPort, String message) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.message = message;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(serverAddress, serverPort)) {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(message.getBytes());
            outputStream.flush();

            System.out.println("Message sent to the server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}