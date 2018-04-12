package org.integratedmodelling.kim.model;

import java.io.File;
import java.io.IOException;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.utils.Escape;
import org.integratedmodelling.kim.utils.NameGenerator;

/**
 * This class encodes the rules for creating URNs that describe local resources: literal values,
 * local files and function calls.
 *
 */
public enum Urns {

  INSTANCE;

  final public static String KLAB_URN_PREFIX   = "urn:klab:";
  final public static String LOCAL_URN_PREFIX  = "urn:klab:local:";
  final public static String VOID_URN_PREFIX   = "urn:klab:void:";
  final public static String LOCAL_FILE_PREFIX = "file:";

  /**
   * Create a unique URN that won't be accepted in any production resource catalog. For testing.
   * 
   * @return a new unprivileged, unique URN
   */
  public String createDisposableUrn() {
    return VOID_URN_PREFIX + NameGenerator.shortUUID();
  }

  public String getFileUrn(File file) {

    if (file == null) {
      throw new IllegalArgumentException("null argument to getFileUrn()");
    }

    /*
     * TODO look inside a project and ensure it is in one; use project ID as namespace. If not in a
     * project, which should only happen in testing, encode as "absolute" namespace.
     */

    try {
      return LOCAL_URN_PREFIX + LOCAL_FILE_PREFIX
          + Escape.forURL(file.getCanonicalPath().toString());
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public String getFunctionUrn(IServiceCall functionCall) {
    return "";
  }

  public boolean isLocal(String urn) {
    return urn.startsWith(LOCAL_URN_PREFIX) || urn.startsWith("local:")
        || urn.startsWith(LOCAL_FILE_PREFIX);
  }
}
