package org.integratedmodelling.klab;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IRuntimeService;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.NotificationUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

/**
 * Runtime would be a better name for this, but it makes it awkward to code with
 * as it conflicts with Java's Runtime which is imported by default.
 * 
 * @author ferdinando.villa
 *
 */
public enum Klab implements IRuntimeService {

	INSTANCE;

    /**
     * Handler to process classes with k.LAB annotations. Register using
     * {@link #registerAnnotationHandler(Class, AnnotationHandler)}; the handlers will
     * be called for each matching class when {@link #scanPackage(String)} is called.
     * 
     * @author ferdinando.villa
     *
     */
    public interface AnnotationHandler {
        void processAnnotatedClass(Annotation annotation, Class<?> cls) throws KlabException;
    }
    
	private Logger logger;
	private Map<Class<? extends Annotation>, AnnotationHandler> annotationHandlers = new HashMap<>();
	private IMonitor rootMonitor = new RootMonitor();

	private Klab() {

		logger = (Logger) LoggerFactory.getLogger(this.getClass());
		if (logger != null) {
			/*
			 * I'd rather not do this, but 100M of debug output when nobody has ever asked
			 * for it and no logging property files are around to be changed is a bit much
			 * to take.
			 */
			logger.setLevel(Level.INFO);
		}
		rootMonitor = new RootMonitor();
		setupExtensions();
	}

	@Override
	public void info(Object... o) {
		if (logger != null) {
			logger.info(NotificationUtils.getMessage(o));
		} else {
			System.err.println("INFO: " + NotificationUtils.getMessage(o));
		}
	}

	@Override
	public void warn(Object... o) {
		if (logger != null) {
			logger.warn(NotificationUtils.getMessage(o));
		} else {
			System.err.println("WARN: " + NotificationUtils.getMessage(o));
		}
	}

	@Override
	public void error(Object... o) {
		if (logger != null) {
			logger.error(NotificationUtils.getMessage(o));
		} else {
			System.err.println("WARN: " + NotificationUtils.getMessage(o));
		}
	}

	@Override
	public void debug(Object... o) {
		if (logger != null) {
			logger.debug(NotificationUtils.getMessage(o));
		} else {
			System.err.println("WARN: " + NotificationUtils.getMessage(o));
		}
	}

	/**
	 * Register a class annotation and its handler for processing when
	 * {@link #scanPackage(String)} is called.
	 * 
	 * @param annotationClass
	 * @param handler
	 */
	public void registerAnnotationHandler(Class<? extends Annotation> annotationClass, AnnotationHandler handler) {
		annotationHandlers.put(annotationClass, handler);
	}

	/**
	 * Single scanning loop for all registered annotations in a package. Done on the
	 * main codebase and in each component based on the declared packages.
	 * 
	 * @param packageId
	 * @return all annotations found with the corresponding class
	 * @throws KlabException
	 */
	public List<Pair<Annotation, Class<?>>> scanPackage(String packageId) throws KlabException {

		List<Pair<Annotation, Class<?>>> ret = new ArrayList<>();

		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		for (Class<? extends Annotation> ah : annotationHandlers.keySet()) {
			provider.addIncludeFilter(new AnnotationTypeFilter(ah));
		}

		Set<BeanDefinition> beans = provider.findCandidateComponents(packageId);
		for (BeanDefinition bd : beans) {

			for (Class<? extends Annotation> ah : annotationHandlers.keySet()) {
				try {
					Class<?> cls = Class.forName(bd.getBeanClassName());
					Annotation annotation = cls.getAnnotation(ah);
					if (annotation != null) {
						annotationHandlers.get(ah).processAnnotatedClass(annotation, cls);
						ret.add(new Pair<>(annotation, cls));
					}
				} catch (ClassNotFoundException e) {
					error(e);
					continue;
				}
			}
		}

		return ret;
	}

	// functions

	// URNs

	/*
	 * Get extensions (ImageIO, Geotools) optimally configured. Most copied from
	 * Geoserver's GeoserverInitStartupListener
	 */
	private static void setupExtensions() {

		// if the server admin did not set it up otherwise, force X/Y axis
		// ordering
		// This one is a good place because we need to initialize this property
		// before any other opeation can trigger the initialization of the CRS
		// subsystem
		if (System.getProperty("org.geotools.referencing.forceXY") == null) {
			System.setProperty("org.geotools.referencing.forceXY", "true");
		}
		// if (Boolean.TRUE
		// .equals(Hints.getSystemDefault(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER))) {
		// Hints.putSystemDefault(Hints.FORCE_AXIS_ORDER_HONORING, "http");
		// }
		// Hints.putSystemDefault(Hints.LENIENT_DATUM_SHIFT, true);
		//
		// // setup the referencing tolerance to make it more tolerant to tiny
		// // differences
		// // between projections (increases the chance of matching a random prj
		// // file content
		// // to an actual EPSG code
		// double comparisonTolerance = DEFAULT_COMPARISON_TOLERANCE;
		//
		// // Register logging, and bridge to JAI logging
		// GeoTools.init((Hints) null);
		//
		// /*
		// * TODO make this a property and implement if it ever becomes necessary
		// */
		// // if (comparisonToleranceProperty != null) {
		// // try {
		// // comparisonTolerance =
		// // Double.parseDouble(comparisonToleranceProperty);
		// // } catch (NumberFormatException nfe) {
		// // KLAB.warn("Unable to parse the specified COMPARISON_TOLERANCE "
		// // + "system property: " + comparisonToleranceProperty +
		// // " which should be a number. Using Default: " +
		// // DEFAULT_COMPARISON_TOLERANCE);
		// // }
		// // }
		// Hints.putSystemDefault(Hints.COMPARISON_TOLERANCE, comparisonTolerance);
		//
		// /*
		// * avoid expiration of EPSG data. FIXME: does not seem to avoid anything.
		// */
		// System.setProperty("org.geotools.epsg.factory.timeout", "-1");
		//
		// /*
		// * Prevents leak ()
		// */
		// ImageIO.scanForPlugins();
		//
		// // in any case, the native png reader is worse than the pure java ones,
		// // so
		// // let's disable it (the native png writer is on the other side
		// // faster)...
		// ImageIOExt.allowNativeCodec("png", ImageReaderSpi.class, false);
		// ImageIOExt.allowNativeCodec("png", ImageWriterSpi.class, true);
		//
		// // initialize GeoTools factories so that we don't make a SPI lookup
		// // every time a
		// // factory is needed
		// Hints.putSystemDefault(Hints.FILTER_FACTORY, CommonFactoryFinder
		// .getFilterFactory2(null));
		// Hints.putSystemDefault(Hints.STYLE_FACTORY, CommonFactoryFinder
		// .getStyleFactory(null));
		// Hints.putSystemDefault(Hints.FEATURE_FACTORY, CommonFactoryFinder
		// .getFeatureFactory(null));
		//
		// final Hints defHints = GeoTools.getDefaultHints();
		//
		// // Initialize GridCoverageFactory so that we don't make a lookup every
		// // time a
		// // factory is needed
		// Hints.putSystemDefault(Hints.GRID_COVERAGE_FACTORY, CoverageFactoryFinder
		// .getGridCoverageFactory(defHints));
	}

	/**
	 * Register a class as a k.IM toolkit, providing extensions usable from Groovy
	 * or other expression language.
	 * 
	 * @param cls
	 */
	public void registerKimToolkit(Class<?> cls) {
		// TODO Auto-generated method stub

	}

	class RootMonitor implements IMonitor {

		@Override
		public void info(Object... info) {
			INSTANCE.info(info);
		}

		@Override
		public void warn(Object... o) {
			INSTANCE.warn(o);
		}

		@Override
		public void error(Object... o) {
			INSTANCE.error(o);
		}

		@Override
		public void debug(Object... o) {
			INSTANCE.debug(o);
		}

		@Override
		public void send(Object o) {
			// TODO Auto-generated method stub

		}

		@Override
		public IIdentity getIdentity() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasErrors() {
			// TODO Auto-generated method stub
			return false;
		}

	}

	public IMonitor getRootMonitor() {
		return rootMonitor;
	}

}
