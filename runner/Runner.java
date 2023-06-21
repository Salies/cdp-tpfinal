package runner;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import compute.Compute;
import compute.Task;

// Baseado em: https://docs.oracle.com/javase/tutorial/rmi/overview.html

public class Runner implements Compute {

    public Runner() {
        super();
    }

    public <T> T executeTask(Task<T> t) {
        System.out.println("Solicitação recebida do servidor. Executando...");
        T executed = t.execute();
        System.out.println("Execução concluída. Enviando resultado para o servidor.\n");
        return executed;
    }

    public static void main(String[] args) {
        try {
            Compute engine = new Runner();
            Compute stub = (Compute) UnicastRemoteObject.exportObject(engine, 0);
            // args[0] = port pro rmi registry
            Registry registry = LocateRegistry.createRegistry(Integer.parseInt(args[0]));
            registry.rebind("Serezane", stub);
            System.out.println("Runner bound.\n");
        } catch (Exception e) {
            System.err.println("Erro no runner:");
            e.printStackTrace();
        }
    }
}
