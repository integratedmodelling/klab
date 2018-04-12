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
import java.util.Map;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

// TODO: Auto-generated Javadoc
/**
 * The Interface IModelService.
 */
public interface IModelService {

  /**
   * A lazy wrapper for a model that includes its ranking in resolving a particular observable and
   * the coverage of the resolution. Returned by
   * {@link IModelService#resolve(IObservable, IResolutionScope)}.
   * 
   * @author Ferd
   *
   */
  public interface IRankedModel extends IModel {

    /**
     * Coverage resulting from resolution, i.e. the portion of the scale that will be covered once
     * the model has been used to produce the observation.
     * 
     * @return the model's coverage of the resolution context
     */
    ICoverage getContextCoverage();
    
    /**
     * Native coverage of the model independent of resolution. Intersected with any other model to compute
     * the coverage of the resulting dataflow.
     * 
     * @return coverage as a scale, or null for global coverage.
     */
    IScale getNativeCoverage();

    /**
     * Breakdown of the resolution criteria with the corresponding ranks.
     * 
     * @return the individual ranks.
     */
    Map<String, Object> getRanks();

  }

  /**
   * Release all models pertaining to named namespace, both in live and persistent storage.
   * 
   * @param namespace
   * @param monitor
   * @throws KlabException
   */
  void releaseNamespace(INamespace namespace, IMonitor monitor) throws KlabException;

  /**
   * Index the passed model in kbox.
   * 
   * @param model
   * @param monitor
   * @throws KlabException
   */
  void index(IModel model, IMonitor monitor) throws KlabException;

  /**
   * Resolve the passed observable to a list of ranked models, ordered from best to worst. The
   * returned models should work in a lazy way, only creating and returning the actual model (which
   * may involve network downloads of multiple projects or components) when any of the models'
   * functions are actually called.
   * 
   * @param observable
   * @param scope
   * @return the list of candidates in decreasing rank.
   * @throws KlabException
   */
  List<IRankedModel> resolve(IObservable observable, IResolutionScope scope) throws KlabException;

  /**
   * Load a single model file from a URL. Namespace must have no dependencies and name a worldview
   * at the top.
   * 
   * @param url
   * @param monitor
   * @return the namespace loaded
   * @throws KlabException
   */
  INamespace load(URL url, IMonitor monitor) throws KlabException;

  /**
   * Load a single model file. Namespace must have no dependencies and name a worldview at the top.
   * 
   * @param file
   * @param monitor
   * @return the namespace loaded
   * @throws KlabException
   */
  INamespace load(File file, IMonitor monitor) throws KlabException;

  /**
   * Load a single model file from a stream. Namespace must have no dependencies and name a
   * worldview at the top.
   * 
   * @param input
   * @param monitor
   * @return the namespace loaded
   * @throws KlabException
   */
  INamespace load(InputStream input, IMonitor monitor) throws KlabException;

}
