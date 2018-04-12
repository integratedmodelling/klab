/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify it under the terms of the Affero
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root directory of the k.LAB
 * distribution (LICENSE.txt). If this cannot be found see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned in author tags. All
 * rights reserved.
 */
package org.integratedmodelling.klab.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.regex.Pattern;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.exceptions.KlabIOException;

// TODO: Auto-generated Javadoc
/**
 * The Class URLUtils.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public class URLUtils {


  /**
   * Pattern to validate a RFC 2141-compliant URN.
   */
  public final static Pattern URN_PATTERN =
      Pattern.compile("^urn:[a-z0-9][a-z0-9-]{0,31}:([a-z0-9()+,\\-.:=@;$_!*']|%[0-9a-f]{2})+$",
          Pattern.CASE_INSENSITIVE);

  /**
   * Check if passed URN string can be really called a URN according to RFC 2141 conventions.
   *
   * @param urn the urn
   * @return true if compliant
   */
  public static boolean isCompliant(String urn) {
    return URN_PATTERN.matcher(urn).matches();
  }


  /**
   * Ping the url by requesting the header and inspecting the return code.
   *
   * @param url the url
   * @return true if ping succeeds
   */
  public static boolean ping(String url) {

    // < 100 is undertermined.
    // 1nn is informal (shouldn't happen on a GET/HEAD)
    // 2nn is success
    // 3nn is redirect
    // 4nn is client error
    // 5nn is server error

    HttpURLConnection connection = null;
    boolean ret = false;
    try {
      connection = (HttpURLConnection) new URL(url).openConnection();
      connection.setRequestMethod("HEAD");
      int responseCode = connection.getResponseCode();
      if (responseCode > 100 && responseCode < 400) {
        ret = true;
      }
    } catch (Exception e) {
    }
    return ret;
  }

  /**
   * Return true if the passed host (not URL) responds on port 80.
   *
   * @param url the url
   * @return true if host responds
   */
  public static boolean pingHost(String url) {
    Socket socket = null;
    boolean reachable = false;
    try {
      socket = new Socket(url, 80);
      reachable = true;
    } catch (Exception e) {
    } finally {
      if (socket != null)
        try {
          socket.close();
        } catch (IOException e) {
        }
    }
    return reachable;
  }

  // /**
  // * Look for thinklab.resource.path in properties, if found scan the path to resolve
  // * the passed name as a file url. If the url is already resolved, just return it. If
  // * the path contains a http-based URL prefix just use that without checking.
  // *
  // * @param url
  // * @param properties
  // * @return a resolved url or the original one if not resolved.
  // */
  // public static String resolveUrl(String url, Properties properties) {
  //
  // String ret = url;
  //
  // if (ret.contains(":/"))
  // return ret;
  //
  // String prop = ".";
  //
  // for (String path : prop.split(";")) {
  //
  // if (path.startsWith("http") && path.contains("/")) {
  // ret = path + url;
  // break;
  // }
  //
  // File pth = new File(path + File.separator + url);
  //
  // if (pth.exists()) {
  // try {
  // ret = pth.toURI().toURL().toString();
  // break;
  // } catch (MalformedURLException e) {
  // }
  // }
  // }
  //
  // return ret;
  // }

  /**
   * Copy the given URL to the given local file, return number of bytes copied.
   *
   * @param url the URL
   * @param file the File
   * @return the number of bytes copied.
   * @throws org.integratedmodelling.klab.exceptions.KlabIOException if URL can't be read or file
   *         can't be written.
   */
  public static long copy(URL url, File file) throws KlabIOException {
    long count = 0;
    int oneChar = 0;

    try {
      InputStream is = url.openStream();
      FileOutputStream fos = new FileOutputStream(file);

      while ((oneChar = is.read()) != -1) {
        fos.write(oneChar);
        count++;
      }

      is.close();
      fos.close();
    } catch (Exception e) {
      throw new KlabIOException(e.getMessage());
    }

    return count;
  }

  /**
   * The listener interface for receiving copy events. The class that is interested in processing a
   * copy event implements this interface, and the object created with that class is registered with
   * a component using the component's <code>addCopyListener</code> method. When the copy event
   * occurs, that object's appropriate method is invoked.
   *
   */
  public interface CopyListener {
    void onProgress(int percent);
  }

  /**
   * Copy.
   *
   * @param url the url
   * @param file the file
   * @param listener the listener
   * @param size pass an approx size in case the server does not pass the length
   * @return nothing
   * @throws org.integratedmodelling.klab.exceptions.KlabIOException the klab IO exception
   */
  public static long copy(URL url, File file, CopyListener listener, long size)
      throws KlabIOException {

    long count = 0;

    try {

      URLConnection connection = url.openConnection();

      /*
       * set configured timeout
       */
      if (Configuration.INSTANCE.getProperties()
          .containsKey(Configuration.KLAB_CONNECTION_TIMEOUT)) {
        int timeout = 1000 * Integer.parseInt(Configuration.INSTANCE.getProperties()
            .getProperty(Configuration.KLAB_CONNECTION_TIMEOUT, "10"));
        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);

      }
      long stated = connection.getContentLengthLong();
      if (stated > 0) {
        size = stated;
      }

      InputStream is = url.openStream();
      FileOutputStream fos = new FileOutputStream(file);

      byte[] buf = new byte[1024];
      int len;
      int progress = 0;
      while ((len = is.read(buf)) > 0) {
        fos.write(buf, 0, len);
        count += len;
        progress = (int) (((double) count / (double) size) * 100.0);
        listener.onProgress(progress);
      }

      if (progress < 100) {
        listener.onProgress(100);
      }

      is.close();
      fos.close();

    } catch (Exception e) {
      throw new KlabIOException(e.getMessage());
    }

    return count;
  }

  /**
   * Copy channeled.
   *
   * @param url the url
   * @param file the file
   * @throws org.integratedmodelling.klab.exceptions.KlabIOException the klab IO exception
   */
  public static void copyChanneled(URL url, File file) throws KlabIOException {

    InputStream is = null;
    FileOutputStream fos = null;

    try {
      is = url.openStream();
      fos = new FileOutputStream(file);
      ReadableByteChannel rbc = Channels.newChannel(is);
      fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    } catch (Exception e) {
      throw new KlabIOException(e.getMessage());
    } finally {
      if (is != null)
        try {
          is.close();
        } catch (IOException e) {
        }
      if (fos != null)
        try {
          fos.close();
        } catch (IOException e) {
        }
    }
  }

  /**
   * Copy the given File to the given local file, return number of bytes copied.
   *
   * @param url the URL
   * @param file the File
   * @return the number of bytes copied.
   * @throws org.integratedmodelling.klab.exceptions.KlabIOException if URL can't be read or file
   *         can't be written.
   */
  public static long copy(File url, File file) throws KlabIOException {
    long count = 0;

    try {
      InputStream is = new FileInputStream(url);
      FileOutputStream fos = new FileOutputStream(file);

      int oneChar;
      while ((oneChar = is.read()) != -1) {
        fos.write(oneChar);
        count++;
      }

      is.close();
      fos.close();
    } catch (Exception e) {
      throw new KlabIOException(e.getMessage());
    }

    return count;
  }

  /**
   * Copy buffered.
   *
   * @param src the src
   * @param dst the dst
   * @throws org.integratedmodelling.klab.exceptions.KlabIOException the klab IO exception
   */
  public static void copyBuffered(File src, File dst) throws KlabIOException {

    try {
      InputStream in = new FileInputStream(src);
      OutputStream out = new FileOutputStream(dst);

      // Transfer bytes from in to out
      byte[] buf = new byte[1024];
      int len;
      while ((len = in.read(buf)) > 0) {
        out.write(buf, 0, len);
      }
      in.close();
      out.close();
    } catch (Exception e) {
      throw new KlabIOException(e.getMessage());
    }

  }

  /**
   * Gets the file for URL.
   *
   * @param url the url
   * @return the file for URL
   * @throws org.integratedmodelling.klab.exceptions.KlabIOException the klab IO exception
   */
  public static File getFileForURL(URL url) throws KlabIOException {
    if (url.toString().startsWith("file:")) {
      return new File(UrlEscape.unescapeurl(url.getFile()));
    } else {
      File temp;
      try {
        temp = File.createTempFile("url", "url");
      } catch (IOException e) {
        throw new KlabIOException(e);
      }
      copy(url, temp);
      return temp;
    }
  }

}
