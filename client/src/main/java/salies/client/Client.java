/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package salies.client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Properties;

/**
 *
 * @author Daniel Serezane
 */
public class Client {
    // Não há necessidade de um try-catch pois o cliente é inútil sem o servidor
    public static void main(String[] args) throws IOException {
        // Carregando o arquivo de configuração para pegar o endereço e a porta do servidor
        Properties properties = new Properties();
        InputStream propInput = new FileInputStream(new File("conn.properties"));
        properties.load(propInput);

        String host = properties.getProperty("host");
        int port = Integer.parseInt(properties.getProperty("port"));

        // Criando o socket
        InetAddress ip = InetAddress.getByName(host);
        Socket soc = new Socket(ip, port);
        // Criando os streams
        ObjectOutputStream outStream = new ObjectOutputStream(soc.getOutputStream());
        ObjectInputStream inStream = new ObjectInputStream(soc.getInputStream());

        // Criando o controlador
        // Ele cuidará do resto
        new ClientController(soc, outStream, inStream);
    }
}
