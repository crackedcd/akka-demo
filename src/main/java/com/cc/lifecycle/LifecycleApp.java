package com.cc.lifecycle;

import akka.actor.*;

public class LifecycleApp {

    public static void main(String[] args) {
        ActorSystem lifecycleSystem = ActorSystem.create("lifecycle");
        // workActor是实际工作的
        ActorRef workActor = lifecycleSystem.actorOf(Props.create(WorkActor.class), "worker");
        // watchActor是监控workActor的，通过构造器把workActor传递了进去
        ActorRef watchActor = lifecycleSystem.actorOf(Props.create(WatchActor.class, workActor), "watcher");

        workActor.tell(Msg.WORKING, ActorRef.noSender());
        workActor.tell(Msg.DONE, ActorRef.noSender());
//        workActor.tell(Msg.CLOSE, ActorRef.noSender());

        /*
         * PoisonPill毒丸与Kill的区别在于：
         * PoisonPill作为普通消息排队，并将在邮箱中已经排队的消息后处理
         * Kill会抛出一个异常，这个actor将会暂停操作，它的监视者将会被调用去处理这个异常
         */
        workActor.tell(PoisonPill.getInstance(), ActorRef.noSender());
    }
}
