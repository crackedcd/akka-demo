package com.cc.helloworld.typed;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;


/**
 * 在behavior中用parent actor的context.spawn()方法创建新的actor，或者用actor system的create方法创建新的actor
 */
public class HelloWorldBehavior extends AbstractBehavior<HelloWorldMsg> {

    // 声明actor
    private final ActorRef<HelloWorldMsg> hwActor;

    // 在behavior构造器中创建actor
    public HelloWorldBehavior(ActorContext<HelloWorldMsg> context) {
        super(context);
        hwActor = context.spawn(HelloWorldBehavior.create(),"helloworldBehavior");
    }

    // 在继承AbstractBehavior的子类中实现create静态方法，利用Behavior.setup()函数创建
    public static Behavior<HelloWorldMsg> create() {
        return Behaviors.setup(HelloWorldBehavior::new);
    }

    // 重写createReceive()方法，响应tell
    @Override
    public Receive<HelloWorldMsg> createReceive() {
        return newReceiveBuilder().onMessage(HelloWorldMsg.class, this::onHello).build();
    }

    // actor处理消息的实际方法，提供给newReceiveBuilder()使用，响应tell
    private Behavior<HelloWorldMsg> onHello(HelloWorldMsg msg) {
        System.out.println("hello world, hello " + msg.name + "!");
        return this;
    }
}
