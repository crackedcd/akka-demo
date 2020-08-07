package com.cc.lifecycle;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Terminated;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class WatchActor extends AbstractActor {

    LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

    ActorRef monitoredActor;
    /**
     * 监听一个actor
     * @param actorRef
     */
    public WatchActor(ActorRef actorRef){
        getContext().watch(actorRef);
        this.monitoredActor = actorRef;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Terminated.class, msg -> {
                    // 收到结束时，提示worker已结束
                    logger.error(monitoredActor.toString() + " has Terminated.");
                    // 结束自己
                    getContext().system().terminate();
                    // 提示自己已结束
                    logger.error(getContext().getSelf().toString() + " has Terminated.");
                }).build();
    }
}
