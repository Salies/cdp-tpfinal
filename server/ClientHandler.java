package server;

import java.net.Socket;
import java.rmi.RemoteException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import compute.Compute;

import server.hashing.Hash;
import server.hashing.VerifyHash;
import server.network.ProfileRenderer;
import server.stats.DataStats;

// Um handler em formato de thread clássico.
// Boa parte do código é auto-explicativo pelos tipos e nomes de variáveis.
public class ClientHandler extends Thread {

    final private Socket soc;
    final private ObjectInputStream inStream;
    final private ObjectOutputStream outStream;
    final private Compute comp;
    final static private String remoteErrorMsg = "Erro ao executar a task remotamente:";

    public ClientHandler(Socket soc, ObjectInputStream inStream, ObjectOutputStream outStream, Compute comp) {
        this.soc = soc;
        this.inStream = inStream;
        this.outStream = outStream;
        this.comp = comp;
    }

    // Handlers, cada um responsável por um serviço.
    // Devolve a string que deve ser passada ao cliente.
    private String handleNetwork(String[] command) {
        ProfileRenderer pr = new ProfileRenderer(
            command[1], command[2], command[3], command[4], 
            Integer.parseInt(command[5]), Integer.parseInt(command[6]), Integer.parseInt(command[7])
        );

        try {
            String html = this.comp.executeTask(pr);
            return html;
        } catch(RemoteException e) {
            System.err.println(remoteErrorMsg);
            e.printStackTrace();
        }

        return "";
    }

    @Override
    public void run() {
        // A comunicação com o cliente dá-se exclusivamente por meio de strings.
        String received;
        String toSend;

        // Enquanto houver conexão...
        while(true) {
            // Loop principal
            try {
                received = (String) this.inStream.readObject();
                // qqq - código de quitar da Bethesda ;)
                if(received.equals("qqq")) {
                    this.soc.close();
                    break;
                }

                //System.out.println(received);

                // Interpretando o comando
                String[] command = received.split(" ");
                
                switch(command[0]) {
                    case "network":
                        toSend = handleNetwork(command);
                        break;
                    default:
                        toSend = "error";
                        break;
                }

                // Mandando a resposta para o cliente
                this.outStream.writeObject(toSend);
            } catch (Exception e) {
                // IOException, ClassNotFoundException, RemoteException
                System.err.println("Erro ao ler do cliente.");
                e.printStackTrace();
            }
        }

        // Fechando a conexão.
        try {
            this.inStream.close();
            this.outStream.close();
        } catch (IOException e) {
            System.err.println("Erro ao fechar conexão.");
            e.printStackTrace();
        }
    }
}
