package salies.runner;

import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ComputeEngine implements Compute {
    public ComputeEngine() {
        super();
    }

    public <T> T executeTask(Task<T> t) {
        return t.execute();
    }
    public static void main(String[] args) {
        // load policy
        System.setProperty("java.security.policy", "file:./src/main/java/salies/runner/server-win.policy");

        try {
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }

            Compute engine = new ComputeEngine();

            Compute stub = (Compute) UnicastRemoteObject.exportObject(engine, 0);

            // criando o rmiregistry por aqui mesmo
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            registry.rebind("Compute", stub);

            System.out.println("ComputeEngine bound");
        } catch (Exception e) {
            System.err.println("ComputeEngine exception:");
            e.printStackTrace();
        }
    }
}