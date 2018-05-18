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
package org.integratedmodelling.klab.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

// TODO: Auto-generated Javadoc
/**
 * The Class JsonUtils.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public class JsonUtils {

	static ObjectMapper defaultMapper = new ObjectMapper();

	/**
	 * Default conversion for a map object.
	 *
	 * @param node
	 *            the node
	 * @return the map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> asMap(JsonNode node) {
		return defaultMapper.convertValue(node, Map.class);
	}

	/**
	 * Default conversion, use within custom deserializers to "normally" deserialize
	 * an object.
	 *
	 * @param <T>
	 *            the generic type
	 * @param node
	 *            the node
	 * @param cls
	 *            the cls
	 * @return the t
	 */
	public static <T> T as(JsonNode node, Class<T> cls) {
		return defaultMapper.convertValue(node, cls);
	}

	/**
	 * Convert node to list of type T.
	 *
	 * @param <T>
	 *            the generic type
	 * @param node
	 *            the node
	 * @param cls
	 *            the cls
	 * @return the list
	 */
	public static <T> List<T> asList(JsonNode node, Class<T> cls) {
		return defaultMapper.convertValue(node, new TypeReference<List<T>>() {
		});
	}

	public static <T> List<T> asList(JsonNode node, Class<T> cls, ObjectMapper mapper) {
		return mapper.convertValue(node, new TypeReference<List<T>>() {
		});
	}

	/**
	 * Convert node to list of type T.
	 *
	 * @param <T>
	 *            the generic type
	 * @param node
	 *            the node
	 * @param cls
	 *            the cls
	 * @return the sets the
	 */
	public static <T> Set<T> asSet(JsonNode node, Class<T> cls) {
		return defaultMapper.convertValue(node, new TypeReference<Set<T>>() {
		});
	}

	/**
	 * Pretty-print the passed map as a JSON object.
	 *
	 * @param object
	 *            the object
	 * @return the string
	 */
	public static String printAsJson(Object object) {

		ObjectMapper om = new ObjectMapper();
		om.enable(SerializationFeature.INDENT_OUTPUT); // pretty print
		om.enable(SerializationFeature.WRITE_NULL_MAP_VALUES); // pretty print
		om.enable(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED); // pretty print

		// DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
		// prettyPrinter.indentArraysWith(DefaultPrettyPrinter.Lf2SpacesIndenter.instance);
		//
		// String json = objectMapper.writer(prettyPrinter).writeValueAsString(object);

		try {
			return om.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("serialization failed: " + e.getMessage());
		}
	}

}
