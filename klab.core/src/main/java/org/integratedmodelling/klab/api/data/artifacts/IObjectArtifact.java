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
package org.integratedmodelling.klab.api.data.artifacts;

import java.util.Collection;
import org.integratedmodelling.klab.api.provenance.IArtifact;

/**
 * A {@code IArtifact} representing a first-class object, which may own other artifacts and has a
 * name. Additional API for a direct observation.
 *
 * @author Ferd
 * @version $Id: $Id
 */
public interface IObjectArtifact extends IArtifact {

  /**
   * Objects have names.
   *
   * @return the object's name
   */
  String getName();

  /**
   * Objects may own other objects. The ownership is one of the three structures in a context: 1)
   * artifact grouping (provenance); 2) ownership structure (always a tree), and 3)
   * relationship/network structure.
   *
   * @return all the directly owned objects
   */
  Collection<IArtifact> getChildren();

}
