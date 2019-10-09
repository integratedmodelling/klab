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
			FXCollections.observableArrayList(Arrays.asList("Aggressive", "Conservative", "Disabled")));

	private BooleanProperty useDevelop = new SimpleBooleanProperty(false);
	private BooleanProperty detectLocalHub = new SimpleBooleanProperty(false);
	private BooleanProperty showReleaseNotes = new SimpleBooleanProperty(false);
	private BooleanProperty updateAutomatically = new SimpleBooleanProperty(false);
	private IntegerProperty buildsToKeep = new SimpleIntegerProperty(1);
	private IntegerProperty maxMemory = new SimpleIntegerProperty(2048);
	private IntegerProperty enginePort = new SimpleIntegerProperty(8283);
	private ObjectProperty<String> buildSelection = new SimpleObjectProperty<>("Latest");
	private ObjectProperty<String> parallelismStrategy = new SimpleObjectProperty<>("Conservative");
	private ObjectProperty<File> workDirectory = new SimpleObjectProperty<>(
			new File(System.getProperty("user.home") + File.separator + ".klab"));
	private ObjectProperty<File> workspaceDirectory = new SimpleObjectProperty<>(
			new File(System.getProperty("user.home") + File.separator + ".klab" + File.separator + "workspace"));
	private ObjectProperty<File> exportDirectory = new SimpleObjectProperty<>(
			new File(System.getProperty("user.home") + File.separator + ".klab" + File.separator + "export"));
	private ObjectProperty<File> certFile = new SimpleObjectProperty<>(
			new File(System.getProperty("user.home") + File.separator + ".klab" + File.separator + "klab.cert"));
	private ObjectProperty<File> tempDirectory = new SimpleObjectProperty<>(getTemporaryPath());

	private DoubleProperty minModelCoverage = new SimpleDoubleProperty(0.01);
	private DoubleProperty minTotalCoverage = new SimpleDoubleProperty(0.95);
	private DoubleProperty minCoverageImprovement = new SimpleDoubleProperty(0.2);

	private BooleanProperty debugParameters = new SimpleBooleanProperty(false);
	private BooleanProperty deleteTempStorage = new SimpleBooleanProperty(true);

	private StringProperty googleApiKey = new SimpleStringProperty("");
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
						Setting.of("k.LAB project workspace", workspaceDirectory, true),
						Setting.of("Default export path", exportDirectory, true),
						Setting.of("Default temporary file path", tempDirectory, true)),

				Category.of("Account", Setting.of("Certificate file", certFile, false)),

				Category.of("Engine",

						Setting.of("Max RAM occupation (MB)", maxMemory, 512, 64000),
						Setting.of("Engine port (default 8283)", enginePort, 8269, 8299),
						Setting.of("Parallelism strategy", parallelismStrategies, parallelismStrategy)

				).subCategories(

						Category.of("Runtime"

						),
						Category.of("Space and time", Group.of("Coverage",
								Setting.of("Minimum model coverage (0-1: default 0.01)", minModelCoverage, 0.0, 1.0, 2),
								Setting.of("Minimum coverage improvement (0-1: default 0.2)", minCoverageImprovement,
										0.0, 1.0, 2),
								Setting.of("Minimum total coverage (0-1: default 0.95)", minTotalCoverage, 0.0, 1.0, 2))
								.description("Context coverage in resolution")),
						Category.of("Connectivity"), Category.of("External APIs",

								Setting.of("Google API key", googleApiKey)

						), Category.of("Resources"), Category.of("External APIs")

				),

				Category.of("Expert settings", Setting.of("Detect and use local hub if available", detectLocalHub),
						Setting.of("Delete leftover temporary storage on startup", deleteTempStorage),
						Setting.of("Launch engine with debug service (port 8000)", debugParameters),
						Setting.of("Alt authentication endpoint", authenticationEndpoint)))

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
