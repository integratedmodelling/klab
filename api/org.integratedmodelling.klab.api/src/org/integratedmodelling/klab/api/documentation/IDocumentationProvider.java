package org.integratedmodelling.klab.api.documentation;

import java.util.Collection;

/**
 * A contextualizer that is also a IDocumentationProvider can provide special
 * tags for insertion in any documentation generated. Others can be provided
 * through specialized annotations such as @table or @graph.
 * 
 * @author ferdinando.villa
 *
 */
public interface IDocumentationProvider {

	interface Item {

		String getId();

		String getTitle();

		String getMarkdownContents();
	}

	/**
	 * 
	 * @param tag
	 * @return the documentation corresponding to the tag
	 */
	Collection<Item> getDocumentation();

}
