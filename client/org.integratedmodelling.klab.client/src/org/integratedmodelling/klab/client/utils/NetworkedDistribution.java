package org.integratedmodelling.klab.client.utils;

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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;


/**
 * A proxy to efficiently synchronize any web-hosted software distribution, 
 * with intelligent sync from an appropriately configured remote host. The 
 * remote host must contain a file list with file hashes named <pre>filelist.txt</pre>. 
 * The file list should be generated at the server side by issuing
 * 
 * <pre>
 * md5sum `find . -type f -print` > filelist.txt
 * </pre>
 * 
 * in the root directory of the distribution. From the Java API, use 
 * {@link org.integratedmodelling.common.utils.FileUtils#createMD5Digest(File, String)}
 * to produce an identical filelist.
 * 
 * @author Ferd
 *
 */
public class NetworkedDistribution {

    @SuppressWarnings("javadoc")
    public final static String DEFAULT_SERVER_URL = "http://www.integratedmodelling.org/downloads/tl/e0";

    String                     remoteURL;
    File                       workspace;
    SyncListener               listener;

    HashMap<String, Long>      localFiles         = new HashMap<String, Long>();
    HashMap<String, Long>      fileSizes          = new HashMap<String, Long>();

    @SuppressWarnings("javadoc")
    public File getServerWorkspace() {
        return workspace;
    }

    /**
     * Use one of these to implement progress monitoring for downloads.
     * 
     * @author Ferd
     *
     */
    public static interface SyncListener {

        /**
         * @param file
         */
        void beforeDownload(String file);

        /**
         * @param localFile
         */
        void beforeDelete(File localFile);

        /**
         * @param downloadFilecount
         * @param deleteFileCount
         */
        void notifyDownloadCount(int downloadFilecount, int deleteFileCount);

        /**
         * 
         */
        void transferFinished();
    }

//    @SuppressWarnings("javadoc")
//    public NetworkedDistribution() {
//        remoteURL = DEFAULT_SERVER_URL;
//        workspace = new File(KLAB.CONFIG.getDataPath() + File.separator + "server");
//    }

    /**
     * @param url
     * @param workspace
     */
    public NetworkedDistribution(String url, File workspace) {
        remoteURL = url;
        this.workspace = workspace;
    }

    /**
     * Set the listener for synchronization, replacing any previously set one.
     * 
     * @param listener
     */
    public void setListener(SyncListener listener) {
        this.listener = listener;
    }

    Properties getRemoteDistributionProperties() {

        Properties ret = new Properties();

        return ret;

    }

    Properties getLocalDistributionProperties() {

        Properties ret = new Properties();

        return ret;

    }

    private void readFilelist(File f, HashMap<String, String> map)
            throws KlabException {

        map.clear();

        if (f.exists() && f.isFile()) {
            try {
                for (String s : FileUtils.readLines(f)) {

                    s = s.trim();

                    if (s.isEmpty())
                        continue;

                    String[] ss = s.split("\\s+");
                    String checksum = ss[0];
                    String file = ss[1];

                    if (file.startsWith("."))
                        file = file.substring(1);
                    if (file.startsWith("/"))
                        file = file.substring(1);

                    if (file.isEmpty())
                        continue;

                    map.put(file, checksum);
                }
            } catch (Exception e) {
                throw new KlabIOException(e);
            }
        }
    }

    /**
     * Load the remote file list in the passed map. Map will be empty if
     * list is not found.
     * 
     * @param files
     * @throws KlabException
     */
    public void getRemoteFilelist(HashMap<String, String> files)
            throws KlabException {

        File f = null;
        try {
            f = File.createTempFile("fls", "txt");
            FileUtils.copyURLToFile(new URL(remoteURL + "/filelist.txt"), f);
        } catch (Exception e) {
            throw new KlabIOException(e);
        }
        readFilelist(f, files);
    }

    /**
     * Load the local file list in the passed map. Map will be empty if list is
     * not found.
     * 
     * @param localFiles
     * @throws KlabException
     */
    public void getLocalFilelist(HashMap<String, String> localFiles)
            throws KlabException {
        readFilelist(new File(workspace + File.separator + "filelist.txt"), localFiles);
    }

    /**
     * Weak check for an existing distribution. Should actually check for all
     * files in the list, but who wants to do that. When fixed, the filelist
     * should be the last file downloaded, so that's a relatively meaningful
     * check.
     * 
     * @return true if the last file in the list has been downloaded successfully.
     */
    public boolean isComplete() {
        return workspace != null
                && workspace.exists()
                && new File(workspace + File.separator
                        + "filelist.txt").exists();
    }

    /**
     * Synchronize the necessary files. Will do nothing (and return true) if we
     * have elected to use a local installation. Will return false if we're not
     * network-enabled or the selected server is offline.
     * 
     * @return true if synchronization was successful.
     * @throws KlabException 
     */
    public boolean sync() throws KlabException {

//        if (!NetUtilities.urlResponds(remoteURL + "/filelist.txt")) {
//            return false;
//        }

        ArrayList<String> toDownload = new ArrayList<String>();
        ArrayList<File> toRemove = new ArrayList<File>();

        HashMap<String, String> remote = new HashMap<String, String>();
        HashMap<String, String> local = new HashMap<String, String>();

        Set<PosixFilePermission> exec = new HashSet<PosixFilePermission>();
        exec.add(PosixFilePermission.OWNER_EXECUTE);
        exec.add(PosixFilePermission.OWNER_READ);
        exec.add(PosixFilePermission.OWNER_WRITE);

        getRemoteFilelist(remote);
        getLocalFilelist(local);

        // process the filelist.txt entry last, so that the distrib only returns
        // isComplete when it
        // got to the end.
        for (String s : remote.keySet()) {
            if (!local.containsKey(s) || !local.get(s).equals(remote.get(s))
                    || !getDestinationFile(s).exists()) {
                if (!s.equals("filelist.txt"))
                    toDownload.add(s);
            }
        }
        toDownload.add("filelist.txt");

        /*
         * TODO scan workspace and schedule anything that isn't in the file list
         * for deletion.
         */
        scanForDeletion(workspace, remote, toRemove);

        if (listener != null) {
            listener.notifyDownloadCount(toDownload.size(), toRemove.size());
        }

        workspace.mkdirs();

        for (String f : toDownload) {
            if (listener != null) {
                listener.beforeDownload(f);
            }
            try {
                FileUtils.copyURLToFile(new URL(remoteURL + "/" + f), getDestinationFile(f));
                if (f.endsWith(".sh")) {
                    // bit of a hack, but that should make things work on Linux
                    // and MacOS.
                    Files.setPosixFilePermissions(getDestinationFile(f)
                            .toPath(), exec);
                }
            } catch (UnsupportedOperationException e) {
                // ignore
            } catch (IOException e) {
                throw new KlabIOException(e);
            }
        }

        for (File f : toRemove) {
            if (listener != null) {
                listener.beforeDelete(f);
            }
            FileUtils.deleteQuietly(f);
        }

        if (listener != null) {
            listener.transferFinished();
        }

        return true;
    }

    private void scanForDeletion(File file, HashMap<String, String> remote, ArrayList<File> toRemove) {

        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                scanForDeletion(f, remote, toRemove);
            }
        } else {

            String fname = ("." + file.toString().substring(workspace.toString().length()))
                    .replaceAll("\\\\", "/");

            if (fname.startsWith("."))
                fname = fname.substring(1);
            if (fname.startsWith("/"))
                fname = fname.substring(1);

            if (!fname.isEmpty() && !fname.equals("filelist.txt") && !fname.endsWith(".log")
                    && !remote.containsKey(fname)) {
                toRemove.add(file);
            }
        }
    }

    private File getDestinationFile(String f) {

        String[] fpath = f.split("\\/");
        String pref = workspace.toString();
        for (int i = 0; i < fpath.length - 1; i++) {
            pref += File.separator + fpath[i];
        }
        new File(pref).mkdirs();
        return new File(pref + File.separator + fpath[fpath.length - 1]);
    }

    @SuppressWarnings("javadoc")
    public static void main(String[] args) throws Exception {

        NetworkedDistribution tl = new NetworkedDistribution("http://www.integratedmodelling.org/downloads/tl/e0", new File(System
                .getProperty("user.home") + File.separator + ".tl" + File.separator + "server"));
        tl.setListener(new SyncListener() {

            long _total;
            long _sofar;

            @Override
            public void beforeDownload(String file) {
                System.out.println("downloading "
                        + file
                        + " ("
                        + NumberFormat.getPercentInstance().format((double) _sofar / (double) _total) + ")");
                _sofar++;
            }

            @Override
            public void beforeDelete(File localFile) {
                System.out.println("deleting " + localFile);
            }

            @Override
            public void notifyDownloadCount(int downloadFilecount, int deleteFileCount) {
                System.out.println(downloadFilecount + " to download, "
                        + deleteFileCount + " to delete, ");
                _total = downloadFilecount;
            }

            @Override
            public void transferFinished() {
                System.out.println("transferred " + _sofar + " files");
            }
        });

        tl.sync();
    }

}

