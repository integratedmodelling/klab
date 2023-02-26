package org.integratedmodelling.klab.api.lang.kim.impl;

import java.util.List;

import org.integratedmodelling.klab.api.collections.KParameters;
import org.integratedmodelling.klab.api.lang.KAnnotation;
import org.integratedmodelling.klab.api.lang.kim.KKimScope;
import org.integratedmodelling.klab.api.lang.kim.KKimStatement;

/**
 * 
 * @author Ferd
 *
 */
public class KimStatement implements KKimStatement {

    private static final long serialVersionUID = -7273214821906819558L;

    @Override
    public int getFirstLine() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getLastLine() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getFirstCharOffset() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getLastCharOffset() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<KAnnotation> getAnnotations() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDeprecation() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isDeprecated() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getSourceCode() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isErrors() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isWarnings() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public KParameters<String> getMetadata() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<KKimScope> getChildren() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getLocationDescriptor() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getURI() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void visit(Visitor visitor) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public KParameters<String> getDocumentationMetadata() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getNamespace() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KKimStatement getParent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getResourceId() {
        // TODO Auto-generated method stub
        return null;
    }



}
