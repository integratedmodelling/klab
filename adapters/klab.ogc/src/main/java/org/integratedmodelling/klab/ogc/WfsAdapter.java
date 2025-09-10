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
package org.integratedmodelling.klab.ogc;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.geotools.data.wfs.WFSDataStore;
import org.geotools.data.wfs.WFSDataStoreFactory;
import org.geotools.referencing.ReferencingFactoryFinder;
import org.geotools.util.factory.Hints;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCalculator;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.extensions.ResourceAdapter;
import org.integratedmodelling.klab.kim.Prototype;
import org.integratedmodelling.klab.ogc.vector.wfs.WfsEncoder;
import org.integratedmodelling.klab.ogc.vector.wfs.WfsImporter;
import org.integratedmodelling.klab.ogc.vector.wfs.WfsPublisher;
import org.integratedmodelling.klab.ogc.vector.wfs.WfsValidator;

import klab.commons.customProperties.CustomPropertyKey;
import klab.commons.customProperties.auth.AuthType;
import klab.commons.customProperties.auth.AuthenticatedUrlClient;
import klab.commons.customProperties.auth.BasicAuth;

/**
 * The Class WfsAdapter.
 */
@ResourceAdapter(
        type = "wfs",
        version = Version.CURRENT,
        requires = { "serviceUrl", "wfsIdentifier" },
        optional = {
                // TODO check out
                // http://docs.geotools.org/latest/userguide/library/data/wfs-ng.html
                // TODO find a way to provide documentation for all these options
                "wfsVersion", "bufferSize", "serverType", "timeoutSeconds", "filter", "computeShape", "sanitize" },
        canCreateEmpty = true,
        handlesFiles = false
        )
public class WfsAdapter implements IResourceAdapter {

    static Map<String, WFSDataStore> dataStores = new HashMap<>();

    public static final String ID = "wfs";
    
    public static final int TIMEOUT = 100000;
    public static final int BUFFER_SIZE = 512;

    /*
     * retry failing datastores every 5 minutes
     */
    private static final long RETRY_INTERVAL_MS = 5 * 60 * 1000;

    private static Map<String, AtomicLong> lastTry = Collections.synchronizedMap(new HashMap<>());

    @Override
    public String getName() {
        return ID;
    }

    @Override
    public IResourceValidator getValidator() {
        return new WfsValidator();
    }

    @Override
    public IResourcePublisher getPublisher() {
        return new WfsPublisher();
    }

    @Override
    public IResourceEncoder getEncoder() {
        return new WfsEncoder();
    }

    public static WFSDataStore getDatastore(String serverUrl, Version version) {

        WFSDataStore ret = dataStores.get(serverUrl);
        
        if (ret == null) {

//            if (lastTry.get(serverUrl) != null
//                    && (System.currentTimeMillis() - lastTry.get(serverUrl).get()) < RETRY_INTERVAL_MS) {
//                return null;
//            }
//
//            if (lastTry.get(serverUrl) == null) {
//                lastTry.put(serverUrl, new AtomicLong());
//            }
//            lastTry.get(serverUrl).set(System.currentTimeMillis());

            Integer wfsTimeout = TIMEOUT;
            Integer wfsBufsize = BUFFER_SIZE;

            final Hints hints = new Hints(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE);
            ReferencingFactoryFinder.getCRSAuthorityFactory("EPSG", hints);

            String getCapabilities = serverUrl + "?SERVICE=wfs&REQUEST=getCapabilities&version=" + version;
            WFSDataStoreFactory dsf = new WFSDataStoreFactory();
            
            Map<String, Serializable> connectionParameters = new HashMap<>();
            
            IUserIdentity userData = Authentication.INSTANCE.getAuthenticatedIdentity(IUserIdentity.class);
            AuthenticatedUrlClient.prepareWFSAuthenticationParameters(serverUrl, connectionParameters ,userData.getGroups(), CustomPropertyKey.GEOSERVER_KEYS);
            
            connectionParameters.put(WFSDataStoreFactory.URL.key, getCapabilities);
            connectionParameters.put(WFSDataStoreFactory.TIMEOUT.key, wfsTimeout);
            connectionParameters.put(WFSDataStoreFactory.BUFFER_SIZE.key, wfsBufsize);
            
            /*
             * TODO all other parameters
             */
            try {
                ret = dsf.createDataStore(connectionParameters);
            } catch (Throwable t) {
                return null;
            }
            
            dataStores.put(serverUrl, ret);
        }

        return ret;

    }

    @Override
    public IResourceImporter getImporter() {
        return new WfsImporter();
    }

    @Override
    public Collection<IPrototype> getResourceConfiguration() {
        return Collections.singleton(new Prototype(
                Dataflows.INSTANCE.declare(getClass().getClassLoader().getResource("ogc/prototypes/wfs.kdl"))
                        .getActuators().iterator().next(),
                null));
    }

	@Override
	public IResourceCalculator getCalculator(IResource resource) {
		// TODO Auto-generated method stub
		return null;
	}
}
