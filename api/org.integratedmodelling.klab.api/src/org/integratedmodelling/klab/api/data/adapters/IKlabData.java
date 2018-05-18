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
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;

/**
 * Encoded k.LAB data resulting from decoding a resource URN in a specified
 * geometry. The interface supports both direct building within an existing
 * artifact or setting of data into the Protobuf-based encoding for remote
 * consumption.
 * <p>
 * A builder is passed to each {@link IResourceEncoder encoder} by the runtime,
 * set appropriately to ensure that no unnecessary storage is wasted.
 * 
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IKlabData {

	interface Builder {

		/**
		 * 
		 * @param name TODO
		 * @return a builder on which the add() functions can be called.
		 */
		Builder startState(String name);

		/**
		 * 
		 * @param doubleValue
		 */
		void add(double doubleValue);

		/**
		 * 
		 * @param floatValue
		 */
		void add(float floatValue);

		/**
		 * 
		 * @param intValue
		 */
		void add(int intValue);

		/**
		 * 
		 * @param longValue
		 */
		void add(long longValue);

		/**
		 * 
		 * @return the builder on which startState() was called.
		 */
		Builder finishState();

		/**
		 * A builder that starts an object. Any further operation will operate on the
		 * object until finishObject() is called.
		 * 
		 * @param name
		 * @return an object builder
		 */
		Builder startObject(String name);

		/**
		 * Finishes the object definition and sets the returned context back to the
		 * original builder.
		 * 
		 * @return the builder on which startObject() was called.
		 */
		Builder finishObject();
		
		/**
		 * 
		 * @param property
		 * @param object
		 * @return the builder itself
		 */
		Builder setProperty(String property, Object object);
		
		/**
		 * 
		 * @param notification
		 * @return the builder itself
		 */
		Builder addNotification(INotification notification);
		
		/**
		 * Build the final data object
		 * @return the finished data
		 */
		IKlabData build();
	}

	/**
	 * 
	 * @return all states built
	 */
	List<IState> getStates();
	
	/**
	 * 
	 * @return all objects built
	 */
	List<IDirectObservation> getObjects();
	
	/**
	 * 
	 * @return all notifications
	 */
	List<INotification> getNotifications();
	
	/**
	 * True if errors happened and no results should be used.
	 * 
	 * @return true if errors
	 */
	boolean hasErrors();
	
}
