package com.cc.helloworld.classic;

import akka.actor.AbstractActor;

public class HelloWorldActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, msg -> {
                    System.out.println("actor match String");
                    System.out.println(msg);
                })
                .match(Integer.class, msg -> {
                    System.out.println("actor match Integer");
                    System.out.println(msg);
                })
                .build();
    }
}
