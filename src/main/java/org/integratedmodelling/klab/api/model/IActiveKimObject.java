package org.integratedmodelling.klab.api.model;

/**
 * A {@code IActiveKimObject} is a {@link IKimObject} that exposes a {@link IBehavior behavior},
 * i.e. a set of runtime actions tied to contextualization events. The behavior may be
 * {@link IBehavior#isEmpty() empty}.
 * 
 * @author Ferd
 *
 */
public interface IActiveKimObject extends IKimObject {

  IBehavior getBehavior();

}
