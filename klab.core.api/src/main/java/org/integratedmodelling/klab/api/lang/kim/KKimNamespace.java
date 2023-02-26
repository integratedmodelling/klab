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
     * Return all the namespace <strong>explicitly</strong> imported through a 'using' statement.
     * See {@link #getImportedNamespaceIds(boolean)} for the implicit imports.
     * 
     * @return the imported namespaces, which will have been validated as existing.
     */
    List<String> getImportedNamespaceIds();

    /**
     * Return all the namespaces that this should not be mixed with during resolution or scenario
     * setting.
     *
     * @return IDs of namespaces we do not agree with
     */
    Collection<String> getDisjointNamespaces();

    /**
     * Find a statement by name and type.
     * 
     * @param name
     * @return
     */
    <T extends KKimStatement> T getStatement(String name, Class<T> cls);

    /**
     * The timestamp of creation of the namespace object - not the underlying file resource (see
     * {@link #getFile()} for that).
     * 
     * @return time of creation
     */
    long getTimestamp();

//    /**
//     * The project this is part of. May be null only if the namespace is a rogue script, which is a
//     * possible use case in testing.
//     * 
//     * @return the project, or null.
//     */
//    IKimProject getProject();

    /**
     * Imports of external OWL ontologies (probably to be phased out)
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

    Map<String, Object> getSymbolTable();

    boolean isInactive();

    boolean isScenario();

    /**
     * The domain concept, if stated.
     * 
     * @return
     */
    KKimConcept getDomain();

//    /**
//     * If the namespace was read from a file, return it.
//     * 
//     * @return the local file or null
//     */
//    File getFile();

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
     * Return all the statements in a flat list, in order of definition (their line offset will be
     * ordered in increasing order).
     * 
     * @return
     */
    List<KKimStatement> getAllStatements();

    /**
     * Return the set of namespace IDs corresponding to all objects being imported, either
     * explicitly or (if scanUsages == true) implicitly through referenced worldview concepts.
     * 
     * @param scanUsages if true, scan all usages. May be a slightly expensive operation.
     * @return all the namespace IDs imported according to request.
     */
    Collection<String> getImportedNamespaceIds(boolean scanUsages);

//    /**
//     * The k.IM loader that loaded (or is loading) this namespace. Null during validation but valid
//     * during code generation.
//     * 
//     * @return
//     */
//    IKimLoader getLoader();

    /**
     * If functions were given to constrain the namespace to a scale, return them.
     * 
     * @return
     */
    List<KServiceCall> getExtents();

    /**
     * A list of the imported namespace IDs.
     * 
     * @return
     */
    Collection<String> getImportedIds();

}
