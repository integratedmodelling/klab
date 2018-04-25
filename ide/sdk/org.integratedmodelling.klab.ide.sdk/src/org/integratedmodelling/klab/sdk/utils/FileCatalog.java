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
package org.integratedmodelling.klab.sdk.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

// TODO: Auto-generated Javadoc
/**
 * A catalog (simply a map String -> T) read from a JSON file and capable of resynchronizing
 * intelligently on request.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 * @param <T> the generic type
 */
public class FileCatalog<T> extends HashMap<String, T> {

  private static final long serialVersionUID = 8664294752013639030L;
  
  Class<? extends T>        cls;
  File                      file;
  long                      timestamp;
  boolean                   error;

  /**
   * Creates the.
   *
   * @param <T> the generic type
   * @param url the url
   * @param type the type
   * @param cls the cls
   * @return the file catalog
   */
  public static <T> FileCatalog<T> create(URL url, Class<T> type, Class<? extends T> cls) {
    return new FileCatalog<T>(url, type, cls);
  }

  /**
   * Creates the.
   *
   * @param <T> the generic type
   * @param file the file
   * @param type the type
   * @param cls the cls
   * @return the file catalog
   */
  public static <T> FileCatalog<T> create(File file, Class<T> type, Class<? extends T> cls) {
    return new FileCatalog<T>(file, type, cls);
  }

  private FileCatalog(URL url, Class<T> type, Class<? extends T> cls) {
    this.cls = cls;
    try (InputStream input = url.openStream()) {
      this.error = !synchronize(input);
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Instantiates a new file catalog.
   *
   * @param file the file
   * @param type the type
   * @param cls the cls
   */
  public FileCatalog(File file, Class<? extends T> type, Class<? extends T> cls) {

    this.file = file;
    this.cls = cls;
    try (InputStream input = new FileInputStream(file)) {
      this.error = !synchronize(input);
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Checks for errors.
   *
   * @return a boolean.
   */
  public boolean hasErrors() {
    return this.error;
  }

  /**
   * Synchronize, reading the file if necessary.
   *
   * @param stream the stream
   * @return true if no errors. Non-existing file is not an error.
   * @throws java.lang.ClassCastException if the data read are not of the type configured
   */
  public boolean synchronize(InputStream stream) {

    boolean ret = true;

    if (this.file == null || (this.file.exists() && this.timestamp < this.file.lastModified())) {

      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

      try {
        JavaType type =
            objectMapper.getTypeFactory().constructMapLikeType(Map.class, String.class, cls);
        Map<Object, T> data = objectMapper.reader(type).readValue(stream);
        clear();
        for (Object key : data.keySet()) {
          put(key.toString(), (T) data.get(key));
        }
        this.timestamp = this.file == null ? System.currentTimeMillis() : this.file.lastModified();
      } catch (IOException e) {
        ret = false;
      }
    }

    return ret;

  }

}
