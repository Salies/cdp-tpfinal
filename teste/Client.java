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
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
      
            // the following loop performs the exchange of
            // information between client and client handler
            dos.writeUTF("salve");
            dos.writeUTF("qqq");
              
            // closing resources
            scn.close();
            dis.close();
            dos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}