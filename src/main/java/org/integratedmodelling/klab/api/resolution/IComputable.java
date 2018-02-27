package org.integratedmodelling.klab.api.resolution;

import java.util.List;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.observations.scale.time.ITransition;

/**
 * The computable interface applies to objects that can contribute computations to a dataflow. If
 * so, they must be able to provide a declarative form of the computation as a list of service
 * calls that can be understood in KDL.
 * 
 * @author ferdinando.villa
 *
 */
public interface IComputable {

  List<IServiceCall> getComputation(ITransition transition);

}
