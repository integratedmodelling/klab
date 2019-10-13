package org.integratedmodelling.controlcenter.api;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.utils.OS;

public interface IProduct {

	public enum Status {

		/**
		 * Status before status is assessed and when offline but with available
		 * downloaded distributions.
		 */
		UNKNOWN,

		/**
		 * Status when offline or other error has occurred
		 */
		UNAVAILABLE,

		/**
		 * When the latest available build is available locally.
		 */
		UP_TO_DATE,

		/**
		 * When builds are available but there are unsynchronized more recent builds.
		 */
		OBSOLETE
	}

	enum Type {

		UNKNOWN,

		/**
		 * Jar packaging with bin/, lib/ and a main jar file with a main class in
		 * properties, OS independent distribution with potential OS-specific
		 * subcomponents to merge in from subdirs.
		 */
		JAR,

		/**
		 * Installer executable packaging.
		 */
		INSTALLER_EXECUTABLE,

		/**
		 * Direct executable packaging.
		 */
		DIRECT_EXE,

		/**
		 * Eclipse packaging with a zipped or unzipped distribution per supported OS.
		 */
		ECLIPSE
	}

	public final static String PRODUCT_NAME_PROPERTY = "klab.product.name";
	public final static String PRODUCT_DESCRIPTION_PROPERTY = "klab.product.description";
	public final static String PRODUCT_AVAILABLE_BUILDS_PROPERTY = "klab.product.builds";
	public final static String PRODUCT_TYPE_PROPERTY = "klab.product.type";
	public final static String PRODUCT_OSSPECIFIC_PROPERTY = "klab.product.osspecific";

	public final static String BUILD_VERSION_PROPERTY = "klab.product.build.version";
	public final static String BUILD_MAINCLASS_PROPERTY = "klab.product.build.main";
	public final static String BUILD_TIME_PROPERTY = "klab.product.build.time";

	/**
	 * Name of product is a lowercase string, short and without spaces,
	 * corresponding to the directory where the product is hosted.
	 * 
	 * @return product ID
	 */
	String getId();

	/**
	 * True if a different distribution is needed per supported operating system.
	 * The {@link OS} enum is used to characterize the OS.
	 * 
	 * @return
	 */
	boolean isOsSpecific();

	/**
	 * Get the type of product. The type enum is of course limited to the current
	 * usage and should be expanded as needed.
	 * 
	 * @return
	 */
	Type getType();

	/**
	 * Name of product is the user-readable name, potentially with more words but
	 * short and suitable for buttons or choice boxes.
	 * 
	 * @return product name
	 */
	String getName();

	/**
	 * Longer description of the product.
	 * 
	 * @return
	 */
	String getDescription();

	/**
	 * List of available builds in most recent -> least recent order, so that the
	 * first element is the most recent.
	 * 
	 * @return
	 */
	List<Integer> getBuilds();

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

	/**
	 * True if build is locally available
	 * 
	 * @param build
	 * @return
	 */
	boolean isInstalled(int build);

	/**
	 * True if build is remotely available
	 * 
	 * @param build
	 * @return
	 */
	boolean isAvailable(int build);

}
