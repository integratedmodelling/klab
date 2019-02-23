package org.integratedmodelling.kim.utils;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.model.IAnnotation;

public class KimUtils {

	/**
	 * Find a specific annotation in a list.
	 * 
	 * @param annotations
	 * @param name
	 * @return
	 */
	public static IAnnotation findAnnotation(List<IAnnotation> annotations, String name) {
		for (IAnnotation annotation : annotations) {
			if (annotation.getName().equals(name)) {
				return annotation;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param annotations
	 * @param name
	 * @return
	 */
	public static List<IAnnotation> findAnnotations(List<IAnnotation> annotations, String name) {
		List<IAnnotation> ret = new ArrayList<>();
		for (IAnnotation annotation : annotations) {
			if (annotation.getName().equals(name)) {
				ret.add(annotation);
			}
		}
		return ret;
	}

	
}
