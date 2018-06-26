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
package org.integratedmodelling.klab.api.data.general;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * Simple execution interface for expressions. A new expression is generated per each call to the
 * corresponding language statement, so each object can store local data about its call context.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IExpression {

  /**
   * Execute the expression
   *
   * @param parameters from context or defined in a language call
   * @param context possibly empty, may be added to determine the result of the evaluation according
   *        to the calling context. The {@link IComputationContext#getMonitor() monitor in the
   *        context} will never be null and can be used to send messages or interrupt the
   *        computation.
   * @return the result of evaluating the expression
   * @throws org.integratedmodelling.klab.exceptions.KlabException TODO
   */
  Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException;

}
