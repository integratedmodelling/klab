package org.integratedmodelling.klab.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;

public class TemplateUtils extends TemplateUtil {

	/**
	 * Return all the substituted templates after substituting the passed variables.
	 * The substitution for each variable can be null, a single POD, a {@link Range}
	 * or a collection of objects.
	 * 
	 * @param template
	 * @param vars
	 * @return
	 */
	public static List<String> expandMatches(String template, Map<String, Object> vars) {

		/*
		 * extract the variables
		 */
		List<String> vs = getTemplateVariables(template);

		if (vs.size() == 0) {
			return Collections.singletonList(template);
		}

		/*
		 * set the vars not in the map to null and substitute any single value in it
		 * with singleton lists
		 */
		List<String> variables = new ArrayList<>();
		List<Set<Object>> sets = new ArrayList<>();

		for (String var : vs) {
			if (vars.containsKey(var)) {
				variables.add(var);
				sets.add(expandSet(vars.get(var)));
			}
		}

		if (variables.size() == 0) {
			return Collections.singletonList(template);
		}

		List<String> ret = new ArrayList<>();

		/*
		 * take the cartesian product of each variable that is represented in the vars
		 * and substitute one by one
		 */
		for (List<Object> permutation : Sets.cartesianProduct(sets)) {
			String tret = template;
			int i = 0;
			for (String var : variables) {
				Object o = permutation.get(i++);
				tret = tret.replaceAll("\\{" + var + "\\}", o.toString());
			}
			ret.add(tret);
		}

		return ret;
	}

	/**
	 * Like {@link #expandMatches(String, Map)} but also returns the specific
	 * matches for each expanded template as a map.
	 * 
	 * @param template
	 * @param vars
	 * @return
	 */
	public static List<Pair<String, Map<String, Object>>> getExpansion(String template, Map<String, Object> vars) {

		/*
		 * extract the variables
		 */
		List<String> vs = getTemplateVariables(template);

		if (vs.size() == 0) {
			return Collections.singletonList(new Pair<>(template, new HashMap<>()));
		}

		/*
		 * set the vars not in the map to null and substitute any single value in it
		 * with singleton lists
		 */
		List<String> variables = new ArrayList<>();
		List<Set<Object>> sets = new ArrayList<>();

		for (String var : vs) {
			if (vars.containsKey(var)) {
				variables.add(var);
				sets.add(expandSet(vars.get(var)));
			}
		}

		if (variables.size() == 0) {
			return Collections.singletonList(new Pair<>(template, new HashMap<>()));
		}

		 List<Pair<String, Map<String, Object>>> ret = new ArrayList<>();

		/*
		 * take the cartesian product of each variable that is represented in the vars
		 * and substitute one by one
		 */
		for (List<Object> permutation : Sets.cartesianProduct(sets)) {
			Map<String, Object> map = new HashMap<>();
			String tret = template;
			int i = 0;
			for (String var : variables) {
				Object o = permutation.get(i++);
				tret = tret.replaceAll("\\{" + var + "\\}", o.toString());
				map.put(var,  o);
			}
			ret.add(new Pair<>(tret, map));
		}

		return ret;
	}

	private static Set<Object> expandSet(Object object) {

		Set<Object> ret = new LinkedHashSet<>();
		if (object == null) {
			ret.add("");
		} else if (object instanceof Range) {
			int bottom = (int) ((Range) object).getLowerBound();
			int upper = (int) ((Range) object).getUpperBound();
			for (int n = bottom; n <= upper; n++) {
				ret.add(n);
			}
		} else if (object instanceof Collection) {
			for (Object o : ((Collection<?>) object)) {
				ret.add(o);
			}
		} else {

			String tt = object.toString();
			if (tt.contains(",")) {
				String[] ttt = tt.split(",");
				for (String tttt : ttt) {
					ret.add(tttt.trim());
				}
			} else {
				ret.add(tt);
			}

		}
		return ret;
	}

	public static void main(String[] argv) {

		String url = "https://disc2.gesdisc.eosdis.nasa.gov:443/opendap/TRMM_L3/TRMM_3B42_Daily.7/{year}/{month}/3B42_Daily.{year}{month}{day}.7.nc4";

		Map<String, Object> vars = new HashMap<>();

		vars.put("year", Range.create(1998, 2010));
		vars.put("month", Range.create(2, 3));
		vars.put("day", "monday,tuesday,happy_days");

		for (String uuh : expandMatches(url, vars)) {
			System.out.println(uuh);
		}

	}

}
