package org.integratedmodelling.opencpu.adapters.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Each operation is a sequence of statements, possibly in a pipeline. Statements use variables
 * produced by other statements. An operation corresponds to the resourceId fragment of the URN and
 * must be one of those admitted by the Method configuration.
 * 
 * @author Ferd
 *
 */
public class OperationConfiguration {

    private String name;
    private String description;
    private List<StatementConfiguration> statements = new ArrayList<>();
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<StatementConfiguration> getStatements() {
        return statements;
    }
    public void setStatements(List<StatementConfiguration> statements) {
        this.statements = statements;
    }

}
