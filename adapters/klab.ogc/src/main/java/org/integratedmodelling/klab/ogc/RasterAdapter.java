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

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResourceCalculator;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.extensions.ResourceAdapter;
import org.integratedmodelling.klab.kim.Prototype;
import org.integratedmodelling.klab.raster.files.RasterEncoder;
import org.integratedmodelling.klab.raster.files.RasterImporter;
import org.integratedmodelling.klab.raster.files.RasterPublisher;
import org.integratedmodelling.klab.raster.files.RasterValidator;

import com.google.common.collect.Sets;

/**
 * The Class RasterAdapter.
 * 
 * TODO evaluate: simple transformations
 * 
 * clipRange (clip to it) dataRange (not in range -> NaN) legalRange (not in
 * range -> exception) transform (expr for each true datapoint) valueFilter
 * (expr tied to specific values 10 -> 1 or 10 -> [x * 2])
 * 
 * Store range and percent nodata when validating?
 */
@ResourceAdapter(type = "raster", version = Version.CURRENT, requires = { "fileUrl" }, optional = { "band",
		"interpolation", "nodata", "bandmixer" }, canCreateEmpty = false, handlesFiles = true)
public class RasterAdapter implements IResourceAdapter {

	/**
	 * All recognized primary file extensions.
	 */
	public static Set<String> fileExtensions = Sets.newHashSet("tif", "tiff");

	/**
	 * All recognized secondary file extensions
	 */
	public static Set<String> secondaryFileExtensions = Sets.newHashSet("tfw", "prj", "tif.ovr", "tif.aux.xml", "txt",
			"pdf");

	/**
	 * All the permitted band mixing operations.
	 */
	public static Set<String> bandMixingOperations = Sets.newHashSet("max_value", "min_value", "avg_value", "max_band", "min_band");

	/**
	 * Interpolation type for metadata
	 */
	public static final String INTERPOLATION_TYPE_FIELD = "interpolation";

	/**
	 * Possible values of interpolation type (JAI classes)
	 */
	public static final String[] INTERPOLATION_TYPE_VALUES = { "bilinear", "nearest", "bicubic", "bicubic2" };

	@Override
	public String getName() {
		return "raster";
	}

	@Override
	public IResourceValidator getValidator() {
		return new RasterValidator();
	}

	@Override
	public IResourcePublisher getPublisher() {
		return new RasterPublisher();
	}

	@Override
	public IResourceEncoder getEncoder() {
		return new RasterEncoder();
	}

	@Override
	public IResourceImporter getImporter() {
		return new RasterImporter();
	}

	@Override
	public Collection<IPrototype> getResourceConfiguration() {
		return Collections.singleton(new Prototype(
				Dataflows.INSTANCE.declare(getClass().getClassLoader().getResource("ogc/prototypes/raster.kdl"))
						.getActuators().iterator().next(),
				null));
	}

	@Override
	public IResourceCalculator getCalculator(IResource resource) {
		// TODO Auto-generated method stub
		return null;
	}

}
