/*******************************************************************************
 * Copyright (C) 2007, 2015:
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
package org.integratedmodelling.klab.api.data.general;

import java.util.Map;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * Simple execution interface for expressions. A new expression is generated per each call to the
 * corresponding language statement, so each object can store local data about its call context.
 * 
 * @author ferdinando.villa
 *
 */
public interface IExpression {

  /**
   * A context is always passed to exec(), although some or all of its members may be null.
   * 
   * FIXME probably not necessary having IComputationContext for expression services.
   * 
   * @author Ferd
   *
   */
  interface Context {

    /**
     * 
     * @return
     */
    INamespace getNamespace();

    /**
     * 
     * @return
     */
    IModel getModel();

    /**
     * @return
     */
    IObservable getObservable();

    /**
     * 
     * @return
     */
    IGeometry getGeometry();
  }

  /**
   * Execute the expression
   * 
   * @param parameters from context or defined in a language call
   * @param monitor
   * @param context usually null, may be added to determine the result of the evaluation according
   *        to the calling context.
   * @return the result of evaluating the expression
   * @throws KlabException TODO
   */
  Object eval(Map<String, Object> parameters, IMonitor monitor, Context context)
      throws KlabException;


  /**
   * Infer the geometry from the source code if possible. Used to define if and how to distribute
   * the computation.
   * 
   * @return the inferred geometry for the expression, or null.
   */
  IGeometry getGeometry();

}
