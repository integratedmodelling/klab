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
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

// TODO: Auto-generated Javadoc
/**
 * The Class Collections.
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

	/**
     * Pretty-print the passed map as a JSON object.
     *
     * @param object the object
     * @return the string
     */
	public static String printAsJson(Object object) {

		ObjectMapper om = new ObjectMapper();
		om.enable(SerializationFeature.INDENT_OUTPUT); // pretty print
		om.enable(SerializationFeature.WRITE_NULL_MAP_VALUES); // pretty print
		om.enable(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED); // pretty print

//		DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
//		prettyPrinter.indentArraysWith(DefaultPrettyPrinter.Lf2SpacesIndenter.instance);
//
//		String json = objectMapper.writer(prettyPrinter).writeValueAsString(object);

		try {
			return om.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("serialization failed: " + e.getMessage());
		}
	}
}
