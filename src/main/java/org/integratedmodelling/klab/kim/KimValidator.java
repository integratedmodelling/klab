package org.integratedmodelling.klab.kim;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.Tuples;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimFunctionCall;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimObserver;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.Kim.UrnDescriptor;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Namespaces;
import org.integratedmodelling.klab.Reasoner;
import org.integratedmodelling.klab.Workspaces;
import org.integratedmodelling.klab.api.extensions.IPrototype;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.model.ConceptStatement;
import org.integratedmodelling.klab.model.Namespace;

public class KimValidator implements Kim.Validator {

	IMonitor monitor;
	
	/*
	 * holds the mapping between the actual ontology ID and the declared one
	 * in root domains where "import <coreUrl> as <prefix>" was used.
	 */
	Map<String, String> corePrefixTranslation = new HashMap<>();
	
	public KimValidator(IMonitor monitor) {
		this.monitor = monitor;
	}

	@Override
	public INamespace synchronizeNamespaceWithRuntime(IKimNamespace namespace) {

		Namespaces.INSTANCE.release(namespace.getName());
		Namespace ns = new Namespace(namespace);

		for (Pair<String, String> imp : namespace.getOwlImports()) {
		    String prefix = Workspaces.INSTANCE.getUpperOntology().importOntology(imp.getFirst(), imp.getSecond());
		    if (prefix == null) {
		        monitor.error("cannot resolve import " + imp.getFirst(), namespace);
		    } else {
		        corePrefixTranslation.put(imp.getSecond(), prefix);
		    }
		}
		
		/*
		 * these should never throw exceptions; instead they should notify any errors,
		 * no matter how internal, through the monitor
		 */
		for (IKimScope statement : namespace.getChildren()) {
		    
		    IKimObject object = null;
		    
			if (statement instanceof IKimConceptStatement) {
				IConcept concept = ConceptBuilder.INSTANCE.build((IKimConceptStatement) statement, ns, monitor);
				if (concept != null) {
	                // wrap in a concept definition to add to namespace
				    object = new ConceptStatement((IKimConceptStatement)statement, concept);
				}
			} else if (statement instanceof IKimModel) {
			    object = ModelBuilder.INSTANCE.build((IKimModel) statement, ns, monitor);
			} else if (statement instanceof IKimObserver) {
				object = ObservationBuilder.INSTANCE.build((IKimObserver) statement, ns, monitor);
			}
			
			if (object != null) {
			    ns.addObject(object);
			}
		}
		
		/*
		 * TODO finalize namespace and send any notification
		 */
		Namespaces.INSTANCE.registerNamespace(ns);
        Reasoner.INSTANCE.addOntology(ns.getOntology());
        
        return ns;
	}

	@Override
    public List<Pair<String, Level>> validateFunction(IKimFunctionCall functionCall, Set<Type> expectedType) {
	    List<Pair<String, Level>> ret = new ArrayList<>();
		IPrototype prototype = Extensions.INSTANCE.getServicePrototype(functionCall.getName());
		if (prototype != null) {
		    
		} else {
		    ret.add(Tuples.create("Function " + functionCall.getName() + " is unknown", Level.SEVERE));
		}
		return ret;
	}

	@Override
	public UrnDescriptor classifyUrn(String urn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnumSet<Type> classifyCoreType(String string, EnumSet<Type> statedType) {
	    
		IConcept coreType = Concepts.INSTANCE.getConcept(string);
		if (coreType == null) {
			return EnumSet.noneOf(Type.class);
		}
		/*
		 * TODO check type
		 */
		return statedType;
	}

}
