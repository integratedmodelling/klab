/*******************************************************************************
 * Copyright (C) 2007, 2014:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any other
 * authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable modular,
 * collaborative, integrated development of interoperable data and model components. For details,
 * see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any warranty; without
 * even the implied warranty of merchantability or fitness for a particular purpose. See the Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA. The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.utils;

/**
 * Use one of these as a member to compare with when same-object equality needs to be assessed in an
 * object that has redefined equals() differently.
 * 
 * @author Ferd
 *
 */
public class InstanceIdentifier {

  static long __ID = 0L;

  // Returns the current thread's unique ID, assigning it if necessary
  private synchronized static long nextId() {
    return __ID++;
  }

  protected Long __id = nextId();

  public int hashCode() {
    return __id.hashCode();
  }

  public boolean equals(Object o) {
    return o instanceof InstanceIdentifier && __id == ((InstanceIdentifier) o).__id;
  }

  public long getValue() {
    return __id;
  }
}
