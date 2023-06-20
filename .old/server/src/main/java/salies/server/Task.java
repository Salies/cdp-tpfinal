package salies.server;

public interface Task<T> {
    T execute();
}