/*******************************************************************************
 *  Copyright (C) 2007, 2015:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.utils;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.URL;

import org.integratedmodelling.klab.exceptions.KlabRuntimeException;

public class NetUtilities {

    static public boolean isServerAlive(String host) {

        System.out.println("hostieging " + host);
        try {
            if (InetAddress.getByName(host + ":80").isReachable(200)) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

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
     * Call with "-" as a parameter to get the typical MAC address string. Otherwise use
     * another string to get a unique machine identifier that can be customized.
     * 
     * @param sep
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
            throw new KlabRuntimeException(e);
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
