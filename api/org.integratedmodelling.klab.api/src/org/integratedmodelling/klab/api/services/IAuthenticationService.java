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
package org.integratedmodelling.klab.api.services;

import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.IIdentity;

/**
 * The {@link IAuthenticationService} provides API access to any identities that
 * should be identifiable through a {@link IIdentity#getId() identity
 * identifier} linked to a request. These currently include the network session
 * and any users.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IAuthenticationService {

	/**
	 * Retrieve the passed identity, validating it as the passed class.
	 * 
	 * @param id
	 * @param type
	 * @return the requested identity. Could be null if expired (e.g. a session).
	 */
	<T extends IIdentity> T getIdentity(String id, Class<T> type);

	/**
	 * Perform the authentication required for the certificate and return the
	 * correspondent identity, or null.
	 * 
	 * @param certificate
	 * @return the authenticated identity
	 */
	IIdentity authenticate(ICertificate certificate);

}
