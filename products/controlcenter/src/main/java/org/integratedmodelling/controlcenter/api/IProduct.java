package org.integratedmodelling.controlcenter.api;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.utils.OS;

public interface IProduct {

	public enum Status {
		UNKNOWN, UNAVAILABLE, UP_TO_DATE, OBSOLETE
	}

	public final static String PRODUCT_NAME_PROPERTY = "klab.product.name";
	public final static String PRODUCT_DESCRIPTION_PROPERTY = "klab.product.description";

	public final static String BUILD_VERSION_PROPERTY = "klab.product.build.version";
	public final static String BUILD_TIME_PROPERTY = "klab.product.build.time";

	/**
	 * Name of product is a lowercase string, short and without spaces,
	 * corresponding to the directory where the product is hosted.
	 * 
	 * @return product ID
	 */
	String getId();

	/**
	 * Name of product is the user-readable name, potentially with more words but
	 * short and suitable for buttons or choice boxes.
	 * 
	 * @return product name
	 */
	String getName();

	/**
	 * List of available builds in most recent -> least recent order, so that the
	 * first element is the most recent.
	 * 
	 * @return
	 */
	List<Integer> getBuilds();

	/**
	 * Relative paths (from base URL) and corresponding hashes of all files
	 * composing the products at the passed build. Paths are slash-separated, start
	 * at base URL (no leading slash) and include the name of the product.
	 * 
	 * @param build
	 * @param os
	 * @return
	 */
	Map<String, String> getFileHashes(int build, OS os);

	/**
	 * The contents of the product.properties file in the product directory.
	 * Non-null values for all the static property names starting with PRODUCT_ in
	 * this interface are mandatory.
	 * 
	 * @return
	 */
	Properties getProperties();

	/**
	 * The contents of the build.properties file in each build directory. Should
	 * include date of build, version number and any other information deemed
	 * useful. Non-null values for all the static property names starting with
	 * BUILD_ in this interface are mandatory.
	 * 
	 * @return
	 */
	Properties getBuildProperties(int build);

	/**
	 * Version of specified build.
	 * 
	 * @return
	 */
	Version getBuildVersion(int build);

	/**
	 * Status of the product. If the product is UNAVAILABLE or UNKNOWN, properties,
	 * builds and the like may not be accessible.
	 * 
	 * @return
	 */
	Status getStatus();

}
