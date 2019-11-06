package com.github.ssjam;

import gnu.getopt.Getopt;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ProxyService {
    private int port = 5978;
    private boolean isDone = false;

    public static void main(String[] args) {
        ProxyService proxy = new ProxyService(getPort(args));
        proxy.run();
    }

    public int status() {
        return this.port;
    }

    public void stop() {
        this.isDone = true;
    }

    public void test() {
        ProxyService proxy = new ProxyService();
        proxy.run();
    }

    ProxyService() {}

    ProxyService(int p) {
        if(p > 0 && p < 65536) {
            this.port = p;
        }
    }

    private static int getPort(String[] args) {
        Getopt g = new Getopt("ProxyServer", args, "p:");
        int c;
        while ((c = g.getopt()) != -1) {
            if (c == 'p') {
                return Integer.parseInt(g.getOptarg());
            }
        }
        return -1;
    }

    private void run() {
        try {
            ServerSocket server = new ServerSocket(this.port);

            System.out.println("Server started...");

            while (!isDone) {
                Socket socket = server.accept();

                new ServerThread(socket).start();
            }

            System.out.println("Server stopped");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static class ServerThread extends Thread {
        private Socket socket = null;

        ServerThread(Socket socket) {
            this.socket = socket;
        }

        public void start() {
            System.out.println("Thread " + this.getName() + " starting...");
            try {
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(out));

                String content = null;
                int i = 0;
                while ((content = reader.readLine()) != null) {
                    System.out.println(i++ + ": " + content);
                    if(content.trim().isEmpty()) {
                        break;
                    }
                }

                writer.print("HTTP/1.1 200\r\n\ngood by!");
                writer.flush();

                socket.close();

                System.out.println("Thread " + this.getName() + " stopped");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
