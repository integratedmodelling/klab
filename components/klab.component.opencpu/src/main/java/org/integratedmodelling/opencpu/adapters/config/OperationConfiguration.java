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
    private List<StatementConfiguration> statements = new ArrayList<>();
}
