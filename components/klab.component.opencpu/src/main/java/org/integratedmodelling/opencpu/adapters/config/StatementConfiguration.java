package org.integratedmodelling.opencpu.adapters.config;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.opencpu.adapters.OpenCPUAdapter.InternalOperation;

/**
 * Statements should be functional. If pipelined, the statement will be connected to the previous,
 * taking the first argument from the result of the previous and executing as a unit within a
 * pipeline.
 * 
 * We shouldn't rely upon or need side effects so the <i>variable</i> field should not be null
 * unless we expect to pipe the output into the next statement. If variable is null and there is a
 * statement after this one, the output will be piped using %>%. Otherwise it will be lost and a
 * warning should be emitted when validating the resource.
 * 
 * Named and unnamed parameters may be coming from previously computed statements, resource
 * parameters or resource arguments, with the latter overriding the previous.
 * 
 * Optional constraints are boolean Groovy expressions evaluated against the resource arguments and
 * parameters as well as the names of previous named statements, mapped to true if the result wasn't
 * NULL. If they return false, the statement is skipped.
 * 
 * An I/O internal operation may be specified in alternative to, or along with, the R code. If so,
 * the I/O method will be executed by the runtime to adapt k.LAB data to R or viceversa. For non-POD
 * data this will usually require saving to temporary files and importing, or writing specialized
 * code statements specifying data frames. If there is any R code, it will be executed after the I/O
 * method if in input, or before the I/O method if in output. Internal operations are identified by
 * a known enum and possibly a set of parameters.
 * 
 * In case the statement has an internalOperation specifying import from k.LAB, the variable name
 * can start with a @ to specify an annotation that the inputs may be tagged with, instead of an
 * individual input name. In that case the output name should be specified in the operation
 * parameters and documented in the method docs.
 * 
 * Some internal operations may be assertions, i.e. only the constraint code matters and if it evaluates to
 * false, the whole chain of execution stops and contextualization fails.
 * 
 *  @author Ferd
 *
 */
public class StatementConfiguration {
    
    private String variable;
    private String code;
    private String constraint;
    private InternalOperation internalOperation;
    private Map<String, String> internalOperationParameters = new HashMap<>();
    
    public String getVariable() {
        return variable;
    }
    public void setVariable(String variable) {
        this.variable = variable;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getConstraint() {
        return constraint;
    }
    public void setConstraint(String constraint) {
        this.constraint = constraint;
    }
    public InternalOperation getInternalOperation() {
        return internalOperation;
    }
    public void setInternalOperation(InternalOperation internalOperation) {
        this.internalOperation = internalOperation;
    }
    public Map<String, String> getInternalOperationParameters() {
        return internalOperationParameters;
    }
    public void setInternalOperationParameters(Map<String, String> internalOperationParameters) {
        this.internalOperationParameters = internalOperationParameters;
    }
}
