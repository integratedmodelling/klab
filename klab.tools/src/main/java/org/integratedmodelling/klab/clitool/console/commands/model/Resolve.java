package org.integratedmodelling.klab.clitool.console.commands.model;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Models;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Reasoner;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.services.IModelService.IRankedModel;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.geocoding.Geocoder;
import org.integratedmodelling.klab.components.geospace.geocoding.Geocoder.Location;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.StringUtils;

public class Resolve implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) throws KlabValidationException {

		String declaration = StringUtils.join((List<?>) call.getParameters().get("arguments"), ' ').trim();
		String ctx = call.getParameters().get("context", String.class);
		String spc = call.getParameters().get("space", String.class);
		String tim = call.getParameters().get("time", String.class);

		List<IExtent> extents = new ArrayList<>();
		if (spc != null) {
			List<Location> location = Geocoder.INSTANCE.lookup(spc);
			if (location.size() > 0) {
				session.getMonitor().info("Choosing geocoded match 1/" + location.size() + ": "
						+ location.get(0).getName() + " " + location.get(0).getBoundingbox());
				extents.add(Shape.create(location.get(0).getBoundingbox().get(0),
						location.get(0).getBoundingbox().get(1), location.get(2).getBoundingbox().get(2),
						location.get(0).getBoundingbox().get(3), Projection.getLatLon()));
			} else {
				session.getMonitor().warn("No geolocation matches for " + spc);
			}
		}

		if (tim != null) {
			if (NumberUtils.encodesInteger(tim)) {
				// year
				extents.add(Time.create(Integer.parseInt(tim)));
			} else {
				throw new KlabUnsupportedFeatureException("Only full years are supported for time at the moment.");
			}
		}
		
		IScale scale = null;
		if (extents.size() > 0) {
			scale = Scale.create(extents);
		}

		IConcept context = null;
		if (ctx != null) {
			context = Concepts.c(ctx);
		}

		IObservable observable = null;
		IConcept concept = null;
		if (declaration.startsWith("k:")) {
			concept = Reasoner.INSTANCE.getOntology().getConcept(declaration.substring(2));
		}
		if (concept == null) {
			observable = Observables.INSTANCE.declare(declaration);
		}

		ResolutionScope scope = ResolutionScope.create(session.getMonitor(), scale);
		if (context != null) {
			scope = scope.getContextualizedScope(Observable.promote(context));
		}

		String ret = "";
		int n = 1;

		for (IRankedModel model : Models.INSTANCE.resolve(observable, scope)) {
			ret += (n++) + ". " + model + "\n";
			for (String key : model.getRanks().keySet()) {
				ret += "   " + key + ": " + model.getRanks().get(key) + "\n";
			}
		}

		return ret;
	}

}
