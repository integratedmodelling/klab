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

import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.provenance.IArtifact;

/**
 * When a {@link IKimModel model specification} is the result of an observation activity, the
 * {@code IModelArtifact} is the produced artifact. This happens in <strong>learning
 * processes</strong>, where the result of contextualizing knowledge is a model instead of a
 * finished observation.
 * <p>
 * 
 * @author ferdinando.villa
 *
 */
public interface IModelArtifact extends IArtifact {

  /**
   * The model specification produced by the activity. This can be serialized to k.IM or turned into
   * a {@link IModel} for distribution or execution.
   * 
   * @return the model specifications
   */
  IKimModel getModel();

}
