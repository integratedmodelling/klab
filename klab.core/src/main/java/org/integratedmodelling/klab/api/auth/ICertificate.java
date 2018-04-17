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
package org.integratedmodelling.klab.api.auth;

import org.integratedmodelling.klab.api.knowledge.IWorldview;

/**
 * A certificate defines a 'root' identity: a {@link IPartnerIdentity},
 * {@link INodeIdentity} or {@link IUserIdentity}.
 * <p>
 * If no certificate file is found, implementations can create a default
 * certificate with anonymous identity, linked to a preferred worldview and
 * enabling basic, local operations with no access to the network. The same
 * certificate may be used for testing.
 * <p>
 * When a certificate begins its lifetime, it should be already authenticated
 * and its validity should have been checked with {@link #isValid()} immediately
 * after creation.
 * <p>
 *
 * @author ferdinando villa
 * @version $Id: $Id
 */
public interface ICertificate {

    public static final String DEFAULT_CERTIFICATE_FILENAME = "klab.cert";

    /*
     * possible values for the certificate type, determining the type of
     * authentication.
     */
    public static final String CERTIFICATE_TYPE_NODE = "NODE";
    public static final String CERTIFICATE_TYPE_USER = "USER";
    public static final String CERTIFICATE_TYPE_PARTNER = "PARTNER";

    /**
     * Create the worldview workspace for this identity and return it (unloaded and
     * not initialized).
     *
     * @return a {@link org.integratedmodelling.klab.api.knowledge.IWorldview}
     *         object.
     */
    IWorldview getWorldview();

    /**
     * A certificate represents an identity - a partner, a partner node or a k.LAB user. The
     * identity returned will reflect the results of authentication: it may have no network
     * parent if the user is offline, for example. It should normally descend from a partner,
     * node, and network session; the anonymous certificate will return a lonely anonymous
     * user.
     * 
     * @return the {@link org.integratedmodelling.klab.api.auth.IIdentity} that owns this 
     * certificate. Never null.
     */
    IIdentity getIdentity();

    /**
     * Validity may depend on expiration date and possibly upstream conditions after
     * authentication, such as having had a certificate invalidated by an
     * administrator.
     *
     * If this returns true, the certificate exists, is readable and properly encrypted, and is
     * current.
     * 
     * If this returns false, {@link #getInvalidityCause} will contain the reason why.
     * 
     * @return true if everything is OK.
     */
    boolean isValid();

    /**
     * Returns why {@link #isValid()} returned false. Undefined otherwise.
     * 
     * @return a description of the cause for invalidity
     */
    String getInvalidityCause();
}
