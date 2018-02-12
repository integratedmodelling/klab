package org.integratedmodelling.klab;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import org.apache.commons.io.IOUtils;
import org.eclipse.xtext.testing.IInjectorProvider;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.integratedmodelling.kdl.api.IKdlDataflow;
import org.integratedmodelling.kdl.kdl.Model;
import org.integratedmodelling.kdl.model.Kdl;
import org.integratedmodelling.klab.api.services.IDataflowService;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.utils.xtext.DataflowInjectorProvider;
import com.google.inject.Inject;
import com.google.inject.Injector;

public enum Dataflows implements IDataflowService {

    INSTANCE;

    @Inject
    ParseHelper<Model> dataflowParser;

    private Dataflows() {
        IInjectorProvider injectorProvider = new DataflowInjectorProvider();
        Injector injector = injectorProvider.getInjector();
        if (injector != null) {
            injector.injectMembers(this);
        }
    }
    
    @Override
    public IKdlDataflow declare(URL url) throws KlabException {
        try (InputStream stream = url.openStream()) {
            return declare(stream);
        } catch (Exception e) {
            throw new KlabIOException(e);
        }
    }    
    
    @Override
    public IKdlDataflow declare(File file) throws KlabException {
        try (InputStream stream = new FileInputStream(file)) {
            return declare(stream);
        } catch (Exception e) {
            throw new KlabIOException(e);
        }
    }
    
    @Override
    public IKdlDataflow declare(InputStream file) throws KlabValidationException {
        IKdlDataflow ret = null;
        try {
            String definition = IOUtils.toString(file); 
            Model model = dataflowParser.parse(definition);
            ret = Kdl.INSTANCE.declare(model);
        } catch (Exception e) {
            throw new KlabValidationException(e);
        }
        return ret;
    }

    /**
     * Compile a successful resolution into a runnable dataflow.
     * 
     * @param scope
     * @return a dataflow, possibly empty.
     * @throws KlabException 
     */
    @Override
    public Dataflow compile(ResolutionScope scope) throws KlabException {
      // TODO Auto-generated method stub
      return null;
    }

}
