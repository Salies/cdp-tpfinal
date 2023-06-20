package salies.server;

import salies.server.plotter.Line;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Server {
    private static Compute comp;
    public static void main(String[] args) throws RemoteException, NotBoundException {
        int port = 51666; // Change this to your desired port number

        // load policy
        System.setProperty("java.security.policy", "file:./src/main/java/salies/runner/client-win.policy");

        // load security manager
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        comp = (Compute) registry.lookup("Compute");

       /* try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                // Create a new thread for each client connection
                Thread clientThread = new Thread(() -> {
                    try {
                        handleClient(clientSocket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private static void handleClient(Socket clientSocket) throws IOException {
        InputStream inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();

        // Handle client requests here
        // For example, you can read from the input stream and send a response to the output stream
        // This example simply echoes the received data back to the client

        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            //outputStream.write(buffer, 0, bytesRead);
            //outputStream.flush();
            // bytes to string
            String input = new String(buffer, 0, bytesRead);
            parseMessage(input, outputStream);
        }

        // Cleanup
        outputStream.close();
        inputStream.close();
        clientSocket.close();
        System.out.println("Client disconnected: " + clientSocket.getInetAddress().getHostAddress());
    }

    // interpreta os comandos recebidos do cliente
    private static void parseMessage(String message, OutputStream out) throws IOException {
        String[] args = message.split(" ");
        // Não precisa valida o que vem do cliente. Vamos confiar nele (eu que fiz, pô!)
        int argLen = args.length;
        Task t = null;
        switch (args[0]) {
            case "plot":
                BufferedImage graph;
                switch(args[1]) {
                    case "0":
                        t = new Line(
                                Double.parseDouble(args[4]),
                                Double.parseDouble(args[5]),
                                Double.parseDouble(args[2]),
                                Double.parseDouble(args[3]),
                                1.0f,
                                "Função afim"
                        );
                        break;
                }
                graph = (BufferedImage) comp.executeTask(t);
                String res = "tarefa concluida";
                out.write(res.getBytes());
                break;
        }
    }
}