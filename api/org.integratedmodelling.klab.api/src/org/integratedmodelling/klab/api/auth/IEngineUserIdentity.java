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

import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.api.engine.IObservationScope;

/**
 * The Interface IEngineUserIdentity. TODO must become a IActorIdentity
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IEngineUserIdentity extends IUserIdentity, IActorIdentity<KlabMessage> {

    /** Constant <code>type</code> */
    Type type = Type.ENGINE_USER;

    /** {@inheritDoc} */
    @Override
    IEngineIdentity getParentIdentity();

    /**
     * True if this engine user is also the one who owns the engine. Shortcut for
     * <code>getParentIdentity(INodeUserIdentity.class).getId().equals(this.getId())</code>.
     * 
     * @return true if owner
     */
    boolean isEngineOwner();

    /**
     * Get the root scope to operate the engine as a user. Also triggers the construction of the
     * user agent with potential k.Actors behavior.
     * 
     * @return
     */
    IObservationScope getScope();

}
