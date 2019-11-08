package com.github.ssjam;

import akka.actor.Props;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.server.HttpApp;
import akka.http.javadsl.server.Route;
import akka.http.javadsl.settings.ServerSettings;
import com.typesafe.config.ConfigFactory;
import gnu.getopt.Getopt;

import java.util.concurrent.ExecutionException;

public class ProxyService extends HttpApp {
    private int port = 5978;
    private boolean isDone = false;
    private String address = "127.0.0.1";

    private ActorRef manager = null;
    private ActorRef getHandler = null;

    ProxyService() {
    }

    ProxyService(int p) {
        if(p > 0 && p < 65536) {
            this.port = p;
        }
    }

    ProxyService(String address, int p) {
        this(p);
        this.address = address;
    }

    public int status() {
        return this.port;
    }

    @Override
    protected Route routes() {
        return concat(
                path("test", () ->
                    complete("<h1>Say hello to akka-http</h1>")
                ),
                get(() -> concat(
                    path("hello", () ->
                            complete("Hello Baby!")
                    ),
                    complete(handleGet())
                )),
                post(() ->
                    complete("ready to run POST...")
                ),
                put(() ->
                    complete("ready to run PUT...")
                ),
                delete(() ->
                    complete("ready to run DELETE...")
                )
        );
    }

    private String handleGet() {
        return("ready to run GET...");
    }

    public static void main(String[] args) {
        ProxyService proxy = new ProxyService(getPort(args));
        proxy.run();
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
        // Starting the server
        final ActorSystem system = ActorSystem.apply("mediaProxy");
        manager = system.actorOf(Guardian.props(true), "Guardian");
        getHandler = system.actorOf(Props.create(InternalGetHandler.class), "WorkerManager");
        final ServerSettings settings = ServerSettings.create(ConfigFactory.load()).withVerboseErrorMessages(true);
        try {
            new ProxyService().startServer(address, port, settings);
        } catch (ExecutionException ex) {
            // Terminate the ActorSystem when exiting
            system.terminate();
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            // Terminate the ActorSystem when exiting
            system.terminate();
            ex.printStackTrace();
        }
    }
}
