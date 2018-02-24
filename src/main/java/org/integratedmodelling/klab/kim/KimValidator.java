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
import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimFunctionCall;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimObserver;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.Kim.UrnDescriptor;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Models;
import org.integratedmodelling.klab.Namespaces;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Reasoner;
import org.integratedmodelling.klab.Workspaces;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.ConceptStatement;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.owl.KimKnowledgeProcessor;

public class KimValidator implements Kim.Validator {

  Monitor              monitor;
  Map<String, Integer> recheckObservationNS  = new HashMap<>();

  /*
   * holds the mapping between the actual ontology ID and the declared one in root domains where
   * "import <coreUrl> as <prefix>" was used.
   */
  Map<String, String>  corePrefixTranslation = new HashMap<>();

  public KimValidator(Monitor monitor) {
    this.monitor = monitor;
  }

  public KimValidator(KimValidator kimValidator, Monitor monitor) {
    this(monitor);
    corePrefixTranslation.putAll(kimValidator.corePrefixTranslation);
  }

  public KimValidator with(Monitor monitor) {
    return new KimValidator(this, monitor);
  }

  @Override
  public INamespace synchronizeNamespaceWithRuntime(IKimNamespace namespace) {

    Namespace ns = new Namespace(namespace);

    try {
      Namespaces.INSTANCE.release(ns, monitor);
    } catch (KlabException e) {
      monitor.error(e);
    }

    for (Pair<String, String> imp : namespace.getOwlImports()) {
      String prefix =
          Workspaces.INSTANCE.getUpperOntology().importOntology(imp.getFirst(), imp.getSecond());
      if (prefix == null) {
        monitor.error("cannot resolve import " + imp.getFirst(), namespace);
      } else {
        corePrefixTranslation.put(imp.getSecond(), prefix);
      }
    }

    /*
     * these should never throw exceptions; instead they should notify any errors, no matter how
     * internal, through the monitor
     */
    for (IKimScope statement : namespace.getChildren()) {

      IKimObject object = null;

      if (statement instanceof IKimConceptStatement) {
        object = new ConceptStatement((IKimConceptStatement) statement);
        IConcept concept = KimKnowledgeProcessor.INSTANCE.build((IKimConceptStatement) statement, ns,
            (ConceptStatement) object, monitor);
        if (concept == null) {
          object = null;
        }
      } else if (statement instanceof IKimModel) {
        object = ModelBuilder.INSTANCE.build((IKimModel) statement, ns, monitor);
        if (object instanceof IModel) {
          try {
            Models.INSTANCE.index((IModel) object, monitor);
          } catch (KlabException e) {
            monitor.error(
                "error storing valid model " + ((IModel) object).getName() + ": " + e.getMessage());
          }
        }
      } else if (statement instanceof IKimObserver) {
        object = ObservationBuilder.INSTANCE.build((IKimObserver) statement, ns, monitor);
        if (object instanceof IObserver) {
          try {
            Observations.INSTANCE.index((IObserver) object, monitor);
          } catch (KlabException e) {
            monitor.error(
                "error storing valid model " + ((IModel) object).getName() + ": " + e.getMessage());
          }
        }
      }

      if (object != null) {
        ns.addObject(object);
      }
    }

    /*
     * TODO finalize namespace, send any notification
     */
    Namespaces.INSTANCE.registerNamespace(ns, monitor);
    Observations.INSTANCE.registerNamespace(ns, monitor);

    Reasoner.INSTANCE.addOntology(ns.getOntology());

    /*
     * Execute any annotations recognized by the engine.
     */
    for (IKimObject object : ns.getObjects()) {
      for (IKimAnnotation annotation : object.getAnnotations()) {
        Annotations.INSTANCE.process(annotation, object, monitor);
      }
    }

    return ns;
  }

  @Override
  public List<Pair<String, Level>> validateFunction(IKimFunctionCall functionCall,
      IPrototype.Type expectedType) {
    List<Pair<String, Level>> ret = new ArrayList<>();
    IPrototype prototype = Extensions.INSTANCE.getServicePrototype(functionCall.getName());
    if (prototype != null) {
      return prototype.validate(functionCall);
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

  @Override
  public boolean isFunctionKnown(String functionName) {
    return Extensions.INSTANCE.getServicePrototype(functionName) != null;
  }

  @Override
  public boolean isAnnotationKnown(String annotationName) {
    return Annotations.INSTANCE.getPrototype(annotationName) != null;
  }

  @Override
  public List<Pair<String, Level>> validateAnnotation(IKimFunctionCall annotationCall,
      IKimStatement target) {
    List<Pair<String, Level>> ret = new ArrayList<>();
    IPrototype prototype = Annotations.INSTANCE.getPrototype(annotationCall.getName());
    if (prototype != null) {
      return prototype.validate(annotationCall);
    }
    // Annotations w/o prototype are allowed
    return ret;

  }

}
