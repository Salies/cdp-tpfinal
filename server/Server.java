package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.math.BigDecimal;
import java.util.HashMap;

import compute.Compute;
import server.hashing.Hash;
import server.network.ProfileRenderer;
import server.stats.DataStats;

public class Server {
    public static void main(String args[]) {
        try {
            String name = "Compute";
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            Compute comp = (Compute) registry.lookup(name);
            
            //Pi task = new Pi(45);
            //BigDecimal pi = comp.executeTask(task);

            Hash task = new Hash("md5", "werehog");
            String h = comp.executeTask(task);

            System.out.println(h);

            ProfileRenderer task2 = new ProfileRenderer("João", "joao", "Sou o João", "Porto", 1, 0, 0);
            String html = comp.executeTask(task2);

            //System.out.println(html);
            System.out.println("html ok");

            Double[] data = {3.0, 2.0, 4.0, 2.0, 2.0, 10.0, 19.0, 18.0, 19.0, 8.0, 4.0, 1.0, 1.0, 1.0, 1.0, 1.0, 3.0, 6.0, 7.0, 9.0, 1.0};
            DataStats ds = new DataStats(data, 1);
            HashMap<String, Double> stats = comp.executeTask(ds);
            System.out.println(stats);
            
        } catch (Exception e) {
            System.err.println("ComputePi exception:");
            e.printStackTrace();
        }
    }    
}
