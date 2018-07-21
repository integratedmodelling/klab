package org.integratedmodelling.kim.api;

import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.utils.Pair;

public interface IKimNamespace extends IKimStatement {

    String getName();

    boolean isPrivate();

    List<IKimNamespace> getImported();

    long getTimestamp();

    IKimProject getProject();

    List<Pair<String, String>> getOwlImports();

    Map<String, Object> getSymbolTable();

    boolean isInactive();

    boolean isScenario();

    /**
     * If this is a script, return its ID (either specified in a run annotation or the file name). Otherwise return null.
     * 
     * @return the script ID or null.
     */
    String getScriptId();

    /**
     * If this is a test case, return its ID (either specified in a run annotation or the file name). Otherwise return null.
     * 
     * @return the test case ID or null.
     */
    String getTestCaseId();

    /**
     * Bound to a worldview, therefore used as a script or sidecar file.
     * 
     * @return
     */
    boolean isWorldviewBound();

    /**
     * Return all the statements in a flat list, in order of definition (their line offset will be 
     * ordered in increasing order).
     * 
     * @return
     */
    List<IKimStatement> getAllStatements();
}
