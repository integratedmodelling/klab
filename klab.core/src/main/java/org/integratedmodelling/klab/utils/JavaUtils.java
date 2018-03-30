/*******************************************************************************
 *  Copyright (C) 2007, 2014:
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

import java.io.File;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.SystemUtils;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * Utilities to find the path to the JRE.
 * 
 * @author Ferd
 *
 */
public class JavaUtils {

    
    public static String getCurrentClasspath() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        if (!(classloader instanceof URLClassLoader)) {
            return System.getProperty("java.class.path");
        }
        return StringUtils.joinObjects(((URLClassLoader)classloader).getURLs(), getClasspathSeparator());
    }
    
    public static char getClasspathSeparator() {
        if (Configuration.INSTANCE.getOS() == OS.WIN) {
            return ';';
        }
        return ':';
    }

    public static int exec(Class<?> klass, boolean wait, String ...args) throws KlabException {

        List<String> params = new ArrayList<>();
        
        params.add(getJavaExecutable());
        params.add("-cp");
        params.add(getCurrentClasspath());
        params.add(klass.getCanonicalName());

        if (args != null) {
            for (String a : args) {
                params.add(a);
            }
        }
        
        ProcessBuilder builder = new ProcessBuilder(params.toArray(new String[params.size()]));
        Process process;
        try {
            process = builder.start();
        if (wait) {
            process.waitFor();
            return process.exitValue();
        }
        } catch (Exception e) {
            
        }
        
        return 0;
    }

    /**
     * Use JAVA_HOME if defined, at worst get the currently running JRE.
     * 
     * @return JAVA_HOME value
     */
    public static String getJavaHome() {

        String ret = null;

        if (System.getenv("JAVA_HOME") != null) {
            File f = new File(System.getenv("JAVA_HOME"));
            if (f.isDirectory() && f.canRead()) {
                ret = f.toString();
            }
        }

        if (ret == null) {
            ret = System.getProperty("java.home");
        }

        return ret;
    }

    /**
     * Get all the VM arguments for the passed options.
     * @param minMemM minimum RAM (-Xms) in megabytes
     * @param maxMemM maximum RAM (-Xmx) in megabytes
     * @param isServer if true, add -server
     * @param permSize if > 0, add -XX:MaxPermSize=<N>m
     * 
     * @return java option string
     */
    public static String[] getOptions(int minMemM, int maxMemM, boolean isServer, int permSize) {

        ArrayList<String> ret = new ArrayList<>();

        ret.add("-Xms" + minMemM + "M");
        ret.add("-Xmx" + maxMemM + "M");
        if (isServer) {
            ret.add("-server");
        }
        if (permSize > 0 && !SystemUtils.isJavaVersionAtLeast(1.8f)) {
            ret.add("-XX:MaxPermSize=" + permSize + "m");
        }
        return ret.toArray(new String[ret.size()]);
    }

    /**
     * Path to the java executable using the java home above.
     * 
     * @return java executable
     */
    public static String getJavaExecutable() {
        return getJavaHome() + File.separator + "bin" + File.separator + "java";
    }

}
