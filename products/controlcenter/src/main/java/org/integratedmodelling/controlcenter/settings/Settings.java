package org.integratedmodelling.controlcenter.settings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.integratedmodelling.controlcenter.ControlCenter;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.services.IConfigurationService;

import com.dlsc.formsfx.model.validators.CustomValidator;
import com.dlsc.preferencesfx.PreferencesFx;
import com.dlsc.preferencesfx.model.Category;
import com.dlsc.preferencesfx.model.Group;
import com.dlsc.preferencesfx.model.Setting;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class Settings {

	private boolean actionReady = false;

	public PreferencesFx preferencesFx;

	private ListProperty<String> buildItems = new SimpleListProperty<>(
			FXCollections.observableArrayList(Arrays.asList("Latest")));
	private ListProperty<String> parallelismStrategies = new SimpleListProperty<>(
			FXCollections.observableArrayList(Arrays.asList("Aggressive", "Standard", "Disabled")));

	private BooleanProperty startWithCLI = new SimpleBooleanProperty(true);
	private BooleanProperty useDevelop = new SimpleBooleanProperty(false);
	private BooleanProperty swichBranches = new SimpleBooleanProperty(false);
	private BooleanProperty detectLocalHub = new SimpleBooleanProperty(false);
	// TODO implement install4j integration
	// private BooleanProperty checkForCCUpdates = new SimpleBooleanProperty(true);
	private BooleanProperty resetAllBuilds = new SimpleBooleanProperty(false);
	private BooleanProperty resetAllBuildsButLatest = new SimpleBooleanProperty(false);
	private BooleanProperty updateAutomatically = new SimpleBooleanProperty(false);
	private BooleanProperty resetKnowledge = new SimpleBooleanProperty(false);
	private BooleanProperty resetModelerWorkspace = new SimpleBooleanProperty(false);
	private IntegerProperty buildsToKeep = new SimpleIntegerProperty(7);
	private IntegerProperty maxMemory = new SimpleIntegerProperty(2048);
	private IntegerProperty checkIntervalKlabUpdates = new SimpleIntegerProperty(1);
	private IntegerProperty sessionIdleMaximum = new SimpleIntegerProperty(7);
	private IntegerProperty maxLocalSessions = new SimpleIntegerProperty(10);
	private IntegerProperty maxRemoteSessions = new SimpleIntegerProperty(0);
	private IntegerProperty maxSessionsPerUser = new SimpleIntegerProperty(3);
	private IntegerProperty enginePort = new SimpleIntegerProperty(IConfigurationService.DEFAULT_ENGINE_PORT);
	private BooleanProperty useUTMProjection = new SimpleBooleanProperty(false);
	private BooleanProperty useGeocoding = new SimpleBooleanProperty(true);
	private IntegerProperty localResourceValidationInterval = new SimpleIntegerProperty(10);
	private IntegerProperty publicResourceValidationInterval = new SimpleIntegerProperty(10);
	private BooleanProperty revalidatePublicResources = new SimpleBooleanProperty(false);
	private BooleanProperty revalidateLocalResources = new SimpleBooleanProperty(false);
	private IntegerProperty maxPolygonCoordinates = new SimpleIntegerProperty(1000);
	private IntegerProperty maxPolygonSubdivisions = new SimpleIntegerProperty(2000);
	private BooleanProperty useNanosecondResolution = new SimpleBooleanProperty(false);
	private ObjectProperty<String> parallelismStrategy = new SimpleObjectProperty<>("Standard");
	private BooleanProperty useInMemoryStorage = new SimpleBooleanProperty(false);
	private BooleanProperty resolveModelsFromNetwork = new SimpleBooleanProperty(true);
	private BooleanProperty visualizeResolutionGraphs = new SimpleBooleanProperty(false);
	private BooleanProperty visualizeSpatialDebuggingAids = new SimpleBooleanProperty(false);
	private BooleanProperty resolveObservationsFromNetwork = new SimpleBooleanProperty(false);
	private BooleanProperty loadRemoteContext = new SimpleBooleanProperty(false);
	private ObjectProperty<File> workDirectory = new SimpleObjectProperty<>(
			new File(System.getProperty("user.home") + File.separator + ".klab"));
	private ObjectProperty<File> workspaceDirectory = new SimpleObjectProperty<>(
			new File(System.getProperty("user.home") + File.separator + ".klab" + File.separator + "workspace"));
	private ObjectProperty<File> productDirectory = new SimpleObjectProperty<>(
			new File(System.getProperty("user.home") + File.separator + ".klab" + File.separator + "products"));
	private ObjectProperty<File> exportDirectory = new SimpleObjectProperty<>(
			new File(System.getProperty("user.home") + File.separator + ".klab" + File.separator + "export"));
	private ObjectProperty<File> certFile = new SimpleObjectProperty<>(
			new File(System.getProperty("user.home") + File.separator + ".klab" + File.separator + "klab.cert"));
	private ObjectProperty<File> tempDirectory = new SimpleObjectProperty<>(getTemporaryPath());

	private DoubleProperty minModelCoverage = new SimpleDoubleProperty(0.01);
	private DoubleProperty minTotalCoverage = new SimpleDoubleProperty(0.95);
	private DoubleProperty minCoverageImprovement = new SimpleDoubleProperty(0.2);

	private BooleanProperty useDebugParameters = new SimpleBooleanProperty(false);
	private BooleanProperty deleteTempStorage = new SimpleBooleanProperty(true);

	private StringProperty googleApiKey = new SimpleStringProperty("");
	private StringProperty bingApiKey = new SimpleStringProperty("");
	private StringProperty mapboxLayerURL = new SimpleStringProperty("");
	private StringProperty mapboxLayerName = new SimpleStringProperty("");
	private StringProperty mapboxLayerAttribution = new SimpleStringProperty("");
	private StringProperty authenticationEndpoint = new SimpleStringProperty("");

	/**
	 * If true, we want to use the develop branch of everything.
	 * 
	 * @return
	 */
	public boolean useDevelop() {
		return useDevelop.get();
	}
	
	public boolean swichBranches() {
	    return swichBranches.get();
	}

	public File getCertificateFile() {
		return certFile.get();
	}

	public File getWorkDirectory() {
		return workDirectory.get();
	}

	public File getExportDirectory() {
		return workDirectory.get();
	}

	public File getProductDirectory() {
		return productDirectory.get();
	}

	public String getAuthenticationEndpoint() {
		return authenticationEndpoint.get();
	}

	public int getProductUpdateInterval() {
		return checkIntervalKlabUpdates.get();
	}

	public int getMaxEngineMemory() {
		return maxMemory.get();
	}

	public boolean useDebug() {
		return useDebugParameters.get();
	}

	public int getEnginePort() {
		return enginePort.get();
	}

	public int getMaxSessions() {
		return maxLocalSessions.get();
	}

	public File getKlabWorkspace() {
		return workspaceDirectory.get();
	}
	
	public int buildsToKeep() {
	    return buildsToKeep.intValue();
	}

	public boolean resetAllBuildsButLatest() {
	    return resetAllBuildsButLatest.get();
	}
	public Settings() {
		preferencesFx = createPreferences();
	}

	public PreferencesFx getPreferences() {
		return preferencesFx;
	}

	/*
	 * trick to attach immediate actions to settings changes
	 */
	public static class Action<T> extends CustomValidator<T> {

		protected Action(Consumer<T> action) {
			super(new Predicate<T>() {

				@Override
				public boolean test(T t) {
					action.accept(t);
					return true;
				}
			}, null);
		}

	}

	/*
	 * Actions that tie a setting to a k.LAB property in the property file.
	 */
	public static class PropertyAction<T> extends CustomValidator<T> {

		protected PropertyAction(String klabProperty) {
			super(new Predicate<T>() {
				@Override
				public boolean test(T t) {
					Configuration.INSTANCE.getProperties().setProperty(klabProperty, t.toString());
					Configuration.INSTANCE.save();
					return true;
				}
			}, null);
		}
		
		protected PropertyAction(String klabProperty, Consumer<T> action) {
			super(new Predicate<T>() {
				@Override
				public boolean test(T t) {
					Configuration.INSTANCE.getProperties().setProperty(klabProperty, t.toString());
					Configuration.INSTANCE.save();
					action.accept(t);
					return true;
				}
			}, null);
		}

		protected PropertyAction(String klabProperty, Consumer<T> action, Function<T, Boolean> validator) {
			super(new Predicate<T>() {
				@Override
				public boolean test(T t) {
					if (validator.apply(t)) {
						Configuration.INSTANCE.getProperties().setProperty(klabProperty, t.toString());
						Configuration.INSTANCE.save();
						action.accept(t);
						return true;
					}
					return false;
				}
			}, null);
		}

	}

	public PreferencesFx createPreferences() {

		return PreferencesFx.of(Settings.class,

				Category.of("Control Center", Group.of("General preferences",
						Setting.of("Use developer stack", useDevelop).validate(new Action<Boolean>((b) -> {
							if (isActionReady())
								ControlCenter.INSTANCE.changeStack(b);
						})),
						Setting.of("Synchronize project branch with runtime", swichBranches).validate(new Action<Boolean>((b) -> {
                            if (isActionReady())
                                preferencesFx.saveSettings();
                        })),
						Setting.of("Number of builds to keep", buildsToKeep),
						// TODO: check if we need to implement in install4j
						// Setting.of("Check for Control Center updates on launch", checkForCCUpdates),
						Setting.of("Check interval for k.LAB updates (minutes)", checkIntervalKlabUpdates),
						Setting.of("Update k.LAB automatically", updateAutomatically)),
						Group.of("Installed k.LAB distributions",
								Setting.of("Delete all builds except latest", resetAllBuildsButLatest),
								Setting.of("Delete all builds installed", resetAllBuilds))),
			
				Category.of("Paths", Setting.of("k.LAB work directory", workDirectory, true),
						Setting.of("Binary products path", productDirectory, true),
						Setting.of("k.LAB project workspace", workspaceDirectory, true),
						Setting.of("Default export path", exportDirectory, true),
						Setting.of("Default temporary file path", tempDirectory, true)),

				Category.of("Account", Setting.of("Certificate file", certFile, false)),

				Category.of("Engine",

						Setting.of("Start with command window", startWithCLI),
						Setting.of("Max RAM occupation (MB)", maxMemory),
						Setting.of("Engine port (default 8283)", enginePort)

				).subCategories(

						Category.of("Runtime",
								Setting.of("Minutes idle before session disposal (0 = never)", sessionIdleMaximum),
								Setting.of("Maximum concurrent local sessions", maxLocalSessions),
								Setting.of("Maximum remote sessions", maxRemoteSessions),
								Setting.of("Maximum concurrent sessions per user", maxSessionsPerUser),
								Setting.of("Parallelism strategy", parallelismStrategies, parallelismStrategy)),
						Category.of("Space and time", Group.of("Coverage",
								Setting.of("Minimum model coverage (0-1: default 0.01)", minModelCoverage, 0.0, 1.0, 2),
								Setting.of("Minimum coverage improvement (0-1: default 0.2)", minCoverageImprovement,
										0.0, 1.0, 2),
								Setting.of("Minimum total coverage (0-1: default 0.95)", minTotalCoverage, 0.0, 1.0, 2))
								.description("Context coverage in resolution"),
								Group.of("Spatial visualization and projection",
										Setting.of("Use geocoding for names", useGeocoding),
										Setting.of("Use UTM projection for location", useUTMProjection),
										Setting.of("Maximum number of visualized coordinates", maxPolygonCoordinates),
										Setting.of("Maximum subdivisions of the diagonal", maxPolygonSubdivisions)),
								Group.of("Temporal precision",
										Setting.of("Use nanosecond resolution", useNanosecondResolution))),
						Category.of("Connectivity",

								Setting.of("Resolve models from k.LAB network", resolveModelsFromNetwork),
								Setting.of("Resolve observations from k.LAB network", resolveObservationsFromNetwork),
								Setting.of("Allow setting remote contexts from URN", loadRemoteContext)
										.validate(new Action<Boolean>((b) -> {
											if (isActionReady())
												System.out.println("porcoddio e' " + b);
										}))

						), Category.of("External APIs",

								Group.of("Google Maps", Setting.of("Google API key", googleApiKey)),
								Group.of("Bing Maps", Setting.of("Bing API key", bingApiKey)),
								Group.of("Custom MapBox layer", Setting.of("Name", mapboxLayerName),
										Setting.of("URL", mapboxLayerURL),
										Setting.of("Attribution", mapboxLayerAttribution))

						),
						Category.of("Resources",
								Group.of("Resource validation",
										Setting.of("Local resource validation interval (minutes)",
												localResourceValidationInterval),
										Setting.of("Public resource validation interval (minutes)",
												publicResourceValidationInterval),
										Setting.of("Revalidate local resources now", revalidateLocalResources),
										Setting.of("Revalidate public resources now", revalidatePublicResources)))

				),

				Category.of("Expert settings",
						Group.of("Cached knowledge",
								Setting.of("Reset synchronized knowledge (requires restart)", resetKnowledge),
								Setting.of("Reset k.Modeler workspace (requires restart)", resetModelerWorkspace)),
						Group.of("Testing settings",
								Setting.of("Delete leftover temporary storage on startup", deleteTempStorage),
								Setting.of("Alternate authentication endpoint", authenticationEndpoint)),
						Group.of("Debugging settings",
								Setting.of("Detect and use local hub if available", detectLocalHub),
								Setting.of("Use in-memory storage (debug only!)", useInMemoryStorage),
								Setting.of("Visualize resolution graphs", visualizeResolutionGraphs),
								Setting.of("Visualize spatial debugging aids", visualizeSpatialDebuggingAids),
								Setting.of("Remote debug engine configuration (port 8000)", useDebugParameters))))

				.persistWindowState(false).saveSettings(true).debugHistoryMode(true).buttonsVisibility(true);
	}

	public List<String> newArrayList(String... strings) {
		List<String> ret = new ArrayList<>();
		for (String s : strings) {
			ret.add(s);
		}
		return ret;
	}

	public static File getTemporaryPath() {
		try {
			File temp = File.createTempFile("temp-file-name", ".tmp");
			String absolutePath = temp.getAbsolutePath();
			String tempFilePath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
			return new File(tempFilePath);
		} catch (IOException e) {
		    System.err.println(e);
		}
		return new File(System.getProperty("user.home") + File.separator + ".klab" + File.separator + ".scratch");
	}

	public boolean isActionReady() {
		return actionReady;
	}

	public void setActionReady(boolean actionReady) {
		this.actionReady = actionReady;
	}

}
