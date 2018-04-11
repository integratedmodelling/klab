package org.integratedmodelling.klab.utils.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Collections {

	/**
	 * Pass any number of lists and return one with all the elements.
	 * 
	 * @param lists
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
	 * @param map
	 * @return
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
