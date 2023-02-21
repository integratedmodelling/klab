///*
// * This file is part of k.LAB.
// * 
// * k.LAB is free software: you can redistribute it and/or modify
// * it under the terms of the Affero GNU General Public License as published
// * by the Free Software Foundation, either version 3 of the License,
// * or (at your option) any later version.
// *
// * A copy of the GNU Affero General Public License is distributed in the root
// * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
// * see <http://www.gnu.org/licenses/>.
// * 
// * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
// * in author tags. All rights reserved.
// */
//package org.integratedmodelling.klab.utils;
//
//import java.io.File;
//import java.net.URLClassLoader;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.commons.lang3.JavaVersion;
//import org.apache.commons.lang3.SystemUtils;
//import org.integratedmodelling.klab.Configuration;
//import org.integratedmodelling.klab.exceptions.KlabException;
//
//// TODO: Auto-generated Javadoc
///**
// * Utilities to find the path to the JRE.
// *
// * @author Ferd
// * @version $Id: $Id
// */
//public class JavaUtils {
//
//    
//    /**
//     * Gets the current classpath.
//     *
//     * @return the current classpath
//     */
//    public static String getCurrentClasspath() {
//        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
//        if (!(classloader instanceof URLClassLoader)) {
//            return System.getProperty("java.class.path");
//        }
//        return StringUtils.joinObjects(((URLClassLoader)classloader).getURLs(), getClasspathSeparator());
//    }
//    
//    /**
//     * Gets the classpath separator.
//     *
//     * @return the classpath separator
//     */
//    public static char getClasspathSeparator() {
//        if (Configuration.INSTANCE.getOS() == OS.WIN) {
//            return ';';
//        }
//        return ':';
//    }
//
//    /**
//     * Exec.
//     *
//     * @param klass the klass
//     * @param wait the wait
//     * @param args the args
//     * @return the int
//     * @throws KlabException the klab exception
//     */
//    public static int exec(Class<?> klass, boolean wait, String ...args) throws KlabException {
//
//        List<String> params = new ArrayList<>();
//        
//        params.add(getJavaExecutable());
//        params.add("-cp");
//        params.add(getCurrentClasspath());
//        params.add(klass.getCanonicalName());
//
//        if (args != null) {
//            for (String a : args) {
//                params.add(a);
//            }
//        }
//        
//        ProcessBuilder builder = new ProcessBuilder(params.toArray(new String[params.size()]));
//        Process process;
//        try {
//            process = builder.start();
//        if (wait) {
//            process.waitFor();
//            return process.exitValue();
//        }
//        } catch (Exception e) {
//            
//        }
//        
//        return 0;
//    }
//
//    /**
//     * Use JAVA_HOME if defined, at worst get the currently running JRE.
//     *
//     * @return JAVA_HOME value
//     */
//    public static String getJavaHome() {
//
//        String ret = null;
//
//        if (System.getenv("JAVA_HOME") != null) {
//            File f = new File(System.getenv("JAVA_HOME"));
//            if (f.isDirectory() && f.canRead()) {
//                ret = f.toString();
//            }
//        }
//
//        if (ret == null) {
//            ret = System.getProperty("java.home");
//        }
//
//        return ret;
//    }
//
//    /**
//     * Get all the VM arguments for the passed options.
//     *
//     * @param minMemM minimum RAM (-Xms) in megabytes
//     * @param maxMemM maximum RAM (-Xmx) in megabytes
//     * @param isServer if true, add -server
//     * @param permSize if > 0, add -XX:MaxPermSize=<N>m
//     * @return java option string
//     */
//    public static String[] getOptions(int minMemM, int maxMemM, boolean isServer, int permSize) {
//
//        ArrayList<String> ret = new ArrayList<>();
//
//        ret.add("-Xms" + minMemM + "M");
//        ret.add("-Xmx" + maxMemM + "M");
//        if (isServer) {
//            ret.add("-server");
//        }
//        if (permSize > 0 && !SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_11)) {
//            ret.add("-XX:MaxPermSize=" + permSize + "m");
//        }
//        return ret.toArray(new String[ret.size()]);
//    }
//
//    /**
//     * Path to the java executable using the java home above.
//     *
//     * @return java executable
//     */
//    public static String getJavaExecutable() {
//        return getJavaHome() + File.separator + "bin" + File.separator + "java";
//    }
//
//}
