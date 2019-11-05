package com.github.ssjam;

import gnu.getopt.Getopt;

public class ProxyServer {
    private int port = 5978;
    private boolean isDone = false;

    public static void main(String[] args) {
        ProxyServer proxy = new ProxyServer();
        proxy.run();
    }

    public int status() {
        return this.port;
    }

    ProxyServer() {}

    ProxyServer(int p) {
        this.port = p;
    }

    private int getPort(String[] args) {
        Getopt g = new Getopt("ProxyServer", args, "p:");
        int c;
        while ((c = g.getopt()) != -1) {
            if (c == 'p') {
                return Integer.parseInt(g.getOptarg());
            }
        }
        return this.port;
    }

    private void run() {

    }
}
