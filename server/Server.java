package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import compute.Compute;

public class Server {
    public static void main(String args[]) {
        try {
            System.out.println("Servidor iniciado.\n");
            System.out.println("Estabelecendo conexao com o executor...");
            Registry registry = LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1]));
            Compute comp = (Compute) registry.lookup("Serezane");
            System.out.println("Conexao com o executor estabelecida.\n");
            System.out.println("Iniciando o listener...");
            // Criando o socket.
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[2]));
            System.out.println("Listener iniciado. O servidor esta pronto para receber conexoes.");
            // Loop principal de conexões.
            while(true) {
                Socket soc = null;
                try {
                    soc = serverSocket.accept();
                    // ATENÇÃO: deve-se criar o ObjectOutputStream antes do ObjectInputStream
                    // caso contrário haverá deadlock.
                    ObjectOutputStream outStream = new ObjectOutputStream(soc.getOutputStream());
                    ObjectInputStream inStream = new ObjectInputStream(soc.getInputStream());
                    // Invocando uma thread para cuidar dessa conexão.
                    Thread t = new ClientHandler(soc, inStream, outStream, comp);
                    // Iniciando a thread.
                    t.start();
                } catch (Exception e) {
                    soc.close();
                    System.err.println("Erro ao aceitar conexao.");
                    e.printStackTrace();
                }
            }

            // A única maneira de encerrar o servidor é com um Ctrl+C, logo, não é necessário fechar o socket.
            // serverSocket.close();
        } catch (Exception e) {
            System.err.println("Erro ao iniciar o servidor:");
            e.printStackTrace();
        }
    }    
}
