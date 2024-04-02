package org.integratedmodelling.klab.engine.runtime;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.integratedmodelling.klab.Accounting;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.dataflow.ObservedConcept;
import org.integratedmodelling.klab.engine.runtime.Session.Estimate;
import org.integratedmodelling.klab.rest.ContextRequest;
import org.integratedmodelling.klab.rest.ObservationRequest;
import org.integratedmodelling.klab.utils.Pair;

/**
 * Carries on observation requests received through the public API, handling task orchestration and
 * notification through the ticket system.
 * 
 * Handles both estimates and observations. If estimates are requested, the results are entirely
 * sent through the tickets.
 * 
 * @author Ferd
 *
 */
public class APIObservationTask extends Thread {

    private ContextRequest contextRequest;
    private IScale scale;
    private ObservationRequest observationRequest;
    private ITicket ticket;
    private ISession session;
    private IDirectObservation contextObservation;
    // previously estimated cost, if any
    long estimatedCost = -1;

    // state computed during the observation, kept here to navigate the finals and
    // the closures
    private boolean error = false;

    public static void submit(ContextRequest request, ISession session, IScale scale, ITicket ticket) {
        APIObservationTask task = new APIObservationTask(request, session, scale, ticket);
        if (!request.isEstimate() && request.getEstimatedCost() >= 0) {
            task.estimatedCost = request.getEstimatedCost();
        }
        task.start();
    }

    public static void submit(ObservationRequest request, ISession session, IDirectObservation ctx, ITicket ticket) {
        APIObservationTask task = new APIObservationTask(request, session, ctx, ticket);
        if (!request.isEstimate() && request.getEstimatedCost() >= 0) {
            task.estimatedCost = request.getEstimatedCost();
        }
        task.start();
    }

    private APIObservationTask(ContextRequest request, ISession session, IScale scale, ITicket ticket) {
        this.contextRequest = request;
        this.session = session;
        this.scale = scale;
        this.ticket = ticket;
    }

    private APIObservationTask(ObservationRequest request, ISession session, IDirectObservation ctx, ITicket ticket) {
        this.observationRequest = request;
        this.session = session;
        this.contextObservation = ctx;
        this.ticket = ticket;
    }

    @Override
    public void run() {

    	((SessionState)session.getState()).setApplicationName("API");

        if ((contextRequest != null && contextRequest.isEstimate())
                || (observationRequest != null && observationRequest.isEstimate())) {
            estimate();
        } else {
            long actualCost = observe();
            Accounting.INSTANCE.charge(session.getUser(), estimatedCost < 0 ? actualCost : estimatedCost, actualCost);
        }
    }

    private Pair<Double, String> getUserCost(long klabCredits) {

        /*
         * TODO these must come from the user profile. Currency code KLB is legitimate for k.LAB
         * credits, at least until we all go live in k.Land.
         */
        String currency = "KLB";
        double pricePerCredit = 1.0;

        return new Pair<>(klabCredits * pricePerCredit, currency);
    }

    /**
     * Estimation of a task should compute the estimate in k.LAB credits, then convert into whatever
     * the price of a credit is for the user. Each user should have their currency and a price per
     * credit in that currency.
     */
    private void estimate() {

        /*
         * if price isn't fixed, compute the job size in k.LAB credits and assign a price.
         */
        long cost = 0;
        if (contextRequest != null) {

            IObservable contextObservable = Observables.INSTANCE.declare(contextRequest.getContextType());
            cost += Accounting.INSTANCE.estimate(new ObservedConcept(contextObservable, Mode.RESOLUTION), scale);

            for (String observable : contextRequest.getObservables()) {
                IObservable obs = Observables.INSTANCE.declare(observable);
                cost += Accounting.INSTANCE.estimate(new ObservedConcept(obs), scale);
            }

        } else if (observationRequest != null) {
            IObservable obs = Observables.INSTANCE.declare(observationRequest.getUrn());
            cost += Accounting.INSTANCE.estimate(new ObservedConcept(obs), contextObservation.getScale());
        }

        Pair<Double, String> userCost = getUserCost(cost);

        ticket.resolve("estimate", ticket.getId(), "cost", "" + userCost.getFirst(), "currency", "" + userCost.getSecond());

        /*
         * remember the estimate for charging if the estimate is accepted. The request will be
         * stored with the estimate field set to false and the cost set to 0+.
         */
        ((Session) session).getEstimates().put(ticket.getId(), new Estimate(cost, contextRequest, observationRequest));

    }

    /**
     * Make the observation and return the actual cost computed during contextualization.
     */
    private long observe() {

        // at the end this will either be the previously accepted estimated cost or the
        // actual cost
        // in KLB

        long actualCost = 0;
        IDirectObservation context = contextObservation;
        List<IObservable> observables = new ArrayList<>();
        List<String> scenarios = new ArrayList<>();
        if (observationRequest != null) {
            observables.add(Observables.INSTANCE.declare(observationRequest.getUrn()));
            scenarios.addAll(observationRequest.getScenarios());
        } else if (contextRequest != null) {
            for (String observable : contextRequest.getObservables()) {
                observables.add(Observables.INSTANCE.declare(observable));
            }
            scenarios.addAll(contextRequest.getScenarios());
        }

        /*
         * if we need a context, make it
         */
        if (contextObservation == null && contextRequest != null) {
            ITask<ISubject> contextObservationTask = null;
            if (contextRequest.getUrn() != null) {
                contextObservationTask = session.observe(contextRequest.getUrn());
            } else {
                IObservable contextObservable = Observables.INSTANCE.declare(contextRequest.getContextType());
                contextObservationTask = session.observe(contextObservable, scale);
            }
            contextObservationTask.addScenarios(scenarios);
            contextObservationTask.addObservationListener((task, subject) -> {
                if (subject != null) {
                    ticket.update("context", subject.getId());
                }
            });
            contextObservationTask.addErrorListener((task, exception) -> {
                ticket.error(ExceptionUtils.getStackTrace(exception));
                error = true;
            });

            try {
                context = contextObservationTask.get();
                actualCost += context.getMetadata().get(IMetadata.IM_OBSERVATION_COST, 0l);
            } catch (Throwable e) {
                ticket.error(ExceptionUtils.getStackTrace(e));
                error = true;
            }
        }

        if (error) {
            // satisfied or reimbursed
            return 0;
        }

        /*
         * make every observation
         */
        for (IObservable observable : observables) {

            IObservation observation = null;
            ITask<IObservation> observationTask = ((ISubject) context).observe(observable);
            observationTask.addScenarios(scenarios);
            observationTask.addObservationListener((task, obs) -> {
                if (obs != null && !obs.isEmpty()) {
                    String artifacts = ticket.getData().get("artifacts") == null ? "" : ticket.getData().get("artifacts");
                    artifacts += (artifacts.isEmpty() ? "" : ",") + obs.getId();
                    ticket.update("artifacts", artifacts);
                }
            });
            observationTask.addErrorListener((task, exception) -> {
                ticket.error(ExceptionUtils.getStackTrace(exception));
                error = true;
            });

            try {
                observation = observationTask.get();
            } catch (Throwable e) {
                ticket.error(ExceptionUtils.getStackTrace(e));
                error = true;
            }

            if (error) {
                return 0;
            }

            actualCost += observation.getMetadata().get(IMetadata.IM_OBSERVATION_COST, 0l);
        }

        Pair<Double, String> userCost = getUserCost(estimatedCost < 0 ? actualCost : estimatedCost);
        ticket.resolve("charge", "" + userCost.getFirst(), "currency", userCost.getSecond(), "actualcost", "" + actualCost);

        return actualCost;
    }

}
