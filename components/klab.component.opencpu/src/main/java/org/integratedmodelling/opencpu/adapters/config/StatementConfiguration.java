package org.integratedmodelling.opencpu.adapters.config;

/**
 * Statements should be functional. If pipelined, the statement will be connected to the previous,
 * taking the first argument from the result of the previous and executing as a unit within a
 * pipeline.
 * 
 * We shouldn't rely upon or need side effects so the outputVariable field should not be null unless
 * we expect to pipe the output into the next statement. If outputVariable is null and there is a
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
 * An I/O method may be specified in alternative to, or along with, the R code. If so, the I/O
 * method will be executed by the runtime to adapt k.LAB data to R or viceversa. For non-POD data
 * this will usually require saving to temporary files and importing, or writing specialized code
 * statements specifying data frames. If there is any R code, it will be executed after the I/O
 * method if in input, or before the I/O method if in output.
 * 
 * @author Ferd
 *
 */
public class StatementConfiguration {

    private String outputVariable;
    private String code;
    private String constraint;

}
