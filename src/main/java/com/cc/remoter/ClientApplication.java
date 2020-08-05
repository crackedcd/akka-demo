package com.cc.remoter;

import akka.actor.*;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.Map;

public class ClientApplication {

    public ClientApplication() {
        Config akkaConfig = ConfigFactory
                .load("remote/application-remote.conf")
                .getConfig("ClientConf");

        ActorSystem as = ActorSystem.create("AkkaServer", akkaConfig);
        String remoteActor = akkaConfig.getString("akka.actor.deployment.remoteServerActor.remote");
        ActorSelection selection = as.actorSelection(remoteActor);
        selection.tell("I am cc", null);
    }

    public static void main(String[] args) {
        new ClientApplication();
    }
}
