package com.github.ssjam;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.actor.typed.PostStop;

public class Guardian extends AbstractActor {

    private ActorContext context = getContext();
    private Boolean debug = false;

    static Props props(Boolean debug) {
        return Props.create(Guardian.class, () -> new Guardian(debug));
    }

    private Guardian() {
        this(false);
    }

    private Guardian(Boolean debug) {
        this.debug = debug;
        context.getSystem().log().info("Media Proxy Application started");
        if(debug) {
            System.out.println("Media Proxy Application started");
        }

    }

    // No need to handle any messages
    @Override
    public Receive createReceive() {
        return receiveBuilder().matchEquals(PostStop.class, signal -> onPostStop()).build();
    }

    private Guardian onPostStop() {
        context.getSystem().log().info("Media Proxy Application stopped");
        if(debug) {
            System.out.println("Media Proxy Application stopped");
        }
        return this;
    }}
