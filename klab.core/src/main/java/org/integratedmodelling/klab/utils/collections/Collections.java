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
package org.integratedmodelling.klab.utils.collections;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Collections.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public class Collections {

	/**
	 * Pass any number of lists and return one with all the elements.
	 *
	 * @param <T> the generic type
	 * @param lists the lists
	 * @return a single list with the content of all those passed
	 */
	@SafeVarargs
	public static <T> List<T> join(List<T>... lists) {
		List<T> ret = new ArrayList<>();
		if (lists != null) {
			for (List<T> list : lists) {
				ret.addAll(list);
			}
		}
		return ret;
	}

}
