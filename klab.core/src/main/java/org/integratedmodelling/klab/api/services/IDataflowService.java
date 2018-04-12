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
package org.integratedmodelling.klab.api.services;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import org.integratedmodelling.kdl.api.IKdlDataflow;
import org.integratedmodelling.kim.api.IKimAction.Trigger;
import org.integratedmodelling.kim.api.data.ILocator;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * Services related to dataflows and the KDL language.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IDataflowService {

  /**
   * Read and return the dataflow specifications corresponding to the passed
   * input, which is expected to contain valid KDL.
   *
   * @param input a {@link java.io.InputStream} object.
   * @return the parsed dataflow.
   * @throws org.integratedmodelling.klab.exceptions.KlabException
   */
  IKdlDataflow declare(InputStream input) throws KlabException;

  /**
   * Read and return the dataflow specifications corresponding to the passed
   * input file, which is expected to contain valid KDL.
   *
   * @param file a {@link java.io.File} object.
   * @return the parsed dataflow.
   * @throws org.integratedmodelling.klab.exceptions.KlabException
   */
  IKdlDataflow declare(File file) throws KlabException;

  /**
   * Read and return the dataflow specifications corresponding to the passed
   * input URL, which is expected to contain valid KDL.
   *
   * @param url a {@link java.net.URL} object.
   * @return the parsed dataflow.
   * @throws org.integratedmodelling.klab.exceptions.KlabException
   */
  IKdlDataflow declare(URL url) throws KlabException;

  /**
   * Compile a resolution scope into a dataflow computing the passed artifact type.
   *
   * @param name a {@link java.lang.String} object.
   * @param scope a {@link org.integratedmodelling.klab.api.resolution.IResolutionScope} object.
   * @return a dataflow that will compute an artifact of the requested type when run.
   * @throws org.integratedmodelling.klab.exceptions.KlabException
   * @param <T> a T object.
   */
  <T extends IArtifact> IDataflow<T> compile(String name, IResolutionScope scope)
      throws KlabException;

  /**
   * Given a transition, return all the action triggers that pertain to it. If more than one trigger
   * is returned, any actions corresponding to the first will be applied before, and the second
   * after, the transition event: e.g. definition vs. resolution or (last) transition vs.
   * termination.
   *
   * @param transition a {@link org.integratedmodelling.kim.api.data.ILocator} object.
   * @return all pertaining triggers. Possibly empty, never null.
   */
  List<Trigger> getActionTriggersFor(ILocator transition);

}
