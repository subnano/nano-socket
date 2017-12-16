package net.nano.net.client;

public interface NanoClient {

    void connect();

    void disconnect();

    public static NanoClientBuilder newBuilder() {
        return new NanoClientBuilder();
    }
}
