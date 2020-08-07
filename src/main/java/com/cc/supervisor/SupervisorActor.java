package com.cc.supervisor;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;

import static akka.actor.SupervisorStrategy.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SupervisorActor extends AbstractActor {

    LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

    private String childName = "childActor";

    @Override
    public void preStart() throws Exception {
        // 定义一个名称为"childActor"的子actor
        ActorRef childActor = getContext().actorOf(Props.create(WorkActor.class), childName);
        //监控
        getContext().watch(childActor);
    }


    @Override
    public Receive createReceive() {
        // 如果传入Msg类型，
        return receiveBuilder().match(String.class, msg -> {
            if (msg.equals("TEST")) {
                // 获取定义的"childActor"
                ActorRef child = getContext().child(childName).get();
                logger.info("supervisor get test");
                child.tell(new IOException(), getSelf());
                child.tell(new NullPointerException(getSelf().path().toString()), getSelf());
                child.tell(new IllegalArgumentException(), getSelf());
            } else {
                unhandled(msg);
            }
        }).match(Terminated.class, msg -> {
            logger.info(msg.getActor() + " is terminated");
        }).matchAny(msg -> {
            logger.error("supervisor match nothing");
        }).build();
    }

    /**
     * 在akka中，每个actor都是其子actor的supervisor
     * 重写supervisorStrategy方法，当一个子actor失败时，supervisor有两种策略：
     * OneForOneStrategy 只对出问题的子actor进行处理，默认策略
     * AllForOneStrategy 对子actor以及他的所有兄弟actor进行处理
     * <p>
     * Actor是有默认的strategy的，默认的strategy的行为有：
     * 当ActorInitializationException发生时将会停止失败的actor
     * 当ActorKilledException发生时将会停止失败的子actor
     * 当Exception发生时将会重启失败的子actor
     * 其他类型的Throwable将会转发给父actor
     * <p>
     * 可选的行为有Resume Restart Stop Escalate。
     */
    @Override
    public SupervisorStrategy supervisorStrategy() {
        // 抛出ArithmeticException异常时，执行忽略策略
        // 抛出NullPointerException异常时，执行重启actor策略
        // 抛出IllegalArgumentException异常时，执行关闭actor策略
        // 其它情况，则交由父actor处理
        return new OneForOneStrategy(3, Duration.create(1, TimeUnit.MINUTES),
                DeciderBuilder.match(IOException.class, e -> {
                    logger.info("!!!!!! " + e.toString());
                    return (Directive) resume();
                }).match(NullPointerException.class, e -> {
                    logger.info("!!!!!! " + e.toString());
                    return (Directive) restart();
                }).match(IllegalArgumentException.class, e -> {
                    logger.info("!!!!!! " + e.toString());
                    return (Directive) stop();
                }).matchAny(o -> {
                    logger.info("!!!!!! matchAny " + o.toString());
                    return (Directive) escalate();
                }).build()
        );
    }
}
