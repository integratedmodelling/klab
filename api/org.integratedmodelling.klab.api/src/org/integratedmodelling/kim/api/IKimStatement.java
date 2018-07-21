package org.integratedmodelling.kim.api;

import java.util.List;

/**
 * 
 * @author Ferd
 *
 */
public interface IKimStatement extends IKimScope {

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
     * @return the metadata
     */
    IKimMetadata getMetadata();

    /**
     * 
     * @return the documentation
     */
    IKimMetadata getDocumentationMetadata();

    /**
     * 
     * @return true if deprecated
     */
    boolean isDeprecated();

    /**
     * 
     * @return the reason for deprecation
     */
    String getDeprecation();

    /**
     * 
     * @return the source code
     */
    String getSourceCode();

    /**
     * All statements have a parent statement except a IKimNamespace, which always returns null,
     * or any objects built from declarations not in a structured namespace, such as concept
     * declarations parsed from loose strings.
     * 
     * @return the parent, or null in case of a namespace or a k.IM object built from a 
     *         loose statement
     */
    IKimStatement getParent();

}
