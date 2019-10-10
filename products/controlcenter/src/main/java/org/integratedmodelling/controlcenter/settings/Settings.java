package org.integratedmodelling.controlcenter.settings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	public PreferencesFx preferencesFx;

	private ListProperty<String> buildItems = new SimpleListProperty<>(
			FXCollections.observableArrayList(Arrays.asList("Latest")));
	private ListProperty<String> parallelismStrategies = new SimpleListProperty<>(
			FXCollections.observableArrayList(Arrays.asList("Aggressive", "Standard", "Disabled")));

	private BooleanProperty useDevelop = new SimpleBooleanProperty(false);
	private BooleanProperty detectLocalHub = new SimpleBooleanProperty(false);
	private BooleanProperty showReleaseNotes = new SimpleBooleanProperty(false);
	private BooleanProperty updateAutomatically = new SimpleBooleanProperty(false);
	private IntegerProperty buildsToKeep = new SimpleIntegerProperty(1);
	private IntegerProperty maxMemory = new SimpleIntegerProperty(2048);
	private IntegerProperty sessionIdleMaximum = new SimpleIntegerProperty(90);
	private IntegerProperty maxLocalSessions = new SimpleIntegerProperty(10);
	private IntegerProperty maxRemoteSessions = new SimpleIntegerProperty(0);
	private IntegerProperty maxSessionsPerUser = new SimpleIntegerProperty(3);
	private IntegerProperty enginePort = new SimpleIntegerProperty(8283);
	private BooleanProperty useUTMProjection = new SimpleBooleanProperty(false);
	private BooleanProperty useGeocoding = new SimpleBooleanProperty(true);
	private IntegerProperty maxPolygonCoordinates = new SimpleIntegerProperty(1000);
	private IntegerProperty maxPolygonSubdivisions = new SimpleIntegerProperty(2000);
	private BooleanProperty useNanosecondResolution = new SimpleBooleanProperty(false);
	private ObjectProperty<String> buildSelection = new SimpleObjectProperty<>("Latest");
	private ObjectProperty<String> parallelismStrategy = new SimpleObjectProperty<>("Standard");
	private BooleanProperty useInMemoryStorage = new SimpleBooleanProperty(false);
	private BooleanProperty resolveModelsFromNetwork = new SimpleBooleanProperty(true);
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

	public Settings() {
		preferencesFx = createPreferences();
	}

	public PreferencesFx getPreferences() {
		return preferencesFx;
	}

	/*
	 * // General StringProperty welcomeText = new
	 * SimpleStringProperty("Hello World"); BooleanProperty nightMode = new
	 * SimpleBooleanProperty(true);
	 * 
	 * // Screen DoubleProperty scaling = new SimpleDoubleProperty(1);
	 * StringProperty screenName = new
	 * SimpleStringProperty("PreferencesFx Monitor");
	 * 
	 * ObservableList<String> resolutionItems = FXCollections
	 * .observableArrayList(Arrays.asList("1024x768", "1280x1024", "1440x900",
	 * "1920x1080")); ObjectProperty<String> resolutionSelection = new
	 * SimpleObjectProperty<>("1024x768");
	 * 
	 * ListProperty<String> orientationItems = new SimpleListProperty<>(
	 * FXCollections.observableArrayList(Arrays.asList("Vertical", "Horizontal")));
	 * ObjectProperty<String> orientationSelection = new
	 * SimpleObjectProperty<>("Vertical");
	 * 
	 * IntegerProperty fontSize = new SimpleIntegerProperty(12); DoubleProperty
	 * lineSpacing = new SimpleDoubleProperty(1.5);
	 * 
	 * // Favorites ListProperty<String> favoritesItems = new
	 * SimpleListProperty<>(FXCollections.observableArrayList(Arrays.asList(
	 * "eMovie", "Eboda Phot-O-Shop", "Mikesoft Text", "Mikesoft Numbers",
	 * "Mikesoft Present", "IntelliG"))); ListProperty<String> favoritesSelection =
	 * new SimpleListProperty<>(
	 * FXCollections.observableArrayList(Arrays.asList("Eboda Phot-O-Shop",
	 * "Mikesoft Text")));
	 * 
	 * // Custom Control IntegerProperty customControlProperty = new
	 * SimpleIntegerProperty(42); // IntegerField customControl =
	 * setupCustomControl();
	 * 
	 * 
	 * private IntegerField setupCustomControl() { return
	 * Field.ofIntegerType(customControlProperty).render(new IntegerSliderControl(0,
	 * 42)); }
	 * 
	 * // -------------- Demo -------------- // Theme ListProperty<String> themesLst
	 * = new SimpleListProperty<>(
	 * FXCollections.observableArrayList(newArrayList("IntelliJ", "Darkula",
	 * "Windows"))); ObjectProperty<String> themesObj = new
	 * SimpleObjectProperty<>("IntelliJ");
	 * 
	 * // IDE ListProperty<String> ideLst = new SimpleListProperty<>(
	 * FXCollections.observableArrayList(newArrayList("Subpixel", "Greyscale",
	 * "No Antializing"))); ObjectProperty<String> ideObj = new
	 * SimpleObjectProperty<>("Subpixel");
	 * 
	 * // Editor ListProperty<String> editorLst = new SimpleListProperty<>(
	 * FXCollections.observableArrayList(newArrayList("Subpixel", "Greyscale",
	 * "No Antializing"))); ObjectProperty<String> editorObj = new
	 * SimpleObjectProperty<>("Subpixel");
	 * 
	 * // Font size ListProperty<String> fontLst = new SimpleListProperty<>(
	 * FXCollections.observableArrayList(newArrayList("8", "10", "12", "14", "18",
	 * "20", "22", "24", "36", "72"))); ObjectProperty<String> fontObj = new
	 * SimpleObjectProperty<>("24");
	 * 
	 * // Project opening ListProperty<String> projectOpeningLst = new
	 * SimpleListProperty<>(FXCollections.observableArrayList(newArrayList(
	 * "Open project in new window", "Open project in the same window",
	 * "Confirm window to open project in"))); ObjectProperty<String>
	 * projectOpeningObj = new SimpleObjectProperty<>("Open project in new window");
	 * 
	 * // Closing tool window ListProperty<String> closingToolLst = new
	 * SimpleListProperty<>(
	 * FXCollections.observableArrayList(newArrayList("Terminate process",
	 * "Disconnect (if available)", "Ask"))); ObjectProperty<String> closingToolObj
	 * = new SimpleObjectProperty<>("Ask");
	 */
	public PreferencesFx createPreferences() {

		return PreferencesFx.of(Settings.class,

				Category.of("Control Center", Setting.of("Use developer stack", useDevelop),
						Setting.of("Number of builds to keep", buildsToKeep),
						Setting.of("Show release notes with update", showReleaseNotes),
						Setting.of("Update automatically", updateAutomatically),
						Setting.of("Choose the build to launch", buildItems, buildSelection)),

				Category.of("Paths", Setting.of("k.LAB work directory", workDirectory, true),
						Setting.of("Binary products path", productDirectory, true),
						Setting.of("k.LAB project workspace", workspaceDirectory, true),
						Setting.of("Default export path", exportDirectory, true),
						Setting.of("Default temporary file path", tempDirectory, true)),

				Category.of("Account", Setting.of("Certificate file", certFile, false)),

				Category.of("Engine",

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

						), Category.of("External APIs",

								Group.of("Google Maps", 
										Setting.of("Google API key", googleApiKey)),
								Group.of("Bing Maps", 
										Setting.of("Bing API key", bingApiKey)),
								Group.of("Custom MapBox layer",
										Setting.of("Name", mapboxLayerName),
										Setting.of("URL", mapboxLayerURL),
										Setting.of("Attribution", mapboxLayerAttribution))

						), Category.of("Resources")

				),

				Category.of("Expert settings", Setting.of("Detect and use local hub if available", detectLocalHub),
						Setting.of("Delete leftover temporary storage on startup", deleteTempStorage),
						Setting.of("Use in-memory storage (debug only!)", useInMemoryStorage),
						Setting.of("Remote debug engine configuration (port 8000)", useDebugParameters),
						Setting.of("Alternate authentication endpoint", authenticationEndpoint)))

				.persistWindowState(false).saveSettings(true).debugHistoryMode(false).buttonsVisibility(true);
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
		}
		return new File(System.getProperty("user.home") + File.separator + ".klab" + File.separator + ".scratch");
	}

}
