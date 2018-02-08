package org.integratedmodelling.klab.api.model;

import java.util.List;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.resolution.IResolvable;

/**
 * An observer is the k.LAB object corresponding to an <code>observe</code> statement in k.IM, used to specify
 * an acknowledged observation. The k.LAB runtime can instantiate an (acknowledged) observation by running it.
 * 
 * The {@link #getChildren()} method of an IObserver only returns other IObservers.
 * 
 * @author Ferd
 *
 */
public interface IObserver extends IActiveKimObject, INamespaceQualified, IResolvable {

    /**
     * The concept this observes, a direct observable.
     * 
     * @return observed concept
     */
    IObservable getObservable();

    /**
     * Specifications for states are reported as observables, whose {@link IObservable#getValue()} is
     * guaranteed not to return null.
     * 
     * @return all stated indirect observations for the resulting observation.
     */
    List<IObservable> getStates();

    /**
     * Metadata can be associated to all observers in k.IM.
     * 
     * @return metadata (never null).
     */
    IMetadata getMetadata();

}
