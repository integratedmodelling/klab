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

// TODO: Auto-generated Javadoc
/**
 * Use one of these as a member to compare with when same-object equality needs to be assessed in an
 * object that has redefined equals() differently.
 *
 * @author Ferd
 * @version $Id: $Id
 */
public class InstanceIdentifier {

  static long __ID = 0L;

  // Returns the current thread's unique ID, assigning it if necessary
  private synchronized static long nextId() {
    return __ID++;
  }

  protected Long __id = nextId();

  /**
   * <p>hashCode.</p>
   *
   * @return a int.
   */
  public int hashCode() {
    return __id.hashCode();
  }

  /** {@inheritDoc} */
  public boolean equals(Object o) {
    return o instanceof InstanceIdentifier && __id.equals(((InstanceIdentifier) o).__id);
  }

  /**
   * Gets the value.
   *
   * @return the value
   */
  public long getValue() {
    return __id;
  }
}
