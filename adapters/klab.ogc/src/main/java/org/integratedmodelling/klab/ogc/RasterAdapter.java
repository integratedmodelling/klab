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
import org.integratedmodelling.klab.raster.files.RasterEncoder;
import org.integratedmodelling.klab.raster.files.RasterPublisher;
import org.integratedmodelling.klab.raster.files.RasterValidator;

/**
 * The Class RasterAdapter.
 */
@ResourceAdapter(type = "raster", version = Version.CURRENT, requires = { "fileUrl" }, optional = { "band",
		"interpolation", "nodata" })
public class RasterAdapter implements IResourceAdapter {

	/**
	 * All recognized primary file extensions.
	 */
	public static String[] fileExtensions = { "tif", "tiff" };

	/**
	 * All recognized secondary file extensions
	 */
	public static String[] secondaryFileExtensions = { "tfw", "prj", "tif.ovr", "tif.aux.xml", "txt", "pdf" };

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

}
