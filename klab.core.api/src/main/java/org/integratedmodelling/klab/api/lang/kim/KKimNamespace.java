package org.integratedmodelling.klab.api.lang.kim;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.collections.impl.Pair;
import org.integratedmodelling.klab.api.lang.KServiceCall;

/**
 * The syntactic peer of a k.LAB namespace.
 * 
 * @author ferdinando.villa
 *
 */
public interface KKimNamespace extends KKimStatement {

    /**
     * Roles a namespace can play within a project. Not fully integrated at the moment, although the
     * namespace should have a getRole() method to expose it.
     * 
     * @author Ferd
     *
     */
    public enum Role {
        KNOWLEDGE, SCRIPT, TESTCASE, CALIBRATION, SCENARIO
    }

    /**
     * The namespace ID: either the stated name (when the namespace is a regular one) or an id
     * composed of the adopted worldview and the unique resource path for the namespace when this
     * represents a script, test, calibration or sidecar file.
     * 
     * @return the name. Never null.
     */
    String getName();

    /**
     * Return all the namespaces that this should not be mixed with during resolution or scenario
     * setting.
     *
     * @return IDs of namespaces we do not agree with
     */
    Collection<String> getDisjointNamespaces();

    /**
     * The timestamp of creation of the namespace object - not the underlying file resource (see
     * {@link #getFile()} for that).
     * 
     * @return time of creation
     */
    long getTimestamp();

    /**
     * Imports of external OWL ontologies
     * 
     * @return
     */
    List<Pair<String, String>> getOwlImports();

    /**
     * Import of vocabularies from resources, as resource URN -> list of vocabularies from that
     * resource
     * 
     * @return
     */
    List<Pair<String, List<String>>> getVocabularyImports();

    /**
     * 
     * @return
     */
    Map<String, Object> getDefines();

    /**
     * True if declared as void.
     * 
     * @return
     */
    boolean isInactive();

    /**
     * True if declared as a scenario.
     * 
     * @return
     */
    boolean isScenario();

    /**
     * The domain concept, if stated.
     * 
     * @return
     */
    KKimConcept getDomain();

    /**
     * If this is a script, return its ID (either specified in a run annotation or the file name).
     * Otherwise return null.
     * 
     * @return the script ID or null.
     */
    String getScriptId();

    /**
     * If this is a test case, return its ID (either specified in a run annotation or the file
     * name). Otherwise return null.
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
     * Return all the top-level statements in order of definition.
     * 
     * @return
     */
    List<KKimStatement> getStatements();

    /**
     * If functions were given to constrain the namespace to a scale, return them.
     * 
     * @return
     */
    List<KServiceCall> getExtents();

    /**
     * A list of the imported namespace IDs matched to a (possibly null) list of identifiers
     * imported from each.
     * 
     * @return
     */
    Map<String, List<String>> getImports();

}
