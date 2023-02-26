package org.integratedmodelling.klab.api.lang.kim.impl;

import org.integratedmodelling.klab.api.lang.kim.KKimExpression;

/**
 * Just a wrapper for some code and an optional language identifier. Used
 * explicitly only where code must be distinguished from other string values,
 * such as in classifiers.
 * 
 * @author Ferd
 *
 */
public class KimExpression implements KKimExpression {

    private static final long serialVersionUID = -8760044805799296995L;

    @Override
    public String getCode() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getLanguage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isForcedScalar() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getSourceCode() {
        // TODO Auto-generated method stub
        return null;
    }

}
