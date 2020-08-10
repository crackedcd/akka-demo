package com.cc.remoter.server;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ServerActor extends AbstractActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private static int instanceCounter = 0;

    public void preStart() {
        instanceCounter++;
        log.info("Starting ServerActor instance #" + instanceCounter);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class,this::printMsg)
                .matchAny(obj -> {
                    System.out.println("out put others: " + obj.toString());
                }).build();
    }

    public void printMsg(String msg) {
        System.out.println("server actor match String");
        System.out.println(msg);
    }

}
