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
package org.integratedmodelling.ml.adapters;

import java.util.Set;

import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.adapters.IFileResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.extensions.ResourceAdapter;
import org.integratedmodelling.klab.kim.Prototype;

import com.google.common.collect.Sets;

@ResourceAdapter(type = "weka", version = Version.CURRENT)
public class WekaAdapter implements IFileResourceAdapter {

	/**
	 * All recognized primary file extensions.
	 */
	public static Set<String> fileExtensions = Sets.newHashSet("bif", "xdsl", "model");

	/**
	 * All recognized secondary file extensions
	 */
	public static Set<String> secondaryFileExtensions = Sets.newHashSet("arff", "csv");

	@Override
	public String getName() {
		return "weka";
	}

	@Override
	public IResourceValidator getValidator() {
		return new WekaValidator();
	}

	@Override
	public IResourcePublisher getPublisher() {
		return new WekaPublisher();
	}

	@Override
	public IResourceEncoder getEncoder() {
		return new WekaEncoder();
	}

	@Override
	public IResourceImporter getImporter() {
		return new WekaImporter();
	}

	@Override
	public IPrototype getResourceConfiguration() {
		return new Prototype(Dataflows.INSTANCE
				.declare(getClass().getClassLoader()
						.getResource("components/org.integratedmodelling.ml/adapter/weka.kdl"))
				.getActuators().iterator().next(), null);
	}
}
