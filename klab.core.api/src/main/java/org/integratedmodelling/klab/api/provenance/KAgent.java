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
package org.integratedmodelling.klab.api.provenance;

import org.integratedmodelling.klab.api.identities.KIdentity;

/**
 * An agent in k.LAB is anything that can make observations. All such agents are a k.LAB
 * {@link org.integratedmodelling.klab.api.KIdentity.IIdentity}. The agent corresponding to a
 * {@link org.integratedmodelling.klab.api.auth.IEngineIdentity} tags choices made by the AI in the
 * system. Observations asserted and made by users using the API will have agents tagged with
 * {@link org.integratedmodelling.klab.api.KUserIdentity.IUserIdentity}.
 *
 * FIXME agents shouldn't be nodes probably, as they can exist beyond a single provenance graph.
 *
 * @author Ferd
 * @version $Id: $Id
 */
public interface KAgent extends KProvenance.Node, KIdentity {

}
