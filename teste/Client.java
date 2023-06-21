import java.io.*;
import java.net.*;
import java.util.Scanner;
  
// Client class
public class Client 
{
    public static void main(String[] args) throws IOException 
    {
        try
        {
            Scanner scn = new Scanner(System.in);
              
            // getting localhost ip
            InetAddress ip = InetAddress.getByName("localhost");
      
            // establish the connection with server port 5056
            Socket s = new Socket(ip, 666);
      
            // obtaining input and out streams
            ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream dis = new ObjectInputStream(s.getInputStream());
      
            // the following loop performs the exchange of
            // information between client and client handler
            String msg = "network Salies salies It's%not%a%lake,%it's%an%ocean. São%Paulo 0 666 1";
            dos.writeObject(msg);

            String received = (String) dis.readObject();
            System.out.println(received.charAt(0));

            // teste 2: hashing
            msg = "hash exec MD5 batatadomal";
            dos.writeObject(msg);

            received = (String) dis.readObject();
            System.out.println(received);

            // hashing reverso
            msg = "hash verify md5 batatadomal " + received;
            dos.writeObject(msg);

            received = (String) dis.readObject();
            System.out.println(received);

            msg = "qqq";
            dos.writeObject(msg);
              
            // closing resources
            scn.close();
            dis.close();
            dos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}