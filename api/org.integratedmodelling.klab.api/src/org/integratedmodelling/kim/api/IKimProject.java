package org.integratedmodelling.kim.api;

import java.io.File;
import java.util.List;
import java.util.Properties;

import org.integratedmodelling.kactors.api.IKActorsBehavior;

public interface IKimProject {

	static final String KLAB_CONFIGURATION_DEFINED_WORLDVIEW_ID = "klab.defined.worldview";
	static final String KLAB_CONFIGURATION_WORLDVIEW_ID = "klab.worldview";

	static public final String SOURCE_FOLDER = "src";
    static public final String DOCUMENTATION_FOLDER = "docs";
	static public final String SCRIPT_FOLDER = "apps";
	static public final String TESTS_FOLDER = "tests";
	static public final String RESOURCE_FOLDER = "resources";

	/**
	 * 
	 * @return
	 */
	String getDefinedWorldview();

	/**
	 * 
	 * @return
	 */
	List<File> getSourceFiles();

	/**
	 * 
	 * @param id
	 * @return
	 */
	IKimNamespace getNamespace(String id);

	/**
	 * 
	 * @return
	 */
	IKimWorkspace getWorkspace();

	/**
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 
	 * @return
	 */
	String getWorldview();

	/**
	 * 
	 * @return
	 */
	File getRoot();

	/**
	 * 
	 * @return
	 */
	Properties getProperties();

	/**
	 * 
	 * @return
	 */
	List<IKimNamespace> getNamespaces();

	/**
	 * 
	 * @return
	 */
	List<IKActorsBehavior> getBehaviors();
	
	/**
	 * 
	 * @return
	 */
	boolean isErrors();

	boolean isWarnings();

}
