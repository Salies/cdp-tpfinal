package salies.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class SocketClient implements Runnable {
    private final String address;
    private final int port;
    private final ConnView view;
    private Socket socket;

    public SocketClient(ConnView connView, String address, int port) {
        this.view = connView;
        this.address = address;
        this.port = port;
    }

    public void sendMessage(String msg) {
        try {
            this.socket.getOutputStream().write(msg.getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            this.socket = new Socket(address, port);
            InputStream in = socket.getInputStream();
            this.view.nextWindow(this);
            while (true) {
                int data = in.read();
                if (data == -1) {
                    break;
                }
                System.out.println(data);
            }
        } catch (IOException ex) {
            this.view.connError();
        }
    }
}