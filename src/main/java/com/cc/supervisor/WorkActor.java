package com.cc.supervisor;

import akka.actor.AbstractActor;
import akka.actor.ActorInitializationException;
import akka.actor.ActorKilledException;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.Option;

public class WorkActor extends AbstractActor {

    LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

    @Override
    public void preStart() throws Exception {
        super.preStart();
        logger.info("child preStart.");
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
        logger.info("work postStop");
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        super.preRestart(reason, message);
        logger.info("child preRestart");
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        super.postRestart(reason);
        logger.info("child postRestart");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Exception.class, msg -> {
                    logger.info(msg.toString());
                    throw msg;
                }).matchAny(msg -> {
                    logger.info(msg.toString());
                }).build();
    }
}
