package com.cc.supervisor;

import akka.actor.*;

public class SupervisorApp {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("supervisor");
        ActorRef supervisorActor = system.actorOf(Props.create(SupervisorActor.class), "supervisor");
        supervisorActor.tell("TEST", ActorRef.noSender());
    }
}
