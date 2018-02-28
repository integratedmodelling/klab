package org.integratedmodelling.klab.api.runtime;

public interface IRuntimeProvider {

    /**
     * Mandatory ID of the execution function for k.IM expression code. 
     */
    static final public String EXECUTE_FUNCTION_ID                           = "klab.runtime.exec";
    static final public String EXECUTE_FUNCTION_PARAMETER_CODE               = "code";
    static final public String EXECUTE_FUNCTION_PARAMETER_LANGUAGE           = "language";
    static final public String EXECUTE_FUNCTION_PARAMETER_CONDITION          = "ifcondition";
    static final public String EXECUTE_FUNCTION_PARAMETER_NEGATIVE_CONDITION = "unlesscondition";
    
    /**
     * A service call whose only purpose is to carry a literal. Doesn't even get compiled into
     * KDL (its source code is the literal itself), so no need for an implementation.
     */
    static final public String LITERAL_FUNCTION_ID                           = "klab.literal";

}
