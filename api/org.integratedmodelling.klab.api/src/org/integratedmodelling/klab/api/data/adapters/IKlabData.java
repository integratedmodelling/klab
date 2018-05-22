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
package org.integratedmodelling.klab.api.data.adapters;

import java.util.List;

import org.integratedmodelling.kim.api.INotification;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;

/**
 * Encoded k.LAB data resulting from decoding a resource URN in a specified
 * geometry. The interface supports both direct building within an existing
 * artifact or setting of data into the Protobuf-based encoding for remote
 * consumption.
 * <p>
 * A builder is passed to each {@link IResourceEncoder encoder} by the runtime.
 * The builder is then used to build a {@code IKlabData} object which is in turn
 * used to extract the final artifact.
 * 
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IKlabData {

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
		 * object until finishObject() is called.
		 * <p>
		 * If this was called before at the same level, the new artifact will be chained
		 * to the previous when built.
		 * 
		 * @param name
		 *            TODO
		 * @return a builder on which the add() functions can be called.
		 */
		Builder startState(String name);

		/**
		 * Add a double value to the current state.
		 * 
		 * @param doubleValue
		 * @throws IllegalStateException
		 *             if {@link #startState(String)} has not been called.
		 */
		void add(double doubleValue);

		/**
		 * 
		 * @param floatValue
		 * @throws IllegalStateException
		 *             if {@link #startState(String)} has not been called.
		 */
		void add(float floatValue);

		/**
		 * 
		 * @param intValue
		 * @throws IllegalStateException
		 *             if {@link #startState(String)} has not been called.
		 */
		void add(int intValue);

		/**
		 * 
		 * @param longValue
		 * @throws IllegalStateException
		 *             if {@link #startState(String)} has not been called.
		 */
		void add(long longValue);

		/**
		 * 
		 * @param booleanValue
		 * @throws IllegalStateException
		 *             if {@link #startState(String)} has not been called.
		 */
		void add(boolean booleanValue);

		/**
		 * 
		 * @param conceptValue
		 * @throws IllegalStateException
		 *             if {@link #startState(String)} has not been called.
		 */
		void add(IConcept conceptValue);

		// TODO add distribution values

		/**
		 * Finish building a state artifact.
		 * 
		 * @return the builder on which startState() was called.
		 */
		Builder finishState();

		/**
		 * Get a builder that defines an object. Any further operation will operate on
		 * the object until finishObject() is called.
		 * <p>
		 * If this was called before at the same level, the new artifact will be chained
		 * to the previous when built.
		 * 
		 * @param artifactName
		 *            the name of the target artifact (obtained through the runtime context)
		 * @param objectName
		 * 			  the name of the object (which should be unique)
		 * @param scale TODO
		 * @return an object builder
		 */
		Builder startObject(String artifactName, String objectName, IScale scale);

		/**
		 * Finishes the object definition and sets the returned context back to the
		 * original builder.
		 * 
		 * @return the builder on which startObject() was called.
		 */
		Builder finishObject();

		/**
		 * Set a property for the metadata associated with the artifact being built.
		 * 
		 * @param property
		 * @param object
		 * @return the builder itself
		 */
		Builder setProperty(String property, Object object);

		/**
		 * Add a notification to the result. Notifications are global, i.e. they refer
		 * to all artifacts built.
		 * 
		 * @param notification
		 * @return the builder itself
		 */
		Builder addNotification(INotification notification);

		/**
		 * Build the final data object.
		 * 
		 * @return the finished data
		 */
		IKlabData build();
	}

	/**
	 * Return the primary artifact that we are meant to build, building it if
	 * necessary.
	 * 
	 * @return the primary artifact for the builder.
	 */
	IArtifact getArtifact();

	/**
	 * Return any notifications passed through a builder. Notifications are a global
	 * list that refers to all artifacts.
	 * 
	 * @return all notifications
	 */
	List<INotification> getNotifications();

	/**
	 * True if errors happened and results should not be used. Normally linked to
	 * the existence of error-level notifications, but implementations can provide
	 * what they prefer.
	 * 
	 * @return true if errors
	 */
	boolean hasErrors();

}
