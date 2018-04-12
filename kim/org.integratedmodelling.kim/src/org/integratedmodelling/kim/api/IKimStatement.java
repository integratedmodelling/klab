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
     * @return the start offset in the first line
     */
    int getFirstCharOffset();

    /**
     * 
     * @return the last offset in the last line
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

}
