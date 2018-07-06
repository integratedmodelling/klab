package org.integratedmodelling.klab.engine.apps;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.auth.IIdentity.Type;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.utils.SpatialDisplay;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.engine.runtime.Session;

public class TestRunner implements Annotations.Handler {

	@Override
	public Object process(IKimObject target, IParameters<Object> arguments, IMonitor monitor) throws Exception {

		String id = arguments.get("name", "unnamed test");
		int repetitions = arguments.get("repeat", 1);

		if (!(arguments.get("observations") instanceof List)) {
			monitor.warn("test annotation does not specify observations");
			return null;
		}

		if (arguments.contains("resources")) {
			boolean ok = true;
			for (Object o : arguments.get("resources", List.class)) {
				IResource resource = Resources.INSTANCE.resolveResource(o.toString());
				if (resource == null || !Resources.INSTANCE.isResourceOnline(resource)) {
					monitor.warn("resource " + o + " is not available: canceling test " + id);
					ok = false;
				}
			}
			if (!ok) {
				return null;
			}
		}

		boolean visualize = arguments.get("visualize", false)
				|| (System.getProperty("visualize") != null && !System.getProperty("visualize").equals("false"));

		List<IObservation> result = new ArrayList<>();
		List<Throwable> exceptions = new ArrayList<>();
		List<?> observations = (List<?>) arguments.get("observations");
		/* only run the context if we are in a script */
		if (monitor.getIdentity().is(Type.SCRIPT)) {

			for (int i = 0; i < repetitions; i++) {

				try {
					Logging.INSTANCE.info("----------------------------------------------------------------------");
					Logging.INSTANCE.info(
							"Running test " + id + " on " + new Date() + (repetitions == 1 ? "" : (" [#" + i + "]")));
					Logging.INSTANCE.info("----------------------------------------------------------------------");

					// safe cast as the annotation is limited to observers
					IObserver observer = (IObserver) target;
					Session session = monitor.getIdentity().getParentIdentity(Session.class);

					if (session != null && observer != null) {
						ISubject subject = session.observe(observer.getName()).get();
						if (subject != null) {
							for (Object o : observations) {
								IObservation ret = subject.observe(o.toString()).get();
								if (ret == null) {
									monitor.warn(id + ": observation of " + o + " in context " + subject.getName()
											+ " was unsuccessful");
								} else {
									/*
									 * TODO run any assertion indicated for the observations
									 */
									result.add(ret);
								}
							}
							/*
							 * TODO run any assertion indicated for the subject
							 */
						} else {
							monitor.warn(id + ": observation of " + observer.getName() + " was unsuccessful");
						}

						for (IServiceCall assertion : arguments.get("assertions", new ArrayList<IServiceCall>())) {
							// TODO check assertion
						}

						if (subject != null && visualize) {

							if (subject.getScale().getSpace() != null) {
								SpatialDisplay display = new SpatialDisplay(subject.getScale().getSpace());

								for (IArtifact artifact : subject.getProvenance().getArtifacts()) {

									if (artifact instanceof IState) {
										display.add((IState) artifact);
									} else {
										String layerName = null;
										for (IArtifact a : artifact) {
											if (a instanceof IDirectObservation
													&& a.getGeometry().getDimension(Dimension.Type.SPACE) != null) {
												if (layerName == null) {
													int gsize = a.groupSize();
													layerName = gsize == 1
															? (((IDirectObservation) a).getObservable().getLocalName())
															: (Concepts.INSTANCE.getDisplayName(
																	((IDirectObservation) a).getObservable().getType())
																	+ " [" + gsize + "]");
												}
												display.add(((IDirectObservation) a).getScale().getSpace(), layerName);
											}
										}
									}
								}

								display.show();
								if (visualize && (System.getProperty("waitForKey") == null
										|| !System.getProperty("waitForKey").equals("false"))) {
									// block to see the display
									System.out.print("Press a key to continue...");
									System.in.read();
								}
							}
						}

				        if (subject != null && monitor instanceof Monitor) {
				        	for (Monitor.Listener listener : ((Monitor)monitor).getListeners()) {
				        		listener.notifyRootContext(subject);
				        	}
				        }

					} else {
						monitor.error(id + ": errors in retrieving observer or session");
					}
				} catch (Throwable t) {
					exceptions.add(t);
				}
			}

			Logging.INSTANCE.info("Finished test " + id + " on " + new Date() + " with "
					+ ((monitor.hasErrors() || exceptions.size() > 0) ? "errors" : "no errors"));

			for (Throwable t : exceptions) {
				Logging.INSTANCE.error("Exception running test " + id + ": " + t.getMessage());
			}

		}

		return result;
	}

}
