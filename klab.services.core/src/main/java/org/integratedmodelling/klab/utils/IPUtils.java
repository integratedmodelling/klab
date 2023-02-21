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

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO: Auto-generated Javadoc
/**
 * The Class IPUtils.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public class IPUtils {
	private static Pattern pattern;
	private static final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	static {
		pattern = Pattern.compile(IPADDRESS_PATTERN);
	}

	/**
	 * Validate ip address with regular expression.
	 *
	 * @param ip
	 *            ip address for validation
	 * @return true valid ip address, false invalid ip address
	 */
	public static boolean validate(final String ip) {
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
	}

	/**
	 * Check IP matching.
	 *
	 * @param pattern
	 *            the pattern
	 * @param address
	 *            the address
	 * @return a boolean.
	 */
	public static boolean checkIPMatching(String pattern, String address) {

		if (pattern.equals("*.*.*.*") || pattern.equals("*"))
			return true;

		String[] mask = pattern.split("\\.");
		String[] ip_address = address.split("\\.");
		for (int i = 0; i < mask.length; i++) {
			if (mask[i].equals("*") || mask[i].equals(ip_address[i]))
				continue;
			else if (mask[i].contains("-")) {
				byte min = Byte.parseByte(mask[i].split("-")[0]);
				byte max = Byte.parseByte(mask[i].split("-")[1]);
				byte ip = Byte.parseByte(ip_address[i]);
				if (ip < min || ip > max)
					return false;
			} else
				return false;
		}
		return true;
	}

	/**
	 * Get the local IP for the interface matching the passed pattern.
	 *
	 * @param pattern
	 *            the pattern
	 * @return IP matching pattern
	 * @throws Exception
	 *             the exception
	 */
	public static String getLocalIpMatching(String pattern) throws Exception {
		Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
		for (; n.hasMoreElements();) {
			NetworkInterface e = n.nextElement();
			Enumeration<InetAddress> a = e.getInetAddresses();
			for (; a.hasMoreElements();) {
				InetAddress addr = a.nextElement();
				if (IPUtils.checkIPMatching(pattern, addr.getHostAddress())) {
					return addr.getHostAddress();
				}
			}
		}
		return null;
	}

	/**
	 * Gets the local ips.
	 *
	 * @return the local ips
	 * @throws SocketException
	 *             the socket exception
	 */
	public static Set<String> getLocalIps() throws SocketException {
		Set<String> ret = new HashSet<>();
		for (int i = 1; i < localPatterns.length; i++) {
			Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
			for (; n.hasMoreElements();) {
				NetworkInterface e = n.nextElement();
				Enumeration<InetAddress> a = e.getInetAddresses();
				for (; a.hasMoreElements();) {
					InetAddress addr = a.nextElement();
					if (IPUtils.checkIPMatching(localPatterns[i], addr.getHostAddress())) {
						ret.add(addr.getHostAddress());
					}
				}
			}
		}
		return ret;
	}
	
	/**
	 * Gets the non-local ips.
	 *
	 * @return the local ips
	 * @throws SocketException
	 *             the socket exception
	 */
	public static Set<String> getIps() throws SocketException {
		Set<String> ret = new HashSet<>();
		for (int i = 1; i < localPatterns.length; i++) {
			Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
			for (; n.hasMoreElements();) {
				NetworkInterface e = n.nextElement();
				Enumeration<InetAddress> a = e.getInetAddresses();
				for (; a.hasMoreElements();) {
					InetAddress addr = a.nextElement();
					if (!isLocal(addr.getHostAddress())) {
						ret.add(addr.getHostAddress());
					}
				}
			}
		}
		return ret;
	}
	
	/**
	 * Gets the non-local ip.
	 *
	 * @return the local ips
	 * @throws SocketException
	 *             the socket exception
	 */
	public static String getIp() throws SocketException {
		for (int i = 1; i < localPatterns.length; i++) {
			Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
			for (; n.hasMoreElements();) {
				NetworkInterface e = n.nextElement();
				Enumeration<InetAddress> a = e.getInetAddresses();
				for (; a.hasMoreElements();) {
					InetAddress addr = a.nextElement();
					if (!isLocal(addr.getHostAddress())) {
						return addr.getHostAddress();
					}
				}
			}
		}
		return null;
	}

	/**
	 * Get the local IP for the interface matching the first local pattern not
	 * starting with 127. Stable as the IP is always checked in the same order; will
	 * prefer 192.168.** IPs to 10.** or 172.** based ones.
	 *
	 * @return IP matching pattern
	 * @throws SocketException 
	 *             the SocketException
	 */
	public static String getLocalIp() throws SocketException {
		for (int i = 1; i < localPatterns.length; i++) {
			Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
			for (; n.hasMoreElements();) {
				NetworkInterface e = n.nextElement();
				Enumeration<InetAddress> a = e.getInetAddresses();
				for (; a.hasMoreElements();) {
					InetAddress addr = a.nextElement();
					if (IPUtils.checkIPMatching(localPatterns[i], addr.getHostAddress())) {
						return addr.getHostAddress();
					}
				}
			}
		}

		return null;
	}

	static String[] localPatterns = new String[] { "127.0.0.1", "192.168.*.*", "10.*.*.*", "172.16.*.*", "172.17.*.*",
			"172.18.*.*", "172.19.*.*", "172.20.*.*", "172.21.*.*", "172.22.*.*", "172.23.*.*", "172.24.*.*",
			"172.25.*.*", "172.26.*.*", "172.27.*.*", "172.28.*.*", "172.29.*.*", "172.30.*.*", "172.31.*.*" };
	
	static String[] localhostPatterns = new String [] { "127.0.0.1", "192.168.0.1", "10.0.0.1" };

	/**
	 * Checks if is local.
	 *
	 * @param ip
	 *            the ip
	 * @return a boolean.
	 */
	public static boolean isLocal(String ip) {

		if (ip.equals("0:0:0:0:0:0:0:1")) {
			return true;
		}
		
		for (String p : localPatterns) {
			if (checkIPMatching(p, ip)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isLocalhost(String ip) {
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			return true;
		}
		
		for (String p : localhostPatterns) {
			if (checkIPMatching(p, ip)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws Exception
	 *             the exception
	 */
	public static void main(String[] args) throws Exception {
		if (checkIPMatching("10.*.*.*", "10.0.0.64")) {
			System.out.println("ZORB");
		}
		if (isLocal("10.0.0.64")) {
			System.out.println("ZORB");
		}
		System.out.println("Local is " + getLocalIp());
		System.out.println("Non-local is " + getIp());
	}

}
