package org.integratedmodelling.klab.common;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.utils.Escape;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.StringUtil;

/**
 * This class encodes the rules for creating URNs that describe local resources:
 * literal values, local files and function calls.
 *
 */
public enum Urns {

	INSTANCE;

	final public static String KLAB_URN_PREFIX = "urn:klab:";
	final public static String LOCAL_URN_PREFIX = "urn:klab:local:";
	final public static String VOID_URN_PREFIX = "urn:klab:void:";
	final public static String LOCAL_FILE_PREFIX = "file:";

	/**
	 * Create a unique URN that won't be accepted in any production resource
	 * catalog. For testing.
	 * 
	 * @return a new unprivileged, unique URN
	 */
	public String createDisposableUrn() {
		return VOID_URN_PREFIX + NameGenerator.shortUUID();
	}

	public String getFileUrn(File file) {

		if (file == null) {
			throw new IllegalArgumentException("null argument to getFileUrn()");
		}

		/*
		 * TODO look inside a project and ensure it is in one; use project ID as
		 * namespace. If not in a project, which should only happen in testing, encode
		 * as "absolute" namespace.
		 */

		try {
			return LOCAL_URN_PREFIX + LOCAL_FILE_PREFIX + Escape.forURL(file.getCanonicalPath().toString());
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public String getFunctionUrn(IServiceCall functionCall) {
		return "";
	}

	public boolean isLocal(String urn) {
		return urn.startsWith(LOCAL_URN_PREFIX) || urn.startsWith("local:") || urn.startsWith(LOCAL_FILE_PREFIX);
	}

	public boolean isUniversal(String urn) {
		return urn.startsWith("klab");
	}

	public String getLocalUrn(String resourceId, IProject project, String owner) {
		return "local:" + owner + ":" + project.getName() + ":" + resourceId;
	}

	/**
	 * Create a new local URN with the passed project instead of the original.
	 * 
	 * @param originalUrn
	 * @param name
	 * @return
	 */
	public String changeLocalProject(String urn, String projectName) {

		if (!isLocal(urn)) {
			throw new IllegalArgumentException("cannot change project name in non-local URN " + urn);
		}
		int fieldIndex = urn.startsWith(LOCAL_URN_PREFIX) ? 4 : 2;
		String ret = "";
		int i = 0;
		for (String field : urn.split(":")) {
			ret += (ret.isEmpty() ? "" : ":") + (i == fieldIndex ? projectName : field);
			i++;
		}
		return ret;
	}

	public Map<String, String> parseParameters(String uu) {
		Map<String, String> ret = new HashMap<>();
		for (String s : uu.split("&")) {
			if (s.contains("=")) {
				String[] kv = s.split("=");
				ret.put(kv[0], kv[1]);
			} else {
				ret.put(Urn.SINGLE_PARAMETER_KEY, s);
			}
		}
		return ret;
	}
	
	/**
	 * Split off the fragment and return the parsed parameter map along with the
	 * clean URN.
	 * 
	 * @param urn
	 * @return
	 */
	public Pair<String, Map<String, String>> resolveParameters(String urn) {
		Map<String, String> parameters = new HashMap<>();
		String clean = urn;
		if (urn.contains("#")) {
			String[] uu = urn.split("#");
			clean = uu[0];
			for (String s : uu[1].split("&")) {
				if (s.contains("=")) {
					String[] kv = s.split("=");
					parameters.put(kv[0], kv[1]);
				} else {
					parameters.put(Urn.SINGLE_PARAMETER_KEY, s);
				}
			}
		}
		return new Pair<>(clean, parameters);
	}

	public boolean isUrn(String urn) {
		return StringUtil.countMatches(urn, ":") > 2;
	}

	public String applyParameters(String urn, Map<String, String> urnParameters) {
		String ret = urn;
		if (urnParameters != null && !urnParameters.isEmpty()) {
			boolean first = true;
			for (Entry<String, String> entry : urnParameters.entrySet()) {
				ret += (first ? "#" : "&") + entry.getKey() + "=" + entry.getValue();
			}
		}
		return ret;
	}

}
