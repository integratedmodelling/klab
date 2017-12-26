package org.integratedmodelling.klab.api.services;

import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;

public interface IModelService {

    /**
     * A wrapper for a model that includes its ranking in resolving a 
     * particular observable and the coverage of the resolution. Returned
     * by {@link IModelService#resolve(IObservable, IResolutionScope)}.
     * 
     * @author Ferd
     *
     */
    public interface RankedModel extends IModel {

        /**
         * The final ranking from the resolver.
         * 
         * @return the final ranking
         */
        double getRank();
        
        /**
         * Coverage of resolution, i.e. the portion of the scale that will be
         * covered once the model has been used to produce the observation.
         * 
         * @return the coverage of resolution
         */
        ICoverage getCoverage();
        
        /**
         * Breakdown of the resolution criteria with the corresponding ranks.
         * 
         * @return the individual ranks.
         */
        Map<String, Double> getRanks();
    }


    
    /**
     * Release all models pertaining to named namespace, both in live and
     * persistent storage.
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
    
}
