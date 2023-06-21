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

    private void sendMessage(String message) {
        // ...
    }

    private void closeConnection() throws IOException {
        this.outStream.writeObject("qqq");
        this.soc.close();
        this.outStream.close();
        this.inStream.close();
    }
}
