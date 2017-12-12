package org.integratedmodelling.klab;

import org.eclipse.xtext.testing.IInjectorProvider;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.integratedmodelling.kdl.kdl.Dataflow;
import org.integratedmodelling.klab.api.services.IDataflowService;
import org.integratedmodelling.klab.utils.xtext.DataflowInjectorProvider;

import com.google.inject.Inject;
import com.google.inject.Injector;

public enum Dataflows implements IDataflowService {

    INSTANCE;
    
    @Inject
    ParseHelper<Dataflow> dataflowParser;
    
    private Dataflows() {
        IInjectorProvider injectorProvider = new DataflowInjectorProvider();
        Injector injector = injectorProvider.getInjector();
        if (injector != null) {
            injector.injectMembers(this);
        }
    }

}
