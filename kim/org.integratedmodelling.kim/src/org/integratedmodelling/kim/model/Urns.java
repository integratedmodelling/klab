package org.integratedmodelling.kim.model;

import java.io.File;
import java.io.IOException;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.utils.Escape;

/**
 * This class encodes the rules for creating URNs that describe local resources:
 * literal values, local files and function calls.
 *
 */
public enum Urns {

	INSTANCE;

	final public static String KLAB_URN_PREFIX = "urn:klab:";
	final public static String LOCAL_URN_PREFIX = "urn:klab:local:";

//	final public static String LOCAL_NUMBER_PREFIX = "number:";
//	final public static String LOCAL_BOOLEAN_PREFIX = "boolean:";
//	final public static String LOCAL_TEXT_PREFIX = "text:";
	final public static String LOCAL_FILE_PREFIX = "file:";
//	final public static String LOCAL_FUNCTION_PREFIX = "function:";

//	public String getLiteralUrn(Object literal) {
//
//		if (literal == null) {
//			throw new IllegalArgumentException("null argument to getLiteralUrn()");
//		}
//
//		String valuePart = "";
//		if (literal instanceof Number) {
//			valuePart = LOCAL_NUMBER_PREFIX;
//		} else if (literal instanceof Boolean) {
//			valuePart = LOCAL_BOOLEAN_PREFIX;
//		} else if (literal instanceof String) {
//			valuePart = LOCAL_TEXT_PREFIX;
//		} else {
//			throw new IllegalArgumentException("literal URNs cannot handle a " + literal.getClass().getCanonicalName());
//		}
//		return LOCAL_URN_PREFIX + valuePart + Escape.forURL(literal.toString());
//	}

	public String getFileUrn(File file) {
		if (file == null) {
			throw new IllegalArgumentException("null argument to getFileUrn()");
		}
		try {
			return LOCAL_URN_PREFIX + LOCAL_FILE_PREFIX + Escape.forURL(file.getCanonicalPath().toString());
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public String getFunctionUrn(IServiceCall functionCall) {
		return "";
	}
}
