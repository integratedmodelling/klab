package org.integratedmodelling.klab.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateUtils {

	/**
	 * Find all {xxx} variables in string, return the list of template variables.
	 * 
	 * @param template
	 * @return
	 */
	public static List<String> getTemplateVariables(String template) {
		List<String> ret = new ArrayList<>();
		final Pattern tvar = Pattern.compile("\\{[a-z]+\\}");
		final Matcher m = tvar.matcher(template);
		while (m.find()) {
			String var = m.group().substring(1, m.group().length() - 1);
			if (!ret.contains(var)) {
				ret.add(var);
			}
		}
		return ret;
	}

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
		List<String> ret = new ArrayList<>();

		/*
		 * extract the variables
		 */

		/*
		 * set the vars not in the map to null and substitute any single value in it
		 * with singleton lists
		 */

		/*
		 * take the cartesian product of each variable that is represented in the vars
		 * and substitute one by one
		 */

		return ret;
	}

	public static void main(String[] args) {
		for (String var : getTemplateVariables(
				"https://disc2.gesdisc.eosdis.nasa.gov:443/opendap/TRMM_L3/TRMM_3B42_Daily.7/{year}/{month}/3B42_Daily.{year}{month}{day}.7.nc4")) {
			System.out.println("  " + var);
		}
	}

}
