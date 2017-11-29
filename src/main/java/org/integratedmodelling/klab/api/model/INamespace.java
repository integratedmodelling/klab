/*******************************************************************************
 *  Copyright (C) 2007, 2015:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.api.model;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.errormanagement.ICompileNotification;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IDocumentation;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IOntology;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.observations.IScale;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

/**
 * This class represents a namespace declared through a k.IM file.
 * 
 * @author  Ferd
 */
public interface INamespace extends IKimObject {
    
    String getName();

    List<IKimObject> getObjects();
    
    List<IKimObject> getAllObjects();

    IKimObject getObject(String id);
    
    /**
     * Time of creation of the underlying resource if any, time when the object definition 
     * finished otherwise.
     * 
     * @return the time of creation of the namespace.
     */
    long getTimeStamp();

    /**
     * Namespaces that are part of domain ontologies will return a non-null concept here, which must be
     * a domain trait specified using 'domain root' in the core domain namespace. If this IS the core
     * domain namespace, the concept returned will be the core domain concept from the foundational
     * ontology.
     * 
     * @return the domain this namespace is part of, if any.
     */
    IConcept getDomain();

    /**
     * Return the project that this namespace was created from. Currently it can be null but
     * shouldn't - external import units should be projects and interactive sessions should 
     * operate within a specialized project.
     * 
     * @return the project this namespace is part of.
     */
    IProject getProject();

    /**
     * Return all namespaces imported by this one.
     * 
     * @return all the namespaces imported.
     */
    Collection<INamespace> getImportedNamespaces();
    
    /**
     * @return namespaces for training the models in us - currently unused.
     */
    List<String> getTrainingNamespaces();

    /**
     * @return namespaces to restrict lookup from - currently unused
     */
    List<String> getLookupNamespaces();

    /**
     * If a model was given a specific coverage in any extent, either directly or through
     * a namespace-wide specification, return the context that
     * expresses that coverage. If no coverage has been specified, return an empty
     * context.
     * @param monitor 
     * 
     * @return any scale constraints imposed on the namespace
     */
    IScale getCoverage(IMonitor monitor);

    /**
     * Return true if the namespace has any errors that will prevent the use of its
     * model objects.
     * 
     * @return whether parsing this namespace generated any errors
     */
    boolean hasErrors();

    /**
     * Return true if the namespace has warnings that should be reported before use.
     * 
     * @return whether parsing this namespace generated any warnings
     */
    boolean hasWarnings();

    /**
     * Retrieve all errors, warnings and info annotations. Use instanceof to see which is which.
     * 
     * @return all notifications (info, warning, error) occurred during parsing.
     */
    Collection<ICompileNotification> getCodeAnnotations();

    /**
     * Returns the ontology associated with the namespace. Not that you should do anything
     * with it.
     * 
     * @return the ontology. Never null.
     */
    IOntology getOntology();

    /**
     * The namespace's symbol table should contain any model objects and define's encountered, plus
     * all the imported symbols from any imported namespaces.
     * 
     * @return the table of known symbols from declarations and imports.
     */
    Map<String, Object> getSymbolTable();

    /**
     * Return true if this namespace is a scenario, meaning that its models will only be
     * used (and if so, preferentially) to resolve dependencies when the scenario is
     * made active.
     * 
     * @return true if this is a scenario
     */
    boolean isScenario();

    /**
     * Return the model resolution criteria defined for the namespace, or the default ones if none were
     * given. These are basically a set of weights that will apply to each model retrieved to resolve
     * a concept in order to rank them and choose the best. Resolution criteria may vary by namespace.
     * 
     * @return resolution criteria for ranking of models matching dependencies in here.
     */
    IMetadata getResolutionCriteria();

    /**
     * Return all the namespaces that this should not be mixed with during
     * resolution or scenario setting.
     * 
     * @return IDs of namespaces we do not agree with
     */
    Collection<String> getDisjointNamespaces();

    /**
     * Return the local file this namespace was read from, or null if it wasn't read from
     * a file. Returning null means in all likelihood that this namespace will never be
     * reloaded. At this stage of development, nothing should ever return null.
     * 
     * FIXME check overlap with getResourceUrl()
     * 
     * @return the file we were read from. 
     */
    File getLocalFile();

    /**
     * If the namespace is private, each model in it is private even if not 
     * tagged as such. 
     * 
     * @return whether the whole namespace is private
     */
    boolean isPrivate();

    /**
     * If the namespace is inactive, each model in it is inactive even if not 
     * tagged as such. 
     * 
     * @return true if the whole namespace is void
     */
    boolean isInactive();
    
    /**
     * True if the namespace does not define any knowledge that cannot be reconstructed
     * exclusively from worldview concepts. This is equivalent to using only 'equals' 
     * instead of 'is' in concept definitions.
     * 
     * @return true if canonical
     */
    boolean isCanonical();

    /**
     * A tainted namespace has concepts that do not derive directly from
     * the worldview. A namespace that is part of a worldview is never
     * tainted.
     * 
     * @return
     */
    boolean isTainted();
    
    /**
     * The doc string defined for the namespace. Should never
     * be null.
     * 
     * @return description for the namespace
     */
    String getDescription();

    /**
     * Documentation in namespaces is merged with that of models in it. It's good to
     * store references or links that apply to more than one model.
     * 
     * @return
     */
    IDocumentation getDocumentation();

    boolean isInternal();

}
