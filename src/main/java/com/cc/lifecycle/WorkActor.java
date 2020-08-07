package com.cc.lifecycle;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class WorkActor extends AbstractActor {

    LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

    /**
     * 当Akka通过Props构建一个Actor后，这个Actor可以立即开始处理消息，进入开始（started）状态。
     * 可以重载preStart在Actor开始处理消息前进行一些初始化准备工作
     */
    @Override
    public void preStart() {
        logger.info("work pre start.");
    }

    /**
     * 一个Actor可能因为完成运算、发生异常又或者人为通过发送Kill，PoisonPill强行终止等而进入停止（stopping）状态。
     * 在停止过程中这个Actor会先以递归方式停止它属下的所有子孙Actor然后停止处理消息并将所有发给它的消息导向DeadLetter队列
     * 可以重载postStop来进行一些事后清理工作
     */
    @Override
    public void postStop() {
        logger.info("work post stop");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Msg.class, msg -> {
                    if (msg == Msg.WORKING) {
                        logger.info(getSender() + " ask for : workActor is WORKING");
                    } else if (msg == Msg.DONE) {
                        logger.info(getSender() + " ask for : workActor is DONE");
                    } else if (msg == Msg.CLOSE) {
                        logger.info(getSender() + " ask for : workActor is CLOSE");
                        // 收到Msg.CLOSE时stop自己
                        getContext().stop(getSelf());
                        getSender().tell(Msg.CLOSE, getSelf());
                    } else if (msg == Msg.EXCEPTION) {
                        throw new NullPointerException(getSender().path().toString());
                    } else {
                        unhandled(msg);
                    }
                }).match(Exception.class, msg -> {
                    logger.info(msg.toString());
                }).matchAny(msg -> {
                    logger.info(msg.toString());
                }).build();
    }
}
