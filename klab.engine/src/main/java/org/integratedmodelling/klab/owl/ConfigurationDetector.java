//package org.integratedmodelling.klab.owl;
//
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//import org.integratedmodelling.kim.api.IKimConcept.Type;
//import org.integratedmodelling.kim.api.IKimConceptStatement;
//import org.integratedmodelling.klab.Observables;
//import org.integratedmodelling.klab.api.knowledge.IConcept;
//import org.integratedmodelling.klab.api.observations.IDirectObservation;
//import org.integratedmodelling.klab.api.observations.IObservation;
//import org.integratedmodelling.klab.common.LogicalConnector;
//import org.integratedmodelling.klab.components.runtime.observations.DirectObservation;
//import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
//
//public class ConfigurationDetector {
//
//	/**
//	 * These describe the configuration from the worldview, and get notified of all
//	 * the triggers and enablements connected to it by models.
//	 * 
//	 * @author Ferd
//	 *
//	 */
//	class ConfigurationDescriptor {
//		Set<IConcept> triggers = new HashSet<>();
//		LogicalConnector connector = null;
//		IConcept configuration;
//	}
//
//	Map<IConcept, ConfigurationDescriptor> configurations = new HashMap<>();
//
//	/**
//	 * These get recorded in context observations and updated by runtime scopes,
//	 * which should call update() on each of the notified configurations at each new
//	 * observation in each context.
//	 * 
//	 * @author Ferd
//	 *
//	 */
//	public class Configuration {
//		
//		Configuration configuration;
//		Map<IConcept, IObservation> matched = new HashMap<>();
//		boolean covered = false;
//	}
//
//	public void registerConfiguration(IKimConceptStatement statement, IConcept configuration) {
//
//		if (!configuration.isAbstract()) {
//
//			IConcept inherent = Observables.INSTANCE.getInherency(configuration);
//			if (inherent != null) {
//
//				ConfigurationDescriptor descriptor = new ConfigurationDescriptor();
//				descriptor.configuration = configuration;
//
//				if (inherent.is(Type.UNION)) {
//					descriptor.connector = LogicalConnector.UNION;
//					for (IConcept component : inherent.getOperands()) {
//						descriptor.triggers.add(component);
//					}
//				} else if (inherent.is(Type.INTERSECTION)) {
//					descriptor.connector = LogicalConnector.INTERSECTION;
//					for (IConcept component : inherent.getOperands()) {
//						descriptor.triggers.add(component);
//					}
//				} else {
//					descriptor.triggers.add(inherent);
//				}
//
//				this.configurations.put(configuration, descriptor);
//			}
//		}
//	}
//
//	/**
//	 * Call at each new observation (groups for instantiators). If a
//	 * DirectObservation is passed, this will use the cache in it to store partial
//	 * matches and resolve them. If non-null is returned, there is a new
//	 * configuration and nothing is done in the observation except removing any
//	 * partial cache entries and avoiding multiple notifications.
//	 * 
//	 * @param context
//	 * @param newObservation
//	 * @param scope
//	 * @return
//	 */
//	public Configuration detectConfiguration(IDirectObservation context, IObservation newObservation,
//			IRuntimeScope scope) {
//		
//		Map<String, Configuration> cache = null;
//		if (context instanceof DirectObservation) {
//			cache = ((DirectObservation)context).getConfigurationCache();
//		}
//		return null;
//	}
//
//}
