/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
package org.integratedmodelling.klab.utils;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.URL;

import org.integratedmodelling.klab.api.exceptions.KIOException;

// TODO: Auto-generated Javadoc
/**
 * The Class NetUtilities.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public class NetUtilities {

    /**
     * Checks if is server alive.
     *
     * @param host the host
     * @return a boolean.
     */
    static public boolean isServerAlive(String host) {

        try {
            if (InetAddress.getByName(host + ":80").isReachable(200)) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * Url responds.
     *
     * @param url the url
     * @return a boolean.
     */
    /*
     * FIXME can still hang for inordinate amounts of time when the URL does not respond. Should
     * use a connection pool, set a timer etc.
     */
    public static boolean urlResponds(String url) {

        URL u;
        try {
            u = new URL(url);

            // if (!isServerAlive(u.getHost())) {
            // return false;
            // }

            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod("HEAD");
            int code = connection.getResponseCode();
            connection.disconnect();
            if (code / 100 != 2) {
                // Env.logger.warn("urlResponds: " + url + " generated code: " + code);
            }
            // 405 = method not allowed means we're alive anyway
            return code == 405 || code / 100 == 2;
        } catch (Exception e) {
            // Env.logger.warn("urlResponds: " + url + " generated an exception: " + e.getMessage());
        }

        return false;
    }
    
    /**
     * Port available.
     *
     * @param port the port
     * @return a boolean.
     */
    public static boolean portAvailable(int port) {

        ServerSocket ss = null;
        DatagramSocket ds = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            ds = new DatagramSocket(port);
            ds.setReuseAddress(true);
            return true;
        } catch (Exception e) {
            e.getMessage();
        } finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    /* should not be thrown */
                }
            }
        }

        return false;
    }

    /**
     * Call with "-" as a parameter to get the typical MAC address string. Otherwise use another
     * string to get a unique machine identifier that can be customized.
     *
     * @param sep the sep
     * @return MAC address
     */
    public static String getMACAddress(String sep) {

        InetAddress ip;
        String ret = null;
        try {

            ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? sep : ""));
            }
            ret = sb.toString();

        } catch (Exception e) {
            throw new KIOException(e);
        }

        return ret;
    }

    // public static void main(String[] args) {
    // System.out.println("MAC: " + getMACAddress("?<DS"));
    //
    // // System.out.println("Start testing:");
    // // System.out.println("Testing 1.1: " + urlResponds("http://ecoinformatics.uvm.edu/geoserver/wcs"));
    // // System.out.println("Testing 2.1: " + urlResponds("http://integratedmodelling.org/geoserver/wcs"));
    // }
}
