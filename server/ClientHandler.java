package server;

import java.net.Socket;
import java.rmi.RemoteException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

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
            System.out.println("Enviando objeto ProfileRenderer (servico network) para o executor...");
            String html = this.comp.executeTask(pr);
            System.out.println("Recebida a resposta para o servico network.");
            return html;
        } catch(RemoteException e) {
            System.err.println(remoteErrorMsg);
            e.printStackTrace();
        }

        return "";
    }

    private String handleHash(String[] command) {
        if(command[1].equals("exec")) {
            Hash h = new Hash(command[2], command[3]);
            try {
                System.out.println("Enviando objeto Hash (servico hash) para o executor...");
                String resHash = this.comp.executeTask(h);
                System.out.println("Recebida a resposta para o servico hash.");
                return resHash;
            } catch(RemoteException e) {
                System.err.println(remoteErrorMsg);
                e.printStackTrace();
            }
            return "";
        }

        if(!command[1].equals("verify")) return "";

        VerifyHash vh = new VerifyHash(command[2], command[3], command[4]);
        try {
            System.out.println("Enviando objeto VerifyHash (servico hash) para o executor...");
            Boolean resHash = this.comp.executeTask(vh);
            System.out.println("Recebida a resposta para o servico hash.");
            return resHash.toString();
        } catch(RemoteException e) {
            System.err.println(remoteErrorMsg);
            e.printStackTrace();
        }
        return "";
    }

    private String handleStats(String[] command) {
        // command[1] é uma sequência de números separadas por ;.
        // ex.: 1.5;2.7;3.9;4.1;5.3;6.5;7.7;8.9;9.1;10.3
        String[] numbers = command[1].split(";");
        // to Double
        Double[] data = new Double[numbers.length];
        for(int i = 0; i < numbers.length; i++) {
            data[i] = Double.parseDouble(numbers[i]);
        }
        // command[2] é o lag para autocorrelação.
        Integer lag = Integer.parseInt(command[2]);
        DataStats ds = new DataStats(data, lag);
        try {
            System.out.println("Enviando objeto DataStats (servico Medidas Estatisticas) para o executor...");
            HashMap<String, Double> resStats = this.comp.executeTask(ds);
            System.out.println("Recebida a resposta para o servico Medidas Estatisticas.");
            StringBuilder res = new StringBuilder();
            for(String key : resStats.keySet()) {
                res.append(key + ":" + resStats.get(key) + ";");
            }
            return res.toString();
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
                System.out.println("Comando recebido do cliente: " + received);
                // qqq - código de quitar da Bethesda ;)
                if(received.equals("qqq")) {
                    System.out.println("Cliente solicitou o encerramento da conexao. Encerrando...");
                    this.soc.close();
                    System.out.println("Conexao encerrada.\n");
                    break;
                }

                // Interpretando o comando
                String[] command = received.split(" ");
                
                switch(command[0]) {
                    case "network":
                        toSend = handleNetwork(command);
                        break;
                    case "hash":
                        toSend = handleHash(command);
                        break;
                    case "stats":
                        toSend = handleStats(command);
                        break;
                    default:
                        toSend = "error";
                        break;
                }

                // Mandando a resposta para o cliente
                this.outStream.writeObject(toSend);
                System.out.println("Resposta enviada para o cliente.\n");
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
