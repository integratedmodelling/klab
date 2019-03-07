package org.integratedmodelling.klab.api.documentation;

import java.util.List;

/**
 * A contextualizer that is also a IDocumentationProvider can provide special
 * tags for insertion in any documentation generated.
 * 
 * @author ferdinando.villa
 *
 */
public interface IDocumentationProvider {

	/**
	 * 
	 * @return the tags provided
	 */
	List<String> getTags();

//	/**
//	 * 
//	 * @param tag
//	 * @return the trigger for the tag, or null
//	 */
//	Trigger getTagTrigger(String tag);
//
//	/**
//	 * 
//	 * @param tag
//	 * @return the section for the tag, or null
//	 */
//	String getTagSection(String tag);

	/**
	 * 
	 * @param tag
	 * @return the documentation corresponding to the tag
	 */
	String getDocumentation(String tag);

}
