package com.cc.masterworker.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class PhisicUtils {

    private static String localIP = null;
    private static String cpuUsage = null;
    private static String memoryUsage = null;

    public static String getLocalIP() {
        try {
            localIP = InetAddress.getLocalHost().toString().split("/")[1];
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return localIP;
    }

    public static String getCpuUsage() {
        return cpuUsage;
    }

    public static String getMemoryUsage() {
        return memoryUsage;
    }

}
