package salies.client;

import java.awt.event.WindowAdapter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class ClientController {
    private final Socket soc;
    private final ObjectOutputStream outStream;
    private final ObjectInputStream inStream;
    private final MainWindow mainWindow;

    public ClientController(Socket soc, ObjectOutputStream outStream, ObjectInputStream inStream) {
        this.soc = soc;
        this.outStream = outStream;
        this.inStream = inStream;
        this.mainWindow = new MainWindow(this);
        // Quando fechar a janela, comunicar ao servidor o fechamento da conexão.
        // Isso garante o funcionamento pleno do servidor e evita vazamentos.
        this.mainWindow.addWindowListener(new WindowAdapter() {
           @Override
           public void windowClosing(java.awt.event.WindowEvent windowEvent) {
               try {
                   closeConnection();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
        });
        this.mainWindow.setVisible(true);
    }

    public void messageHandler(String type, String body) throws IOException, ClassNotFoundException {
        // passo 1: envia a mensagem para o servidor
        String message = type + " " + body;
        this.outStream.writeObject(message);
        System.out.println("Solicitado ao servidor o seviço " + type + " com o corpo " + body + ".");

        // passo 2: recebe a resposta do servidor - dado o protocolo estabelecido,
        // ela sempre será uma String
        String response = (String) this.inStream.readObject();
        System.out.println("Recebido o resultado do servidor: " + response);

        // passo 3: mandando a resposta para a MainWindow.
        // Não é responsabilidade do controlador trata-lá, isso é coisa da View.
        switch (type) {
            case "network" -> this.mainWindow.setRenderedHTML(response);
            case "hash exec" -> this.mainWindow.setHashResult(response);
            case "hash verify" -> this.mainWindow.setVerifyHashResult(response);
            case "stats" -> this.mainWindow.setStatsResult(response);
        }
    }

    private void closeConnection() throws IOException {
        System.out.println("Fechando a conexão...");
        this.outStream.writeObject("qqq");
        this.soc.close();
        this.outStream.close();
        this.inStream.close();
    }
}
