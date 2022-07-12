package org.integratedmodelling.klab.engine.apps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.auth.IIdentity.Type;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IAcknowledgement;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.testing.Assertion;
import org.integratedmodelling.klab.components.geospace.utils.SpatialDisplay;
import org.integratedmodelling.klab.components.runtime.observations.Subject;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;

public class TestRunner implements Annotations.Handler {

	class TestResult {

		IServiceCall assertion;
		boolean ok;
		List<Exception> exceptions = new ArrayList<>();
		String message = "";
		List<String> details = new ArrayList<>();
		long startTime, endTime;

		TestResult(IServiceCall assertion, String message, boolean ok, long startTime) {
			this.assertion = assertion;
			this.message = message;
			this.ok = ok;
			this.startTime = startTime;
			this.endTime = System.currentTimeMillis();
		}
	}

	class TestMonitor extends Engine.Monitor {

		List<TestResult> data = new ArrayList<>();

		int okCount = 0;
		int failCount = 0;

		protected TestMonitor(IMonitor monitor) {
			super((Monitor) monitor);
		}

		public void testError(IServiceCall assertion, Assertion test, long startTime, Object... errors) {
			error("assertion " + assertion.getName() + " failed");
			TestResult result = new TestResult(assertion, "assertion " + assertion.getName() + " succeeded", false,
					System.currentTimeMillis());
			result.details.addAll(test.getDetails());
			data.add(result);
			failCount++;
		}

		/**
		 * Report in reverse
		 * 
		 * @return
		 */
		public List<String> report() {
			List<String> ret = new ArrayList<>();
			for (int i = data.size() - 1; i >= 0; i--) {
				TestResult result = data.get(i);
				for (int d = result.details.size() - 1; d >= 0; d--) {
					ret.add("   " + result.details.get(d));
				}
				ret.add((result.ok ? "SUCCESS: " : "   FAIL: ") + result.assertion.getName() + " on "
						+ new Date(result.startTime) + " [" + (result.endTime - result.startTime) + " ms]");
			}
			return ret;
		}

		public void testOk(IServiceCall assertion, Assertion test, long startTime) {
			info("assertion " + assertion.getName() + " succeeded");
			data.add(new TestResult(assertion, "assertion " + assertion.getName() + " succeeded", true,
					System.currentTimeMillis()));
			okCount++;
		}

		public int failCount() {
			return failCount;
		}

		public int okCount() {
			return okCount;
		}
	}

	@Override
	public Object process(IKimObject target, IParameters<String> arguments, IMonitor monitor) throws Exception {

		/*
		 * TODO each test should run in its own session!
		 */

		// switch monitor for a test monitor that also logs and summarizes
		monitor = new TestMonitor(monitor);

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
					IAcknowledgement observer = (IAcknowledgement) target;
					Session session = monitor.getIdentity().getParentIdentity(Session.class);

					session.getState().resetContext();
					session.getState().resetRoles();

					if (arguments.contains("roles") && arguments.get("roles") instanceof Map) {
						Map<?, ?> roles = arguments.get("roles", Map.class);
						for (Entry<?, ?> role : roles.entrySet()) {
							
							IConcept r = null;
							if (role.getKey() instanceof IKimConcept) {
								r = Concepts.INSTANCE.declare((IKimConcept) role.getKey());
							} else if (role.getKey() instanceof IKimObservable) {
								r = Observables.INSTANCE.declare((IKimObservable) role.getKey(), monitor).getType();
							}

							if (r != null) {
								for (Object o : (role.getValue() instanceof List ? ((List<?>) role.getValue())
										: Collections.singletonList(role.getValue()))) {
									IConcept c = null;
									if (o instanceof IKimConcept) {
										c = Concepts.INSTANCE.declare((IKimConcept) role.getKey());
									} else if (o instanceof IKimObservable) {
										c = Observables.INSTANCE.declare((IKimObservable) role.getKey(), monitor).getType();
									}
									if (c != null) {
										monitor.info("Setting role " + r.getDefinition() + " for " + c.getDefinition());
										session.getState().addRole(r, c);
									}
								}
							}
						}
					}

					if (session != null && observer != null) {
						ISubject subject = (ISubject) session.getState().submit(observer.getName()).get();
						if (subject != null) {
							for (Object o : observations) {
								IArtifact ret = ((Subject)subject).observe(o.toString()).get();
								if (ret == null) {
									monitor.warn(id + ": observation of " + o + " in context " + subject.getName()
											+ " was unsuccessful");
								} else if (ret instanceof IObservation) {
									/*
									 * TODO run any assertion indicated for the observations
									 */
									result.add((IObservation) ret);
								} /* TODO it may be a view, run assertions on that too */
							}
						} else {
							monitor.warn(id + ": observation of " + observer.getName() + " was unsuccessful");
						}

						for (IServiceCall assertion : arguments.get("assertions", new ArrayList<IServiceCall>())) {
							Object test = Extensions.INSTANCE.callFunction(assertion, monitor);
							if (test instanceof Assertion) {
								evaluateAssertion(assertion, (Assertion) test, ((Subject) subject).getScope(),
										(TestMonitor) monitor);
							} else {
								monitor.error(
										"function " + assertion.getName() + " does not produce an assertion: ignored");
							}
						}

						if (subject != null && visualize) {

							if (subject.getScale().getSpace() != null) {
								SpatialDisplay display = new SpatialDisplay(subject.getScale());

								for (IArtifact artifact : ((IRuntimeScope)subject.getScope()).getCatalog().values()) {

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
															? (((IDirectObservation) a).getObservable().getName())
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
							for (Monitor.Listener listener : ((Monitor) monitor).getListeners()) {
								listener.notifyRootContext(subject);
							}
						}

					} else {
						monitor.error(id + ": errors in retrieving observer or session");
					}

					session.getState().resetRoles();

				} catch (Throwable t) {
					monitor.error(t);
				}
			}

			for (String s : ((TestMonitor) monitor).report()) {
				Logging.INSTANCE.info(s);
			}
			Logging.INSTANCE.info(" TEST REPORT: " + ((TestMonitor) monitor).okCount() + " ok, "
					+ ((TestMonitor) monitor).failCount() + " failures");
			Logging.INSTANCE.info("----------------------------------------------------------------------");
			Logging.INSTANCE.info("Finished test " + id + " on " + new Date() + " with "
					+ ((monitor.hasErrors() ? "errors" : "no errors")));

		}

		return result;
	}

	private void evaluateAssertion(IServiceCall assertion, Assertion test, IRuntimeScope runtimeContext,
			TestMonitor monitor) {

		Object o = assertion.getParameters().get("target");
		List<String> targets = new ArrayList<>();
		if (o instanceof List) {
			for (Object t : ((List<?>) o)) {
				targets.add(t.toString());
			}
		} else {
			targets.add(o.toString());
		}

		long startTime = System.currentTimeMillis();
		try {
			if (!test.evaluate(targets, assertion.getParameters(), runtimeContext)) {
				monitor.testError(assertion, test, startTime);
			} else {
				monitor.testOk(assertion, test, startTime);
			}
		} catch (Throwable e) {
			monitor.testError(assertion, test, startTime, e);
		}

	}

}
