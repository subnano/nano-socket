package net.nano.net.socket;

import net.nano.core.util.Builder;

public class SocketConfig {

    private final String hostname;
    private final int port;

    SocketConfig(SocketConfigBuilder builder) {
        this.hostname = builder.hostname;
        this.port = builder.port;
    }

    public String hostname() {
        return hostname;
    }

    public int port() {
        return port;
    }

    public static SocketConfigBuilder newBuilder() {
        return new SocketConfigBuilder();
    }

    public static class SocketConfigBuilder implements Builder<SocketConfigBuilder, SocketConfig> {

        private String hostname;
        private int port;

        public SocketConfigBuilder hostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        @Override
        public SocketConfigBuilder reset() {
            return null;
        }

        @Override
        public SocketConfig build() {
            return new SocketConfig(this);
        }

        public SocketConfigBuilder port(int port) {
            this.port = port;
            return this;
        }
    }
}
