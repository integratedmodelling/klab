package org.integratedmodelling.klab.utils;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtils {

	public static <T> List<T> arrayToList(T[] objects) {
		List<T> ret = new ArrayList<>();
		if (objects != null) {
			for (T obj : objects) {
				ret.add(obj);
			}
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> join(List<T>... resources) {
		List<T> ret = new ArrayList<>();
		for (List<T> list : resources) {
			ret.addAll(list);
		}
		return ret;
	}
}
