package org.integratedmodelling.adapter.datacube;

import javax.annotation.Nullable;

import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.data.IGeometry;

/**
 * Implement this to make a (k.LAB-extended) datacube out of any datathing.
 * Microservice-flavored because of boredom: just implements the basic
 * interaction between services. Ideal to provide the backend of a UrnAdapter,
 * which, not surprisingly, can be found in generic form in this same package.
 * 
 * @author Ferd
 *
 */
public class Datacube {

	private boolean online = false;

	private UrnTranslationService urnTranslation;
	private AvailabilityService availability;
	private IngestionService ingestion;
	private CachingService caching;
	private EncodingService encoding;
	private MaintenanceService maintenance;

	public Datacube(Class<? extends UrnTranslationService> translationServiceClass,
			@Nullable Class<? extends AvailabilityService> availabilityServiceClass,
			@Nullable Class<? extends IngestionService> ingestionServiceClass,
			@Nullable Class<? extends CachingService> cachingServiceClass,
			Class<? extends EncodingService> encodingServiceClass,
			Class<? extends MaintenanceService> maintenanceServiceClass) {

		try {
			urnTranslation = ConstructorUtils.invokeConstructor(translationServiceClass);
			encoding = ConstructorUtils.invokeConstructor(encodingServiceClass);
			maintenance = ConstructorUtils.invokeConstructor(maintenanceServiceClass);
			if (availabilityServiceClass != null) {
				availability = ConstructorUtils.invokeConstructor(availabilityServiceClass);
			}
			if (ingestionServiceClass != null) {
				ingestion = ConstructorUtils.invokeConstructor(ingestionServiceClass);
			}
			if (cachingServiceClass != null) {
				caching = ConstructorUtils.invokeConstructor(cachingServiceClass);
			}
			online = true;
		} catch (Throwable e) {
			Logging.INSTANCE.error("cannot initialize datacube services: datacube is offline");
		}

		maintenance.initialize();
		online = maintenance.checkService();

	}

	/**
	 * Validates and translates the URN to the desired temporal, spatial and logical
	 * coordinates for the data.
	 * 
	 * @author Ferd
	 *
	 */
	public interface UrnTranslationService {

	}

	/**
	 * Worries about the availability of the data, procuring them as needed and
	 * returning an estimation of the likely time to availability.
	 * 
	 * @author Ferd
	 *
	 */
	public interface AvailabilityService {

		enum Availability {
			IMMEDIATE, DELAYED, NONE
		}

		Availability checkAvailability(IGeometry geometry, String variable);
	}

	/**
	 * Ingests data when their availability is DELAYED.
	 * 
	 * @author Ferd
	 *
	 */
	public interface IngestionService {
	}

	/**
	 * Caches the data after ingestion to ensure future availability.
	 * 
	 * @author Ferd
	 *
	 */
	public interface CachingService {

	}

	/**
	 * Encodes the data to a IKlabData.Builder when availability is confirmed.
	 * 
	 * @author Ferd
	 *
	 */
	public interface EncodingService {
	}

	/**
	 * Maintains the datacube.
	 */
	public interface MaintenanceService {

		/**
		 * One-time setup if never done, or on demand
		 */
		void setup();

		/**
		 * Initialization at each construction of the datacube service.
		 */
		void initialize();

		/**
		 * At regular intervals, set through properties
		 */
		void maintain();

		/**
		 * Check for the availability of the datacube service. Quickly please.
		 * 
		 * @return
		 */
		boolean checkService();

		/**
		 * Before each request
		 */
		void cleanupBefore();

		/**
		 * After each request
		 */
		void cleanupAfter();
	}
}
