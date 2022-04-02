package org.integratedmodelling.klab;

import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.knowledge.IObservedConcept;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.services.IAccountingService;

public enum Accounting implements IAccountingService {

    INSTANCE;

    private Accounting() {
        Services.INSTANCE.registerService(this, IAccountingService.class);
    }

    @Override
    public long estimate(IObservedConcept observable, IScale scale) {
        // TODO entirely to define. For now assign a 1000th of the size of the scale, no matter the
        // observable or the observation mode.
        return (long) (scale.size() / 1000.0);
    }

    /**
     * Pass after any successful contextualization to analyze and collect data, potentially also
     * feeding the dataset for machine learning, either locally or remotely. Time may vary greatly
     * in different contexts, independent of the extent and potentially also of the size of the
     * scale.
     * 
     * @param observable the observable
     * @param scale the scale
     * @param dataflow the sub-dataflow created specifically for this observable. Should not be used
     *        if it contains external references.
     * @param millisecondsToResolve time spent in resolving the observable
     * @param millisecondsToInitialize time spent running the initialization dataflow
     * @param millisecondsToRun time spent running temporal transitions
     * @param millisecondsToSummarize time spent running termination tasks
     */
    public void submit(IObservedConcept observable, IScale scale, IDataflow<?> dataflow,
            long millisecondsToResolve, long millisecondsToInitialize, long millisecondsToRun,
            long millisecondsToSummarize) {

    }

    @Override
    public long getBalance(IUserIdentity user) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void charge(IUserIdentity user, long chargedCost, long actualCost) {
        // TODO Auto-generated method stub
        
    }

}
