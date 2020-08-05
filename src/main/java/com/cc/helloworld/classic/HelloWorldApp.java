package com.cc.helloworld.classic;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class HelloWorldApp {

    public static void main(String[] args) {
    }

    private void sayHelloWorld() {
        // 创建system，抽象actor系统，可包括多个actor
        ActorSystem hwSystem = ActorSystem.create("helloworld");
        // 创建actorRef，actor不直接与外界接触，操作依赖actorRef
        // 即是system是一个大集合，里面可能有多个actor，但无法直接操作actor，必须依赖system外的actorRef实现
        Props hwProps = Props.create(HelloWorldActor.class);
        ActorRef hwActor = hwSystem.actorOf(hwProps, "myActor");
        // 通过actorRef，向具体的actor发送消息
        // tell()第一个参数是消息，第二个参数是发送者，该方法是异步的，只是给actor的mailbox发送消息，然后就返回
        // actor收到消息时，知道消息是哪个ref发来的，此处.noSender()表示不需要actor关心发送者
        hwActor.tell("Hello, World!", ActorRef.noSender());
        // 通知system终止
        hwSystem.terminate();
    }
}
