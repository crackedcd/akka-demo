package com.cc.helloworld.typed;

import akka.actor.typed.ActorSystem;

/**
 * 有类型的actor
 */
public class HelloWorldApp {

    public static void main(String[] args) {
        /*
         * 要在actor system中定义新的actor，需定义一个继承AbstractBehavior的类(定义了一个行为)
         * actor是一组内部状态(state)+行为(behavior)的组合
         * 内部状态在接发消息的过程中发生改变，定义时需要传入继承了AbstractBehavior的类作为参数。
         */
        // system
        final ActorSystem<HelloWorldMsg> hwSystem = ActorSystem.create(HelloWorldBehavior.create(), "helloworld");

        // system tell
        hwSystem.tell(new HelloWorldMsg("cc"));

        // system terminate
        hwSystem.terminate();
    }
}
