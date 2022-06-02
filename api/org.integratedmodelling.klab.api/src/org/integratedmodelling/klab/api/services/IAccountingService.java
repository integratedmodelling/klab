package org.integratedmodelling.klab.api.services;

import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.knowledge.IObservedConcept;
import org.integratedmodelling.klab.api.observations.scale.IScale;

/**
 * This service provides an interface to the accounting API and the estimation engine, which should
 * be shared between engine instances and fed with data based on usage. The estimation engine could
 * reside wherever the statistics service is, arguably within an engine cluster and possibly with a
 * global coordinator in the hub, and can use machine learning or simple averages to provide up to
 * date estimates for the cost of making observations.
 * <p>
 * The common currency for observation costs is the k.LAB credit, which can then be converted, when
 * appropriate, into currencies based on prices established for each user profile.
 * 
 * @author Ferd
 *
 */
public interface IAccountingService {

    /**
     * Return the estimated cost of the passed observation in the passed scale in k.LAB credits
     * (KLB), which are then converted to a user's currency based on the established per-credit
     * price.
     * 
     * @param observable the observable with the mode of observation requested.
     * @param scale the scale in which the observation must be made.
     * @return the estimated cost of observation in KLB
     */
    long estimate(IObservedConcept observable, IScale scale);

    /**
     * Get the user balance in k.LAB credits. This is kept irrespective of the price per credit set
     * for the user, which can be zero.
     * 
     * @param user
     * @return
     */
    long getBalance(IUserIdentity user);

    /**
     * Charge the user the passed amount of k.LAB credits and update their balance. Also pass the
     * actual cost computed during contextualization, so that statistics on how the two match in
     * case of previous estimations can be kept.
     * 
     * @param user
     * @param chargedCost
     * @param actualCost
     */
    void charge(IUserIdentity user, long chargedCost, long actualCost);

}
