package org.integratedmodelling.klab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.eclipse.xtext.testing.IInjectorProvider;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.kdecl.ConceptDeclaration;
import org.integratedmodelling.kim.kdecl.ObservableSemantics;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.KimConcept;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IProperty;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IConceptService;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.integratedmodelling.klab.kim.ConceptBuilder;
import org.integratedmodelling.klab.owl.Concept;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.owl.Property;
import org.integratedmodelling.klab.utils.xtext.KnowledgeDeclarationInjectorProvider;
import com.google.inject.Inject;
import com.google.inject.Injector;

public enum Concepts implements IConceptService {

    INSTANCE;
	
	@Inject
    ParseHelper<ConceptDeclaration>  declarationParser;

    private Concepts() {
        IInjectorProvider injectorProvider = new KnowledgeDeclarationInjectorProvider();
        Injector injector = injectorProvider.getInjector();
        if (injector != null) {
            injector.injectMembers(this);
        }
    }

    @Override
    public IConcept declare(String declaration) {

        try {
            Object parsed = declarationParser.parse(declaration);
            ConceptDeclaration dcl = null;
            if (parsed instanceof ConceptDeclaration) {
              dcl = (ConceptDeclaration)parsed;
            } else if (parsed instanceof ObservableSemantics) {
              dcl = ((ObservableSemantics)parsed).getDeclaration();
            }
            KimConcept interpreted = Kim.INSTANCE.declareConcept(dcl);
            return ConceptBuilder.INSTANCE.declare(interpreted, Klab.INSTANCE.getRootMonitor());
        } catch (Exception e) {
          Klab.INSTANCE.error(e);
        }

        return null;
    }

    @Override
    public IConcept declare(IKimConcept observable, IMonitor monitor) {
        return ConceptBuilder.INSTANCE.declare(observable, monitor);
    }

    
    @Override
    public IProperty getProperty(String propertyId) {
        return OWL.INSTANCE.getProperty(propertyId);
    }

    @Override
    public Concept getConcept(String conceptId) {
        return OWL.INSTANCE.getConcept(conceptId);
    }

    @Override
    public Object getMetadata(IConcept concept, String field) {
        Object ret = concept.getMetadata().get(field);
        if (ret != null) {
            return ret;
        }
        for (IConcept c : concept.getParents()) {
            ret = getMetadata(c, field);
            if (ret != null) {
                return ret;
            }
        }
        return null;
    }

    /**
     * Quick static way to obtain a concept that is known to exist. Throws
     * an unchecked exception if the concept isn't found.
     * 
     * @param conceptId
     * @return the concept. Never null.
     */
    public static Concept c(String conceptId) {

        if (conceptId == null || conceptId.isEmpty()) {
            return null;
        }

        Concept ret = OWL.INSTANCE.getConcept(conceptId);
        if (ret == null) {
            throw new KlabRuntimeException("cannot find concept " + conceptId);
        }
        return ret;

    }

    /**
     * Quick static way to obtain a property that is known to exist. Throws
     * an unchecked exception if the property isn't found.
     * 
     * @param propertyId
     * @return the property. Never null.
     */
    public static Property p(String propertyId) {

        if (propertyId == null || propertyId.isEmpty()) {
            return null;
        }

        Property ret = OWL.INSTANCE.getProperty(propertyId);
        if (ret == null) {
            throw new KlabRuntimeException("cannot find property " + propertyId);
        }
        return ret;

    }

    /**
     * Get the best display name for a concept.
     * 
     * @param t
     * @return a name for display
     */
    public String getDisplayName(IConcept t) {

        String ret = t.getMetadata().getString(NS.DISPLAY_LABEL_PROPERTY);

        if (ret == null) {
            ret = t.getMetadata().getString(IMetadata.DC_LABEL);
        }
        if (ret == null) {
            ret = t.getName();
        }
        if (ret.startsWith("i")) {
            ret = ret.substring(1);
        }
        return ret;
    }

    /**
     * Arrange a set of concepts into the collection of the most specific members of each
     * concept hierarchy therein. Return one concept or null.
     * 
     * @param cc
     * @return least general
     */
    @Override
    public IConcept getLeastGeneralConcept(Collection<IConcept> cc) {
        Collection<IConcept> z = getLeastGeneral(cc);
        return z.size() > 0 ? z.iterator().next() : null;
    }

    @Override
    public IConcept getLeastGeneralCommonConcept(IConcept concept1, IConcept c) {
        return concept1.getLeastGeneralCommonConcept(c);
    }

    @Override
    public IConcept getLeastGeneralCommonConcept(Collection<IConcept> cc) {

        IConcept ret = null;
        Iterator<IConcept> ii = cc.iterator();

        if (ii.hasNext()) {

            ret = ii.next();

            if (ret != null)
                while (ii.hasNext()) {
                    ret = ret.getLeastGeneralCommonConcept(ii.next());
                    if (ret == null)
                        break;
                }
        }

        return ret;
    }

    /**
     * Arrange a set of concepts into the collection of the most specific members of each
     * concept hierarchy therein.
     * 
     * @param cc
     * @return least general
     */
    @Override
    public Collection<IConcept> getLeastGeneral(Collection<IConcept> cc) {

        Set<IConcept> ret = new HashSet<>();
        for (IConcept c : cc) {
            List<IConcept> ccs = new ArrayList<>(ret);
            boolean set = false;
            for (IConcept kn : ccs) {
                if (c.is(kn)) {
                    ret.remove(kn);
                    ret.add(c);
                    set = true;
                } else if (kn.is(c)) {
                    set = true;
                }
            }
            if (!set) {
                ret.add(c);
            }
        }
        return ret;
    }

    /**
     * True if concept was declared in k.IM at root level. These serve as the "official"
     * least general "family" of concepts for several purposes - e.g. for traits, only
     * these are seen as "general" enough to be used in an "exposes" statement.
     * 
     * 
     * @param tr
     * @return
     */
    public boolean isBaseDeclaration(IConcept tr) {
        return tr.getMetadata().get(NS.BASE_DECLARATION) != null;
    }
    
}
