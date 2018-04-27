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
package org.integratedmodelling.klab.api.model;

/**
 * A {@code IActiveKimObject} is a {@link org.integratedmodelling.klab.api.model.IKimObject} that exposes a {@link IBehavior behavior},
 * i.e. a set of runtime actions tied to contextualization events. The behavior may be
 * {@link org.integratedmodelling.klab.api.model.IBehavior#isEmpty() empty}.
 *
 * @author Ferd
 * @version $Id: $Id
 */
public interface IActiveKimObject extends IKimObject {

  /**
   * <p>getBehavior.</p>
   *
   * @return a {@link org.integratedmodelling.klab.api.model.IBehavior} object.
   */
  IBehavior getBehavior();

}
