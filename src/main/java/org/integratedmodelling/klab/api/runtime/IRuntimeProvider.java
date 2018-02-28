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
}
