package org.integratedmodelling.klab.documentation;

import java.util.LinkedHashMap;

/**
 * Eventually a map using all the tags from {@link BibTexFields} so it can be
 * linked to bibtex and use import tools and the like. For now we can use it as
 * such when importing BibTex, but the IDE and reporting engine only use the
 * field {@link BibTexFields#EXAMPLE_CITATION} to store a fixed citation to use
 * in references.
 * 
 * @author ferdinando.villa
 *
 */
public class Reference extends LinkedHashMap<String, String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4783514922443736809L;

}
