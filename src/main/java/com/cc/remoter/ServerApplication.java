package com.cc.remoter;

import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.Map;

public class ServerApplication {

    public ServerApplication() {
        Config akkaConfig = ConfigFactory
                .load("remote/application-remote.conf")
                .getConfig("ServerConf");

        ActorSystem as = ActorSystem.create("AkkaServer", akkaConfig);
        as.actorOf(Props.create(ServerActor.class), "test-server");
    }

    public static void main(String[] args) {
        new ServerApplication();
    }
}
