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
package org.integratedmodelling.klab.api.auth;

import java.util.Date;
import java.util.Set;

/**
 * The Interface IEngineUserIdentity.
 */
public interface IEngineUserIdentity extends IUserIdentity {

    Type type = Type.ENGINE_USER;
    
    @Override
    IEngineIdentity getParentIdentity();
    
    /**
     * Never null, may be ANONYMOUS_USER_ID when isAnonymous() returns true.
     * 
     * @return the username
     */
    String getUsername();

    /**
     * List roles for this user. So far, only role of relevance is ADMIN - roles
     * are just strings, stored at server side and defined at authenticate().
     * Roles do not imply assets but are checked for privileged operations.
     * 
     * @return a set of role names
     */
    Set<String> getRoles();

    /**
     * List groups the user belongs to. At client side, only non-empty after
     * authenticate() is successful.
     * 
     * @return a set of group names
     */
    Set<String> getGroups();

    /**
     * True after the empty constructor is used. In this situation, isOnline()
     * is false, the assets are empty (not null) and the username is
     * "anonymous".
     * 
     * @return whether the user is anonymous and unauthenticated.
     */
    boolean isAnonymous();

    /**
     * Primary server URL, harvested from the group set at the server side and
     * stored in the certificate. One of three bits of data that the certificate
     * contains along with username and email (plus optionally basic anagraphic
     * data for pretty-printing at client side). This one can be null (in
     * anonymous and unprivileged users). If null, everything works but the
     * whole system is essentially a self-contained sandbox.
     * 
     * @return the URL of the primary server for the user.
     */
    String getServerURL();

    /**
     * Never null - users cannot be created at server side without an email
     * address.
     * 
     * @return the user's email address.
     */
    String getEmailAddress();

    /**
     * May be null if user has been created in non-standard ways.
     * 
     * @return user stated first name.
     */
    String getFirstName();

    /**
     * May be null if user has been created in non-standard ways.
     * 
     * @return user stated last name.
     */
    String getLastName();

    /**
     * OK, Anglo-saxons, have it your way. At least it can be null.
     * 
     * @return user middle initials, if any.
     */
    String getInitials();

    /**
     * Input by user at registration, possibly null.
     * 
     * @return user affiliation, if any.
     */
    String getAffiliation();

    /**
     * Return whatever further comments were entered by user at registration.
     * 
     * @return user-stated comments.
     */
    String getComment();

    /**
     * Date of last login for user. Should be kept up to date at server side
     * when authorizing, and correspond to authentication time at client side.
     * 
     * @return date of last login.
     */
    Date getLastLogin();

}
