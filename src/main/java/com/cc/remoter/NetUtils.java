package com.cc.remoter;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetUtils {

    private static String localIP = null;

    public static String getLocalIP() {
        try {
            localIP = InetAddress.getLocalHost().toString().split("/")[1];
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return localIP;
    }
}
