package org.integratedmodelling.adapter.datacube;

import java.io.File;

import javax.annotation.Nullable;

import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.integratedmodelling.klab.Configuration;
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
	private String name; 
	private UrnTranslationService urnTranslation;
	private AvailabilityService availability;
	private IngestionService ingestion;
	private CachingService caching;
	private EncodingService encoding;
	private MaintenanceService maintenance;
	private File scratchArea;

	public Datacube(String name, Class<? extends UrnTranslationService> translationServiceClass,
			@Nullable Class<? extends AvailabilityService> availabilityServiceClass,
			@Nullable Class<? extends IngestionService> ingestionServiceClass,
			@Nullable Class<? extends CachingService> cachingServiceClass,
			Class<? extends EncodingService> encodingServiceClass,
			Class<? extends MaintenanceService> maintenanceServiceClass) {
		
		this.name = name;
		this.scratchArea = Configuration.INSTANCE.getDataPath(this.name);
		
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

		maintenance.initialize(this);
		online = maintenance.checkService(this);
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

		Availability checkAvailability(IGeometry geometry, String variable, Datacube datacube);
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
		void setup(Datacube datacube);

		/**
		 * Initialization at each construction of the datacube service.
		 */
		void initialize(Datacube datacube);

		/**
		 * At regular intervals, set through properties
		 */
		void maintain(Datacube datacube);

		/**
		 * Check for the availability of the datacube service. Quickly please.
		 * 
		 * @return
		 */
		boolean checkService(Datacube datacube);

		/**
		 * Before each request
		 */
		void cleanupBefore(Datacube datacube);

		/**
		 * After each request
		 */
		void cleanupAfter(Datacube datacube);
	}
	
	public boolean isOnline() {
		return this.online;
	}
	
	protected File getScratchArea() {
		return scratchArea;
	}
	
	protected void setOnline(boolean b) {
		this.online = b;
	}
}
