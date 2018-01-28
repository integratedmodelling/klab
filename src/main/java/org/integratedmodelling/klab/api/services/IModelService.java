package org.integratedmodelling.klab.api.services;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

public interface IModelService {

    /**
     * A wrapper for a model that includes its ranking in resolving a particular observable and the coverage
     * of the resolution. Returned by {@link IModelService#resolve(IObservable, IResolutionScope)}.
     * 
     * @author Ferd
     *
     */
    public interface RankedModel extends IModel {

        /**
         * Coverage resulting from resolution, i.e. the portion of the scale that will be covered once the model has been
         * used to produce the observation.
         * 
         * @return the coverage of the resolution
         */
        ICoverage getCoverage();

        /**
         * Breakdown of the resolution criteria with the corresponding ranks.
         * 
         * @return the individual ranks.
         */
        Map<String, Double> getRanks();

        /**
         * The network ID of the server that hosts this model. Null if the model
         * is available locally.
         * 
         * @return network ID of server
         */
        Optional<String> getServer();
    }

    /**
     * Release all models pertaining to named namespace, both in live and persistent storage.
     * 
     * @param name
     */
    void releaseNamespace(String name);

    /**
     * Store model in kbox.
     * 
     * @param model
     */
    void index(IModel model);

    /**
     * Resolve the passed observable to a list of ranked models.
     * 
     * @param observable
     * @param scope
     * @return the list of candidates in decreasing rank.
     */
    List<RankedModel> resolve(IObservable observable, IResolutionScope scope);

    /**
     * Load a single model file from a URL. Namespace must have no dependencies and name a worldview at the
     * top.
     * 
     * @param url
     * @param monitor 
     * @return the namespace loaded
     * @throws KlabException
     */
    INamespace load(URL url, IMonitor monitor) throws KlabException;

    /**
     * Load a single model file from a file. Namespace must have no dependencies and name a worldview at the
     * top.
     * 
     * @param file
     * @param monitor 
     * @return the namespace loaded
     * @throws KlabException
     */
    INamespace load(File file, IMonitor monitor) throws KlabException;

    /**
     * Load a single model file from an inputstream. Namespace must have no dependencies and name a worldview
     * at the top.
     * 
     * @param input
     * @param monitor 
     * @return the namespace loaded
     * @throws KlabException
     */
    INamespace load(InputStream input, IMonitor monitor) throws KlabException;

}
