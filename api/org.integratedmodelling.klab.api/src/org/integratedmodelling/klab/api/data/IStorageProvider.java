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
package org.integratedmodelling.klab.api.data;

import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;

/**
 * Interface for a component. If exactly one component implementing this is available, the system
 * will use that. Otherwise the configuration must be able to establish which storage component to
 * use.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IStorageProvider {

  /**
   * Create appropriate storage for the passed observable and scale. The storage must be able to
   * promote itself to probabilistic if a distribution is ever passed.
   *
   * @param observable a {@link org.integratedmodelling.klab.api.knowledge.IObservable} object.
   * @param scale a {@link org.integratedmodelling.klab.api.observations.scale.IScale} object.
   * @param context a {@link org.integratedmodelling.klab.api.runtime.IContextualizationScope} object.
   * @return a {@link org.integratedmodelling.klab.api.data.artifacts.IDataArtifact} object.
   */
  IStorage<?> createStorage(IArtifact.Type type, IScale scale, IContextualizationScope context);

}
