package org.integratedmodelling.klab;

import org.eclipse.xtext.testing.IInjectorProvider;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.integratedmodelling.kim.kdecl.ConceptDeclaration;
import org.integratedmodelling.kim.kdecl.ObservableSemantics;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.KimObservable;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IProperty;
import org.integratedmodelling.klab.api.services.IConceptService;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.utils.xtext.KnowledgeDeclarationInjectorProvider;

import com.google.inject.Inject;
import com.google.inject.Injector;

public enum Concepts implements IConceptService {

    INSTANCE;
	
	@Inject
	ParseHelper<ObservableSemantics> observableParser;

	@Inject
	ParseHelper<ConceptDeclaration> declarationParser;

    private Concepts() {
    	IInjectorProvider injectorProvider = new KnowledgeDeclarationInjectorProvider();
		Injector injector = injectorProvider.getInjector();
		if (injector != null) {
			injector.injectMembers(this);
		}
    }
    
    /*
     * TODO use InjectWith and provide a similar setup to the testing in kim.tests to obtain
     * a reusable parser instead of injecting all this stuff at every declaration.
     */
    @Override
    public IConcept declare(String declaration) {

    	try {
			ObservableSemantics parsed = observableParser.parse(declaration);
			KimObservable interpreted = Kim.INSTANCE.declareObservable(parsed);
			return declare(interpreted);
		} catch (Exception e) {
		}
        
		return null;
    }

    private IConcept declare(KimObservable obs) {
        // TODO Auto-generated method stub
		System.out.println(obs.toString());
        return null;
    }

    private IConcept declare(ConceptDeclaration obs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IProperty getProperty(String propertyId) {
        return OWL.INSTANCE.getProperty(propertyId);
    }
    
    @Override
    public IConcept getConcept(String conceptId) {
        return OWL.INSTANCE.getConcept(conceptId);
    }
    
    /**
     * Quick static way to obtain a concept that is known to exist. Throws
     * an unchecked exception if the concept isn't found.
     * 
     * @param conceptId
     * @return
     */
    public static IConcept c(String conceptId) {
        
        if (conceptId == null || conceptId.isEmpty()) {
            return null;
        }

        IConcept ret = OWL.INSTANCE.getConcept(conceptId);
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
     * @return
     */
    public static IProperty p(String propertyId) {

        if (propertyId == null || propertyId.isEmpty()) {
            return null;
        }

        IProperty ret = OWL.INSTANCE.getProperty(propertyId);
        if (ret == null) {
            throw new KlabRuntimeException("cannot find property " + propertyId);
        }
        return ret;

    }

}
