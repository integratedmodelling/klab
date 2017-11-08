package org.integratedmodelling.klab;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.resource.IResourceFactory;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.LazyStringInputStream;
import org.integratedmodelling.kim.KnowledgeDeclarationStandaloneSetup;
import org.integratedmodelling.kim.kdecl.ConceptDeclaration;
import org.integratedmodelling.kim.kdecl.ObservableSemantics;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.KimObservable;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IProperty;
import org.integratedmodelling.klab.api.services.IConceptService;

import com.google.inject.Injector;

public enum Concepts implements IConceptService {

    INSTANCE;

    private Injector         injector;
    private IResourceFactory resourceFactory;

    private Concepts() {
    }
    
    public IConcept declare(String declaration) {

        IConcept ret = null;

        if (injector == null) {
            injector = KnowledgeDeclarationStandaloneSetup.doSetup();
            resourceFactory = injector.getInstance(IResourceFactory.class);
        }

        try (InputStream input = new LazyStringInputStream(declaration)) {

            XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
            XtextResource resource = (XtextResource) resourceFactory
                    .createResource(URI.createURI("klab://" + UUID.randomUUID()));
            resourceSet.getResources().add(resource);
            resource.load(input, null);

            EcoreUtil.resolveAll(resource);

            if (!resource.getParseResult().hasSyntaxErrors()) {
                if (resource.getParseResult() instanceof ObservableSemantics) {
                    KimObservable obs = Kim
                            .declareObservable((ObservableSemantics) resource.getParseResult());
                    ret = declare(obs);
                }
            }
        } catch (IOException e) {
            // just return null
        }
        return ret;
    }

    private IConcept declare(KimObservable obs) {
        // TODO Auto-generated method stub
        return null;
    }

    private IConcept declare(ConceptDeclaration obs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IProperty getProperty(String propertyId) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public IConcept getConcept(String conceptId) {
        // TODO Auto-generated method stub
        return null;
    }

}
