//package org.integratedmodelling.klab.api.observations;
//
///**
// * An observation that has different states or contents based on who made it. The "observer" is
// * another direct observation in the same context. One observation may have several observers, and
// * the viewpoint may be switched to another's by calling {@link #setObserver(IDirectObservation)}.
// * 
// * We may eventually create a subject to represent the session user for consistency, but as of the
// * current version this is not done and the straight IObservation is considered objective.
// * 
// * @author Ferd
// * @deprecated should be merged into IAgent, each observation will be tagged with its observer and
// *             there's no need for the agent.
// */
//public abstract interface ISubjectiveObservation extends IObservation {
//
////    /**
////     * The subject observation that contextualized this observation. This is not the same as the
////     * context observation: it allows recording different viewpoints on observations that are
////     * contextual to the same observable - e.g. qualities of the same subject seen by different
////     * child subjects in it.
////     * 
////     * @return the subject that currently provides the viewpoint for this observation, or empty if
////     *         this was a user-made observation.
////     */
////    IDirectObservation getObserver();
//
//    /**
//     * Switch the viewpoint to that of the passed observer. The observer must have been previously
//     * set into the observation.
//     * 
//     * @param observer
//     */
//    void setObserver(IDirectObservation observer);
//
//}
