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

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.extensions.ResourceAdapter;
import org.integratedmodelling.klab.ogc.vector.wfs.WfsEncoder;
import org.integratedmodelling.klab.ogc.vector.wfs.WfsPublisher;
import org.integratedmodelling.klab.ogc.vector.wfs.WfsValidator;

/**
 * The Class WfsAdapter.
 */
@ResourceAdapter(type = "wfs", version = Version.CURRENT, requires = { "serviceUrl", "wfsVersion" }, optional = {
		"namespace" })
public class WfsAdapter implements IResourceAdapter {

	@Override
	public String getName() {
		return "wfs";
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

}
