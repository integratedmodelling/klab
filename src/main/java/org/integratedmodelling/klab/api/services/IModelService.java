package org.integratedmodelling.klab.api.services;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

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
     * @return the coverage of the resolution
     */
    ICoverage getCoverage();

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
   * Load a single model file from an inputstream. Namespace must have no dependencies and name a
   * worldview at the top.
   * 
   * @param input
   * @param monitor
   * @return the namespace loaded
   * @throws KlabException
   */
  INamespace load(InputStream input, IMonitor monitor) throws KlabException;

}
