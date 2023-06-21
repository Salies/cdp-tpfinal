package salies.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class ClientController {
    private final ObjectOutputStream outStream;
    private final ObjectInputStream inStream;
    private final MainWindow mainWindow;

    public ClientController(ObjectOutputStream outStream, ObjectInputStream inStream) {
        this.outStream = outStream;
        this.inStream = inStream;
        this.mainWindow = new MainWindow(this);
    }
}
