package org.integratedmodelling.controlcenter.product;

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
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.integratedmodelling.controlcenter.runtime.Downloader;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;

/**
 * A proxy to efficiently synchronize any web-hosted software distribution, with
 * intelligent sync from an appropriately configured remote host. The remote
 * host must contain a file list with file hashes named
 * 
 * <pre>
 * filelist.txt
 * </pre>
 * 
 * . The file list should be generated at the server side by issuing
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
public class Distribution {

	String remoteURL;
	File workspace;
	SyncListener listener;

	HashMap<String, Long> localFiles = new HashMap<String, Long>();
	HashMap<String, Long> fileSizes = new HashMap<String, Long>();

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
		 * This is only called when preparing an incremental update from a previous
		 * distribution, which can run relatively long.
		 */
		void notifyDownloadPreparationStart();

		/**
		 * This is only called when preparing an incremental update from a previous
		 * distribution, which can run relatively long.
		 */
		void notifyDownloadPreparationEnd();

		void notifyFileProgress(String file, long bytesSoFar, long totalBytes);

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
		 * Notify an error
 		 * @param e an exception
		 */
		void notifyError(Exception e);

		/**
		 * 
		 */
		void transferFinished(Exception e);
	}

	/**
	 * @param url
	 * @param workspace
	 */
	public Distribution(String url, File workspace) {
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

	private void readFilelist(File f, HashMap<String, String> map) throws KlabException {

		map.clear();

		/*
		 * type 0 = "hash filename" (built by md5sum); type 1 = "file,hash" (built by
		 * Maven process). Checked on the first valid line only.
		 */
		int type = -1;

		if (f.exists() && f.isFile()) {
			try {
				for (String s : FileUtils.readLines(f)) {

					s = s.trim();

					if (s.isEmpty() || s.startsWith("#")) {
						continue;
					}

					if (type < 0) {
						type = s.contains(",") ? 1 : 0; 
					}
					
					String[] ss = type == 0 ? s.split("\\s+") : s.split(",");
					String checksum = type == 0 ? ss[0] : ss[1];
					String file = type == 0 ? ss[1] : ss[0];

					if (file.startsWith(".")) {
						file = file.substring(1);
					}
					if (file.startsWith("/")) {
						file = file.substring(1);
					}

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
	 * Load the remote file list in the passed map. Map will be empty if list is not
	 * found.
	 * 
	 * @param files
	 * @throws KlabException
	 */
	public void getRemoteFilelist(HashMap<String, String> files) throws KlabException {

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
	 * Load the local file list in the passed map. Map will be empty if list is not
	 * found.
	 * 
	 * @param localFiles
	 * @throws KlabException
	 */
	public void getLocalFilelist(HashMap<String, String> localFiles) throws KlabException {
		readFilelist(new File(workspace + File.separator + "filelist.txt"), localFiles);
	}

	/**
	 * Weak check for an existing distribution. Should actually check for all files
	 * in the list, but who wants to do that. When fixed, the filelist should be the
	 * last file downloaded, so that's a relatively meaningful check.
	 * 
	 * @return true if the last file in the list has been downloaded successfully.
	 */
	public boolean isComplete() {
		return workspace != null && workspace.exists()
				&& new File(workspace + File.separator + "filelist.txt").exists();
	}

	/**
	 * Synchronize the necessary files. Will do nothing (and return true) if we have
	 * elected to use a local installation. Will return false if we're not
	 * network-enabled or the selected server is offline.
	 * 
	 * @return true if synchronization was successful.
	 * @throws KlabException
	 */
	public boolean sync() throws KlabException {

	    HashMap<String, String> toDownload = new HashMap<String, String>();
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
			if (!local.containsKey(s) || !local.get(s).equals(remote.get(s)) || !getDestinationFile(s).exists()) {
				if (!s.equals("filelist.txt"))
					toDownload.put(s, remote.get(s));
			}
		}
		toDownload.put("filelist.txt", null);

		/*
		 * TODO scan workspace and schedule anything that isn't in the file list for
		 * deletion.
		 */
		scanForDeletion(workspace, remote, toRemove);

		if (listener != null) {
			listener.notifyDownloadCount(toDownload.size(), toRemove.size());
		}

		workspace.mkdirs();
		Exception downloadError = null;
		
		for (String f : toDownload.keySet()) {
			if (listener != null) {
				listener.beforeDownload(f);
			}
			try {
				new Downloader(new URL(remoteURL + "/" + f), getDestinationFile(f), (sofar, total) -> {
					if (listener != null) {
						listener.notifyFileProgress(f, sofar, total);
					}
				}, toDownload.get(f)).download();
				
				if (f.endsWith(".sh")) {
					// bit of a hack, but that should make things work on Linux
					// and MacOS.
					Files.setPosixFilePermissions(getDestinationFile(f).toPath(), exec);
				}
			} catch (UnsupportedOperationException e) {
				// ignore
			} catch (IOException e) {
			    listener.notifyError(e);
			    break;
			} catch (KlabException e) {
			    listener.notifyError(e);
			    if (e.getCause() instanceof KlabIOException) {
			        downloadError = e;
			    }
			    break;
			}
		}
		if (downloadError == null) {
    		for (File f : toRemove) {
    			if (listener != null) {
    				listener.beforeDelete(f);
    			}
    			FileUtils.deleteQuietly(f);
    		}
		}
		if (listener != null) {
			listener.transferFinished(downloadError);
		}

		return true;
	}

	private void scanForDeletion(File file, HashMap<String, String> remote, ArrayList<File> toRemove) {

		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				scanForDeletion(f, remote, toRemove);
			}
		} else {

			String fname = ("." + file.toString().substring(workspace.toString().length())).replaceAll("\\\\", "/");

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

	public static void main(String[] args) throws Exception {

		Distribution tl = new Distribution("http://www.integratedmodelling.org/downloads/products/master/cli/102",
				new File(System.getProperty("user.home") + File.separator + "scratchmyass"));
		tl.setListener(new SyncListener() {

			long _total;
			long _sofar;

			@Override
			public void beforeDownload(String file) {
				System.out.println("downloading " + file + " ("
						+ NumberFormat.getPercentInstance().format((double) _sofar / (double) _total) + ")");
				_sofar++;
			}

			@Override
			public void beforeDelete(File localFile) {
				System.out.println("deleting " + localFile);
			}

			@Override
			public void notifyDownloadCount(int downloadFilecount, int deleteFileCount) {
				System.out.println(downloadFilecount + " to download, " + deleteFileCount + " to delete, ");
				_total = downloadFilecount;
			}

			@Override
			public void transferFinished(Exception e) {
				System.out.println("transferred " + _sofar + " files");
				if (e != null) {
				    System.err.println("error: " + e);
				}
			}

			@Override
			public void notifyFileProgress(String file, long bytesSoFar, long totalBytes) {
				System.out.println("   " + file + ": " + bytesSoFar + "/" + totalBytes);
			}

			@Override
			public void notifyDownloadPreparationStart() {
				// TODO Auto-generated method stub

			}

			@Override
			public void notifyDownloadPreparationEnd() {
				// TODO Auto-generated method stub

			}

            @Override
            public void notifyError( Exception e ) {
                System.err.println(e);
            }
		});

		tl.sync();
	}

}
