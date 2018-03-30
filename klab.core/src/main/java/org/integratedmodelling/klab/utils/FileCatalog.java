package org.integratedmodelling.klab.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A catalog (simply a map String -> T) read from a JSON file and capable of resynchronizing
 * intelligently on request.
 * 
 * @author ferdinando.villa
 *
 * @param <T>
 */
public class FileCatalog<T> extends HashMap<String, T> {

  private static final long serialVersionUID = 8664294752013639030L;

  Class<? extends T>        cls;
  File                      file;
  long                      timestamp;
  boolean                   error;

  public FileCatalog(File file, Class<? extends T> cls) {

    this.file = file;
    this.cls = cls;
    this.error = !synchronize();
  }

  public boolean hasErrors() {
    return this.error;
  }

  /**
   * Synchronize, reading the file if necessary.
   * 
   * @return true if no errors. Non-existing file is not an error.
   * @throws ClassCastException if the data read are not of the type configured
   */
  public boolean synchronize() {

    boolean ret = true;

    if (this.file.exists() && this.timestamp < this.file.lastModified()) {

      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      
      try {
        JavaType type =
            objectMapper.getTypeFactory().constructMapLikeType(Map.class, String.class, cls);
        Map<Object, T> data = objectMapper.readerFor(type).readValue(this.file);
        clear();
        for (Object key : data.keySet()) {
          put(key.toString(), (T) data.get(key));
        }
        this.timestamp = this.file.lastModified();
      } catch (IOException e) {
        ret = false;
      }
    }

    return ret;

  }

}
