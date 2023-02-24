/*
R * This file is part of k.LAB.
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
package org.integratedmodelling.klab.api.exceptions;

// TODO: Auto-generated Javadoc
/**
 * An unchecked exception reserved for situations that should never happen in a
 * production environment. To be used in k.LAB code instead of Java's
 * IllegalArgumentException for ease of debugging.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public class KIllegalArgumentException extends KException {

	private static final long serialVersionUID = 461213337593957416L;

	/**
	 * Instantiates a new klab illegal status exception.
	 */
	public KIllegalArgumentException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new klab illegal status exception.
	 *
	 * @param arg0 the arg 0
	 */
	public KIllegalArgumentException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new klab illegal status exception.
	 *
	 * @param e the e
	 */
	public KIllegalArgumentException(Throwable e) {
		super(e);
	}

}
