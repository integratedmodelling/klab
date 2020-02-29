package org.integratedmodelling.klab.api;

import java.util.List;

import org.integratedmodelling.kim.api.IKimAnnotation;

public interface IStatement {
	/**
	 * 
	 * @return the first line number
	 */
	int getFirstLine();

	/**
	 * 
	 * @return the last line number
	 */
	int getLastLine();

	/**
	 * 
	 * @return the start offset in the document
	 */
	int getFirstCharOffset();

	/**
	 * 
	 * @return the last offset in the document
	 */
	int getLastCharOffset();

	/**
	 * 
	 * @return the annotations
	 */
	List<IKimAnnotation> getAnnotations();

	/**
	 * 
	 * @return the reason for deprecation
	 */
	String getDeprecation();

	/**
	 * 
	 * @return true if deprecated
	 */
	boolean isDeprecated();

	/**
	 * 
	 * @return the source code
	 */
	String getSourceCode();

	boolean isErrors();

	boolean isWarnings();
}
