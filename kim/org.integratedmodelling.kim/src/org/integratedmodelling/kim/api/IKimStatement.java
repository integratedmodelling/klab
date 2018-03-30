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
     * @return
     */
    int getFirstLine();

    /**
     * 
     * @return
     */
    int getLastLine();

    /**
     * 
     * @return
     */
    int getFirstCharOffset();

    /**
     * 
     * @return
     */
    int getLastCharOffset();

    /**
     * 
     * @return
     */
    List<IKimAnnotation> getAnnotations();

    /**
     * 
     * @return
     */
    IKimMetadata getMetadata();

    /**
     * 
     * @return
     */
    IKimMetadata getDocumentationMetadata();

    /**
     * 
     * @return
     */
    boolean isDeprecated();

    /**
     * 
     * @return
     */
    String getDeprecation();

    /**
     * 
     * @return
     */
    String getSourceCode();

}
