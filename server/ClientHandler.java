package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.io.IOException;

// Um handler em formato de thread clássico.
// Boa parte do código é auto-explicativo pelos tipos e nomes de variáveis.
public class ClientHandler extends Thread {

    final Socket soc;
    final DataInputStream inStream;
    final DataOutputStream outStream;

    public ClientHandler(Socket soc, DataInputStream inStream, DataOutputStream outStream) {
        this.soc = soc;
        this.inStream = inStream;
        this.outStream = outStream;
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
                received = this.inStream.readUTF();
                // qqq - código de quitar da Bethesda ;)
                if(received.equals("qqq")) {
                    this.soc.close();
                    break;
                }

                System.out.println(received);

                // Interpretando o comando.
                /*String[] command = received.split(" ");
                System.out.println(command);*/
            } catch (IOException e) {
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
