/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
package org.integratedmodelling.klab.api.data;

import java.util.List;

import org.integratedmodelling.klab.api.geometry.KGeometry;
import org.integratedmodelling.klab.api.geometry.KLocator;
import org.integratedmodelling.klab.api.knowledge.KArtifact;
import org.integratedmodelling.klab.api.knowledge.KConcept;
import org.integratedmodelling.klab.api.knowledge.observation.scale.KScale;
import org.integratedmodelling.klab.api.knowledge.observation.scope.KContextScope;
import org.integratedmodelling.klab.api.services.runtime.KNotification;

/**
 * Encoded k.LAB data object, resulting from decoding a resource URN in a
 * specified geometry. The interface supports both direct building within an
 * existing artifact or setting of data into the Protobuf-based encoding for
 * remote consumption.
 * <p>
 * A builder is passed to each {@link IResourceEncoder encoder} by the runtime.
 * The builder is then used to build a {@code IKlabData} object which is in turn
 * used to extract the final artifact.
 * 
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface KKlabData {

	/**
	 * A builder is passed to each {@link IResourceEncoder encoder} by the runtime,
	 * set appropriately to ensure that no unnecessary storage is wasted.
	 * <p>
	 * When the builder is used, a {@IComputationContext runtime context} will be
	 * available, and should be used to inquire about the names and types of the
	 * target artifacts expected. * @author ferdinando.villa
	 *
	 */
	interface Builder {

		/**
		 * Get a builder that defines a state. Any further operation will operate on the
		 * object until {@link finishState()} is called.
		 * <p>
		 * If this was called before at the same level, the new artifact will be chained
		 * to the previous when built.
		 * 
		 * @param name TODO
		 * @param unit the unit this will be set in if predefined, which may need to be
		 *             converted at the receiving end if the adapter isn't flexible. Can
		 *             and should be null when appropriate.
		 * @param the  scope must be passed in case there is an extensive unit and it
		 *             needs to be matched to the extents of contextualization.
		 * @return a builder on which the add() functions can be called.
		 * @throws IllegalArgumentException if the state being build has a name not
		 *                                  recognized by the context associated with
		 *                                  this builder.
		 */
		Builder startState(String name, String unit, KContextScope scope);

		/**
		 * Add a value to the state being defined by this builder. The state is added in
		 * the k.LAB natural order for the geometry associated with the builder, i.e.
		 * starting at an offset of 0 and moving up by 1 at every add. TODO The locator must be
		 * passed if unit conversions are required; pass a simple offset if not.
		 * 
		 * @param value
		 * @throws IllegalStateException if {@link #startState(String)} has not been
		 *                               called.
		 */
        void add(Object value/* , ILocator locator */);

		/**
		 * Add a value to the state being defined by this builder at the passed locator.
		 * 
		 * @param value
		 * @param locator
		 * @throws IllegalStateException if {@link #startState(String)} has not been
		 *                               called.
		 */
		void set(Object value, KLocator locator);

		/**
		 * Finish building a state artifact and return the original builder on which
		 * {@link #startState(String)} was called.
		 * 
		 * @return the builder on which {@link startState()} was called.
		 * @throws IllegalStateException if {@link startState()} was not called before.
		 */
		Builder finishState();

		/**
		 * Get a builder that defines an object. Any further operation should operate on
		 * the object until {@link #finishObject()} is called, returning the builder
		 * that gets this call.
		 * <p>
		 * If this was called before at the same level, the new artifact will be chained
		 * to the previous when built.
		 * 
		 * @param artifactName the name of the target artifact (obtained through the
		 *                     runtime context)
		 * @param objectName   the name of the object (which should be unique)
		 * @param scale        the scale for the new object
		 * @return an object builder
		 * @throws IllegalArgumentException if the artifact name is not recognized by
		 *                                  the context associated with this builder.
		 */
		Builder startObject(String artifactName, String objectName, KGeometry scale);

		/**
		 * Finishes the object definition and sets the returned context back to the
		 * original builder.
		 * 
		 * @return the builder on which startObject() was called.
		 * @throws IllegalStateException if {@link startState()} was not called before.
		 */
		Builder finishObject();

		/**
		 * Set a property for the metadata associated with the artifact being built.
		 * 
		 * @param property
		 * @param object
		 * @return the builder itself
		 */
		Builder withMetadata(String property, Object object);

		/**
		 * Semantics is given only once per resolution and may be the only output if the
		 * observation is a characterization. The result is one concept, which may be an
		 * OR of several related ones (which also enforces proper return values due to
		 * union validation).
		 * 
		 * @param semantics
		 * @return
		 */
		Builder withSemantics(KConcept semantics);

		/**
		 * Add a notification to the result. Notifications are global, i.e. they refer
		 * to all artifacts built.
		 * 
		 * @param notification
		 * @return the builder itself
		 */
		Builder addNotification(KNotification notification);

		/**
		 * Build the final data object.
		 * 
		 * @return the finished data
		 */
		KKlabData build();
	}

	/**
	 * Return the primary artifact that we are meant to build, building it if
	 * necessary.
	 * 
	 * @return the primary artifact for the builder.
	 */
	KArtifact getArtifact();

	/**
	 * The artifact type of the primary artifact.
	 * 
	 * @return
	 */
	KArtifact.Type getArtifactType();

	/**
	 * Return any notifications passed through a builder. Notifications are a global
	 * list that refers to all artifacts.
	 * 
	 * @return all notifications
	 */
	List<KNotification> getNotifications();

	/**
	 * Return the number of objects at the level of this data response, 0 if
	 * !type.isCountable(), 0 or more if object or event.
	 *
	 * @return
	 */
	int getObjectCount();

	/**
	 * The number of states in the primary artifact, normally 1 if type == quality
	 * or 0 if not.
	 * 
	 * @return
	 */
	int getStateCount();

	/**
	 * True if errors happened and results should not be used. Normally linked to
	 * the existence of error-level notifications, but implementations can provide
	 * what they prefer.
	 * 
	 * @return true if errors
	 */
	boolean hasErrors();

	/**
	 * 
	 * @param i
	 * @return
	 */
	KScale getObjectScale(int i);

	/**
	 * 
	 * @param i
	 * @return
	 */
	String getObjectName(int i);

	/**
	 * 
	 * @param i
	 * @return
	 */
	KMetadata getObjectMetadata(int i);

	/**
	 * Normally null, unless the resource is a characterizer that classifies an
	 * object or a resolves an abstract trait or role into one or more (in OR)
	 * concrete ones. The results are worldview-bound.
	 * 
	 * @return
	 */
	KConcept getSemantics();
	
	/**
	 * Get overall metadata for the resource extraction operation. 
	 * 
	 * @return
	 */
	KMetadata getMetadata();

}
