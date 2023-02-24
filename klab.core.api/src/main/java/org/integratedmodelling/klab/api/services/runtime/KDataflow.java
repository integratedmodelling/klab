/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify it under the terms of the Affero
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root directory of the k.LAB
 * distribution (LICENSE.txt). If this cannot be found see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned in author tags. All
 * rights reserved.
 */
package org.integratedmodelling.klab.api.services.runtime;

import org.integratedmodelling.klab.api.knowledge.KArtifact;
import org.integratedmodelling.klab.api.knowledge.observation.KObservation;
import org.integratedmodelling.klab.api.knowledge.observation.scale.KScale;

/**
 * Dataflows in k.LAB represent "raw" computations, which create, compute and link
 * {@link org.integratedmodelling.klab.api.data.KObjectArtifact.IObjectArtifact}s in response to a request
 * for observation of a given semantic
 * {@link org.integratedmodelling.klab.api.resolution.IResolvable}. The computation is stripped of
 * all semantics; therefore it can be run by a semantically-unaware workflow system.
 * <p>
 * Dataflows are serialized and rebuilt from KDL specifications by
 * {@link org.integratedmodelling.klab.api.services.IDataflowService}. Dataflows are also built by
 * the engine after resolving a IResolvable, and can be serialized to KDL if necessary using
 * {@link #getKdlCode()}.
 * <p>
 * The end result of {@link #run(KScale, IMonitor) running a dataflow} in a given scale is a
 * {@link org.integratedmodelling.klab.api.provenance.KArtifact}. In k.LAB, this corresponds to
 * either a {@link org.integratedmodelling.klab.api.KObservation.IObservation} (the usual case) or a
 * {@link org.integratedmodelling.klab.api.KModel.IModel} (when the computation is a learning
 * activity, which builds an explanation of a process). Dataflows built
 * {@link org.integratedmodelling.klab.api.services.IObservationService#resolve(String, org.integratedmodelling.klab.api.runtime.ISession, String[])
 * within the k.LAB runtime} as a result of a semantic resolution will produce {@link KObservation
 * observations}, i.e. semantic artifacts. But if those dataflows are {@link #getKdlCode()
 * serialized}, loaded and run, they will produce non-semantic artifacts as the semantic information
 * is not preserved in the dataflow specifications.
 * <p>
 * Dataflows written by users or created by k.LAB can be stored on k.LAB nodes as URN-specified
 * computations, which can be referenced in k.LAB models. The KDL language that specified dataflows
 * is also used to define service contracts for k.IM-callable services or remote computations
 * accessed through REST calls.
 * <p>
 * A dataflow is the top-level {@link KActuator actuator} of a k.LAB computation. It adds top-level
 * semantics to the actuator's contract. Only a dataflow can be run and serialized from the API.
 * <p>
 * The KDL specification and the parser provided in the klab-kdl project provide a bridge to
 * different workflow systems. Models of computation are inferred in k.LAB and depend on the
 * specific {@link IRuntimeProvider runtime} adopted as well as on the semantics of the services
 * (actors) used; exposing the computational model is work in progress.
 * <p>
 * TODO expose all metadata and context fields.
 * <p>
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 * @param <T> the most specific type of artifact this dataflow will build when run.
 * @since 0.10.0
 */
public interface KDataflow<T extends KArtifact> extends KActuator {

//    /**
//     * Each dataflow in the hierarchy must be able to produce the root dataflow, corresponding to
//     * the one that created the root observation.
//     * 
//     * @return
//     */
//    IDataflow<T> getRootDataflow();
//
//    /**
//     * Run the dataflow in the passed scale using the configured or default
//     * {@link org.integratedmodelling.klab.api.runtime.IRuntimeProvider} and return the resulting
//     * artifact.
//     *
//     * @param scale the scale of contextualization. Assumed (and not checked) compatible with the
//     *        scale of the resolution that generated this dataflow. The dataflow's perdurants will
//     *        initially be resolved in the initialization scale. TODO the scale should be checked
//     *        against the coverage and the empty artifact should be returned if incompatible.
//     * @param scope the scope for the contextualization. Should contain the scale, which should also
//     *        not be passed.
//     * @return the built artifact. May be empty, never null.
//     * @throws org.integratedmodelling.klab.exceptions.KlabException
//     */
//    T run(IScale scale, IContextScope scope);

//    /**
//     * Return the k.DL source code for the dataflow. If the dataflow has been read from a k.DL
//     * stream, return the original code, otherwise reconstruct it by decompiling the dataflow. The
//     * code must be syntactically correct and usable within a resource. If serialization of large
//     * and complex extents makes the code unsuitable for transmission to clients, the runtime should
//     * not encode those directly but reference appropriate sidecar files which must be built by
//     * export().
//     *
//     * @return the k.DL code. Never null.
//     */
//    String getKdlCode();
//
//    /**
//     * Export the dataflow as a basename.kdl file in the passed directory, adding any needed side
//     * files in it.
//     * 
//     * @param baseName
//     * @param directory
//     */
//    void export(String baseName, File directory);

    /**
     * An empty dataflow is a valid dataflow that produces an
     * {@link org.integratedmodelling.klab.api.provenance.KArtifact#isEmpty() empty artifact} when
     * run in its scale.
     *
     * @return true if the dataflow is empty
     */
    boolean isEmpty();

}
