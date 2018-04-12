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

import org.integratedmodelling.kim.api.IKimAction.Trigger;
import org.integratedmodelling.kim.api.IKimAction.Type;
import org.integratedmodelling.klab.api.resolution.IComputable;

/**
 * Action execution is deferred to dataflow through their {@link IComputable} identity.
 * 
 * @author ferdinando.villa
 *
 */
public interface IAction extends IComputable {

  /**
   * 
   * @return
   */
  Trigger getTrigger();

  /**
   * 
   * @return
   */
  Type getType();
  
}
