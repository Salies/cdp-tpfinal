package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.util.HashMap;

import compute.Compute;
import server.hashing.Hash;
import server.network.ProfileRenderer;
import server.stats.DataStats;

public class Server {
    public static void main(String args[]) {
        try {
            System.out.println("Servidor iniciado.");
            System.out.println("Estabelecendo conexão com o executor...");
            Registry registry = LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1]));
            Compute comp = (Compute) registry.lookup("Serezane");
            System.out.println("Conexão com o executor estabelecida.");
            System.out.println("Iniciando o listener...");
            // Criando o socket.
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[2]));
            System.out.println("Listener iniciado. O servidor está pronto para receber conexões.");
            // Loop principal de conexões.
            while(true) {
                Socket soc = null;
                try {
                    soc = serverSocket.accept();
                    // Pegando os fluxos...
                    DataInputStream inStream = new DataInputStream(soc.getInputStream());
                    DataOutputStream outStream = new DataOutputStream(soc.getOutputStream());
                    // Invocando uma thread para cuidar dessa conexão.
                    Thread t = new ClientHandler(soc, inStream, outStream);
                    // Iniciando a thread.
                    t.start();
                } catch (Exception e) {
                    System.err.println("Erro ao aceitar conexão.");
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
