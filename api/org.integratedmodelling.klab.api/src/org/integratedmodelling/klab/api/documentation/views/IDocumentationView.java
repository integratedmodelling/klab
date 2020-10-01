package org.integratedmodelling.klab.api.documentation.views;

import java.io.OutputStream;

/**
 * Return value of knowledge views when compiled. Hold the usable form of the
 * artifact represented - either text or binary resource.
 * 
 * @author Ferd
 *
 */
public interface IDocumentationView {

	/**
	 * If true, use getText() to access the artifact (e.g. markdown or HTML).
	 * Otherwise, use write(OutputStream) which is always an option.
	 * 
	 * @return
	 */
	boolean isText();

	/**
	 * Get the artifact in text form. Only call if isText() returns true.
	 * 
	 * @return
	 */
	String getText();

	/**
	 * Get the artifact as an outputstream. Should always work, even when isText() returns
	 * true.
	 * 
	 * @return
	 */
	void write(OutputStream output);

}
