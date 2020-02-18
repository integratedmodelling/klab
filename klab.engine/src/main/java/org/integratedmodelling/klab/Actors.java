package org.integratedmodelling.klab;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.eclipse.xtext.testing.IInjectorProvider;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.kactors.Model;
import org.integratedmodelling.kactors.model.KActors;
import org.integratedmodelling.klab.api.services.IActorsService;
import org.integratedmodelling.klab.api.services.IDataflowService;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.xtext.DataflowInjectorProvider;

import com.google.inject.Inject;
import com.google.inject.Injector;

public enum Actors implements IActorsService {

	INSTANCE;
	
	@Inject
	ParseHelper<Model> dataflowParser;

	private Actors() {
		IInjectorProvider injectorProvider = new DataflowInjectorProvider();
		Injector injector = injectorProvider.getInjector();
		if (injector != null) {
			injector.injectMembers(this);
		}
		Services.INSTANCE.registerService(this, IDataflowService.class);
	}

	@Override
	public IKActorsBehavior declare(URL url) throws KlabException {
		try (InputStream stream = url.openStream()) {
			return declare(stream);
		} catch (Exception e) {
			throw new KlabIOException(e);
		}
	}

	@Override
	public IKActorsBehavior declare(File file) throws KlabException {
		try (InputStream stream = new FileInputStream(file)) {
			return declare(stream);
		} catch (Exception e) {
			throw new KlabIOException(e);
		}
	}

	@Override
	public IKActorsBehavior declare(InputStream file) throws KlabValidationException {
		IKActorsBehavior ret = null;
		try {
			String definition = IOUtils.toString(file);
			Model model = dataflowParser.parse(definition);
			ret = KActors.INSTANCE.declare(model);
		} catch (Exception e) {
			throw new KlabValidationException(e);
		}
		return ret;
	}
	
}
