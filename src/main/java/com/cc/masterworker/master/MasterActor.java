package com.cc.masterworker.master;

import akka.actor.*;
import scala.concurrent.duration.Duration;


public class MasterActor extends AbstractActor {

    String remoteAddr;

    public MasterActor(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, msg -> {
                    ActorSelection sel = getContext().getSystem().actorSelection(remoteAddr);
                    // 把printerActor传递给worker，tell
                    sel.tell("master call '" + msg + "'", getSender());
                }).build();
    }
}
