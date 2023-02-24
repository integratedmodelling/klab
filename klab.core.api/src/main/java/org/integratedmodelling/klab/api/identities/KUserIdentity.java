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
package org.integratedmodelling.klab.api.identities;

import java.util.Date;
import java.util.Set;

/**
 * The Interface IUserIdentity. Represents the user that runs a k.LAB session.
 * The user can be given a behavior which will intercept any actor messages
 * directed to it or not understood by other actors.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public abstract interface KUserIdentity extends KIdentity {

	/**
	 * Never null, may be ANONYMOUS_USER_ID when isAnonymous() returns true.
	 *
	 * @return the username
	 */
	String getUsername();

	/**
	 * List groups the user belongs to. Empty in anonymous users.
	 *
	 * @return a set of group names
	 */
	Set<KGroup> getGroups();

	/**
	 * Anonymous users cannot access the network and get a default worldview.
	 * Intended for system exploration without a certificate.
	 * 
	 * @return true if anonymous
	 */
	boolean isAnonymous();

	/**
	 * A user who has successfully authenticated and possesses a network session
	 * with at least one online node is online. Anonymous users are always offline.
	 * This may return false and still allow offline operations when users could not
	 * be authenticated because of unreachable network. If the users cannot be
	 * authenticated because of a 403 response, no user identity can be created.
	 * 
	 * @return true if online
	 */
	boolean isOnline();

	/**
	 * Primary server URL, harvested from the group set at the server side and
	 * stored in the certificate. One of three bits of data that the certificate
	 * contains along with username and email (plus optionally basic anagraphic data
	 * for pretty-printing at client side). This one can be null (in anonymous and
	 * unprivileged users). If null, everything works but the whole system is
	 * essentially a self-contained sandbox.
	 *
	 * @return the URL of the primary server for the user.
	 */
	String getServerURL();

	/**
	 * Never empty unless anonymous: true users cannot be created at server side
	 * without an email address.
	 *
	 * @return the user's email address. Not null.
	 */
	String getEmailAddress();

	/**
	 * May be empty if user has been created in non-standard ways.
	 *
	 * @return user stated first name. Not null.
	 */
	String getFirstName();

	/**
	 * May be empty if user has been created in non-standard ways.
	 *
	 * @return user stated last name. Not null.
	 */
	String getLastName();

	/**
	 * OK, Anglo-saxons, have it your way. At least it can be empty.
	 *
	 * @return user middle initials, if any. Not null.
	 */
	String getInitials();

	/**
	 * Input by user at registration, possibly empty.
	 *
	 * @return user affiliation, if any. Not null.
	 */
	String getAffiliation();

	/**
	 * Return whatever further comments were entered by user at registration.
	 * Possibly empty.
	 *
	 * @return user-stated comments. Not null.
	 */
	String getComment();

	/**
	 * Date of last login for user. Should be kept up to date at server side when
	 * authorizing, and correspond to authentication time at client side. For
	 * anonymous users this is the date of current login.
	 *
	 * @return date of last login. Not null.
	 */
	Date getLastLogin();
}
