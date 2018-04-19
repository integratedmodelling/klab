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
package org.integratedmodelling.klab.api.services;

import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.data.IStorageProvider;
import org.integratedmodelling.klab.api.runtime.IRuntimeProvider;
import org.integratedmodelling.klab.api.runtime.monitoring.IMessageBus;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * The k.LAB runtime.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IRuntimeService {

    /**
     * Storage for states is provided by a component found in the classpath. If more storage
     * components are available, configuration must have been defined to choose it. This allows
     *
     * @return the storage provider.
     * @throws KlabException if no storage provider is installed or there is more than one
     *         without appropriate configuration.
     */
    IStorageProvider getStorageProvider();

    /**
     * Get the configured runtime provider, in charge of running dataflows resulting from resolution
     * of semantic assets. If no configuration is given, get the default provider.
     * <p>
     *
     * @return the storage provider. Never null.
     * @throws KlabException if no storage provider is installed or there is more than one
     *         without appropriate configuration.
     */
    IRuntimeProvider getRuntimeProvider();

    /**
     * Return the JSON source code of a map containing all the JSON level 4 schemata for REST beans
     * indexed by Java class name. These should be harvested and built at runtime from the package
     * containing all resource beans. Called by JS code to validate resources before use.
     * <p>
     * TODO currently not working properly due to limitations in the schema extractor used so far.
     * <p>
     * 
     * @return the JSON schema source code
     */
    String getResourceSchema();

    /**
     * Get the root monitor that owns every computation in this runtime. This may be a node monitor
     * in nodes, an engine monitor in engines, a user monitor in an instance without a running engine.
     * The user may be anonymous. See {@link IIdentity} for details on the identity.
     * 
     * @return the root monitor. Never null.
     */
    IMonitor getRootMonitor();

    /**
     * Get the global message bus. Implementation may set this to be a true client/server communication
     * hub for RPC, or to a simpler message queue to use within an application. Using the {@link IMonitor}
     * and the message bus for all communication is a guarantee that an engine will be compatible with
     * remote usage.
     * <p>
     * Notifications from {@link ILoggingService} may also appear on the bus according to the configured
     * logging level.
     * <p>
     * @return the message bus. Should not return null.
     */
    IMessageBus getMessageBus();

}
