package com.platform.filter;

import java.net.InetAddress;

public class Machine {
    private static String machineName = null;
    public static String getName() {
        if (machineName == null) {
            try {
                machineName = InetAddress.getLocalHost().getHostName();
            } catch (Exception e) {
                machineName = "notFound";
            }
        }
        if(machineName.equals("notFound"))
        {
            return null;
        }
        return machineName;
    }
}
