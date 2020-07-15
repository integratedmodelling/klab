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
package org.integratedmodelling.klab;

import java.io.Serializable;
import java.util.StringTokenizer;

// TODO: Auto-generated Javadoc
/**
 * The version class for the whole system. Holds the current official release
 * version.
 *
 * @author Ferd
 * @version $Id: $Id
 */
public class Version implements Comparable<Version>, Serializable {

	private static final long serialVersionUID = -3054349171116917643L;

	/**
	 * Main version number for the whole k.LAB software stack, which is expected to
	 * have synchronized release numbers. Change this whenever a new version is
	 * released.
	 */
	public static final String CURRENT = "0.10.0";

	/**
	 * Modifier for release (e.g. alpha, beta, RC1 etc). Should be synchronized by
	 * CI system or maven goal.
	 */
	public static final String MODIFIER = "";

	/*
	 * next fields should be added by a script before build and not committed.
	 * *****************************************************************************
	 * DO NOT CHANGE THE CONSTANT VALUES - the script relies on them to substitute
	 * proper values.
	 * *****************************************************************************
	 */

	/** The Constant VERSION_BRANCH. */
	public static final String VERSION_BRANCH = "VERSION_BRANCH";

	/** The Constant VERSION_COMMIT. */
	public static final String VERSION_COMMIT = "VERSION_COMMIT";

	/** The Constant VERSION_BUILD. */
	public static final String VERSION_BUILD = "VERSION_BUILD";

	/** The Constant VERSION_DATE. */
	public static final String VERSION_DATE = "VERSION_DATE";

	/**
	 * Version identifier parts separator.
	 */
	public static final char SEPARATOR = '.';

	private static Version currentVersion;

	/**
	 * Parses given string as version identifier. All missing parts will be
	 * initialized to 0 or empty string. Parsing starts from left side of the
	 * string.
	 *
	 * @param str version identifier as string
	 * @return version identifier object
	 */
	public static Version create(final String str) {
		Version result = new Version();
		result.parseString(str);
		return result;
	}

	private transient int major;
	private transient int minor;
	private transient int build;
	private transient String name;
	private transient String asString;

	/**
	 * The default version parses the current version string, so it can be used for
	 * comparison with others.
	 */
	public Version() {
		parseString(CURRENT);
	}

	/**
	 * Initialize from a version string.
	 *
	 * @param version the version
	 */
	public Version(String version) {
		parseString(version);
	}

	private void parseString(final String str) {

		major = 0;
		minor = 0;
		build = 0;
		name = "";
		StringTokenizer st = new StringTokenizer(str, "" + SEPARATOR, false);
		// major segment
		if (!st.hasMoreTokens()) {
			return;
		}
		String token = st.nextToken();
		try {
			major = Integer.parseInt(token, 10);
		} catch (NumberFormatException nfe) {
			name = token;
			while (st.hasMoreTokens()) {
				name += st.nextToken();
			}
			return;
		}
		// minor segment
		if (!st.hasMoreTokens()) {
			return;
		}
		token = st.nextToken();
		try {
			minor = Integer.parseInt(token, 10);
		} catch (NumberFormatException nfe) {
			name = token;
			while (st.hasMoreTokens()) {
				name += st.nextToken();
			}
			return;
		}
		// build segment
		if (!st.hasMoreTokens()) {
			return;
		}
		token = st.nextToken();
		try {
			build = Integer.parseInt(token, 10);
		} catch (NumberFormatException nfe) {
			name = token;
			while (st.hasMoreTokens()) {
				name += st.nextToken();
			}
			return;
		}
		// name segment
		if (st.hasMoreTokens()) {
			name = st.nextToken();
			while (st.hasMoreTokens()) {
				name += st.nextToken();
			}
		}
	}

	/**
	 * Creates version identifier object from given parts. No validation performed
	 * during object instantiation, all values become parts of version identifier as
	 * they are.
	 *
	 * @param aMajor major version number
	 * @param aMinor minor version number
	 * @param aBuild build number
	 * @param aName  build name, <code>null</code> value becomes empty string
	 */
	public Version(final int aMajor, final int aMinor, final int aBuild, final String aName) {
		major = aMajor;
		minor = aMinor;
		build = aBuild;
		name = (aName == null) ? "" : aName;
	}

	/**
	 * Gets the builds the.
	 *
	 * @return build number
	 */
	public int getBuild() {
		return build;
	}

	/**
	 * 
	 * @param build
	 * @return
	 */
	public Version withBuild(int build) {
		this.build = build;
		return this;
	}

	public Version withMajor(int major) {
		this.major = major;
		return this;
	}

	public Version withMinor(int minor) {
		this.minor = minor;
		return this;
	}

	/**
	 * Gets the major.
	 *
	 * @return major version number
	 */
	public int getMajor() {
		return major;
	}

	/**
	 * Gets the minor.
	 *
	 * @return minor version number
	 */
	public int getMinor() {
		return minor;
	}

	/**
	 * Gets the name.
	 *
	 * @return build name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Compares two version identifiers to see if this one is greater than or equal
	 * to the argument.
	 * <p>
	 * A version identifier is considered to be greater than or equal if its major
	 * component is greater than the argument major component, or the major
	 * components are equal and its minor component is greater than the argument
	 * minor component, or the major and minor components are equal and its build
	 * component is greater than the argument build component, or all components are
	 * equal.
	 * </p>
	 *
	 * @param other the other version identifier
	 * @return <code>true</code> if this version identifier is compatible with the
	 *         given version identifier, and <code>false</code> otherwise
	 */
	public boolean isGreaterOrEqualTo(final Version other) {
		if (other == null) {
			return false;
		}
		if (major > other.major) {
			return true;
		}
		if ((major == other.major) && (minor > other.minor)) {
			return true;
		}
		if ((major == other.major) && (minor == other.minor) && (build >= other.build)) {
			return true;
		}
		if ((major == other.major) && (minor == other.minor) && (build == other.build)
				&& name.equalsIgnoreCase(other.name)) {
			return true;
		}
		return false;
	}

	/**
	 * Compares two version identifiers for compatibility.
	 * <p>
	 * A version identifier is considered to be compatible if its major component
	 * equals to the argument major component, and its minor component is greater
	 * than or equal to the argument minor component. If the minor components are
	 * equal, than the build component of the version identifier must be greater
	 * than or equal to the build component of the argument identifier.
	 * </p>
	 *
	 * @param other the other version identifier
	 * @return <code>true</code> if this version identifier is compatible with the
	 *         given version identifier, and <code>false</code> otherwise
	 */
	public boolean isCompatibleWith(final Version other) {
		if (other == null) {
			return false;
		}
		if (major != other.major) {
			return false;
		}
		if (minor > other.minor) {
			return true;
		}
		if (minor < other.minor) {
			return false;
		}
		if (build >= other.build) {
			return true;
		}
		return false;
	}

	/**
	 * Compares two version identifiers for equivalence.
	 * <p>
	 * Two version identifiers are considered to be equivalent if their major and
	 * minor components equal and are at least at the same build level as the
	 * argument.
	 * </p>
	 *
	 * @param other the other version identifier
	 * @return <code>true</code> if this version identifier is equivalent to the
	 *         given version identifier, and <code>false</code> otherwise
	 */
	public boolean isEquivalentTo(final Version other) {
		if (other == null) {
			return false;
		}
		if (major != other.major) {
			return false;
		}
		if (minor != other.minor) {
			return false;
		}
		if (build >= other.build) {
			return true;
		}
		return false;
	}

	/**
	 * Compares two version identifiers for order using multi-decimal comparison.
	 *
	 * @param other the other version identifier
	 * @return <code>true</code> if this version identifier is greater than the
	 *         given version identifier, and <code>false</code> otherwise
	 */
	public boolean isGreaterThan(final Version other) {
		if (other == null) {
			return false;
		}
		if (major > other.major) {
			return true;
		}
		if (major < other.major) {
			return false;
		}
		if (minor > other.minor) {
			return true;
		}
		if (minor < other.minor) {
			return false;
		}
		if (build > other.build) {
			return true;
		}
		return false;

	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Version)) {
			return false;
		}
		Version other = (Version) obj;
		if ((major != other.major) || (minor != other.minor) || (build != other.build)
				|| !name.equalsIgnoreCase(other.name)) {
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Returns the string representation of this version identifier. The result
	 * satisfies <code>version.equals(new Version(version.toString()))</code>.
	 */
	@Override
	public String toString() {
		if (asString == null) {
			asString = "" + major + SEPARATOR + minor + SEPARATOR + build //$NON-NLS-1$
					+ (name.length() == 0 ? "" : SEPARATOR + name); //$NON-NLS-1$
			if (!MODIFIER.isEmpty()) {
				asString += "-" + MODIFIER;
			}
		}
		return asString;
	}

	/** {@inheritDoc} */
	@Override
	public int compareTo(final Version obj) {
		if (equals(obj)) {
			return 0;
		}
		if (major != obj.major) {
			return major - obj.major;
		}
		if (minor != obj.minor) {
			return minor - obj.minor;
		}
		if (build != obj.build) {
			return build - obj.build;
		}
		return name.toLowerCase().compareTo(obj.name.toLowerCase());
	}

	/**
	 * Get the versioned jar file name for a particular base name.
	 *
	 * @param file base name without version
	 * @return versioned jar file name
	 */
	public static String getJarFilename(String file) {
		String fname = file + "-" + Version.CURRENT;
		if (!Version.MODIFIER.isEmpty()) {
			fname += "-" + Version.MODIFIER;
		}
		fname += ".jar";
		return fname;
	}

	/**
	 * Gets the current.
	 *
	 * @return the current
	 */
	public static Version getCurrent() {
		if (currentVersion == null) {
			currentVersion = create(CURRENT);
		}
		return currentVersion;
	}

}
