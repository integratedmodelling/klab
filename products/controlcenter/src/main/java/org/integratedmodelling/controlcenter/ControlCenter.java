package org.integratedmodelling.controlcenter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.integratedmodelling.controlcenter.api.IAuthentication.Group;
import org.integratedmodelling.controlcenter.api.IAuthentication.Status;
import org.integratedmodelling.controlcenter.api.IInstance;
import org.integratedmodelling.controlcenter.auth.Authentication;
import org.integratedmodelling.controlcenter.jre.JreDialog;
import org.integratedmodelling.controlcenter.jre.JreModel;
import org.integratedmodelling.controlcenter.product.Distribution.SyncListener;
import org.integratedmodelling.controlcenter.product.ProductService;
import org.integratedmodelling.controlcenter.product.ProductService.BuildStatus;
import org.integratedmodelling.controlcenter.settings.Settings;
import org.integratedmodelling.klab.utils.BrowserUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.kordamp.ikonli.javafx.FontIcon;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import kong.unirest.JacksonObjectMapper;
import kong.unirest.Unirest;

public class ControlCenter extends Application {

	public static final String COLOR_GREEN = "#28c41d";
	public static final String COLOR_LIGHT_GREY = "#bbbbbb";
	public static final String COLOR_RED = "#f23a01";
	public static final String COLOR_YELLOW = "#dfb300";

	public static final String JREDIR_PROPERTY = "klab.directory.jre";
	public static final String PRODUCTION_BRANCH_PROPERTY = "klab.branch.production";
	public static final String DEVELOP_BRANCH_PROPERTY = "klab.branch.develop";
	private static final String DEFAULT_JRE_DOWNLOAD_URL = "http://www.integratedmodelling.org/downloads";

	public static ControlCenter INSTANCE;

	Properties properties = new Properties();
	Settings settings;
	AtomicBoolean downloadViewShown = new AtomicBoolean(false);

	@FXML
	Button buttonSettings;

	@FXML
	Label certContentLabel;

	@FXML
	Label certUsername;

	@FXML
	Label certDescription;

	@FXML
	Label messageLabel;

	@FXML
	FontIcon downloadIcon;

	@FXML
	VBox certificateArea;

	@FXML
	GridPane groupIconArea;

	@FXML
	Button downloadButton;

	// the next three are in a stackpane and only one must be visible at a time
	@FXML
	VBox engineMessageArea;
	@FXML
	VBox downloadProgressArea;
	@FXML
	GridPane engineRuntimeArea;

	@FXML
	Label installedVersionLabel;
	@FXML
	Label engineMessageDetail;
	@FXML
	FontIcon engineMessageIcon;
	@FXML
	Label engineHeaderDetail;
	@FXML
	Label engineHeader;

	// the next are all the progress bars and labels that compose the download
	// status panel
	@FXML
	Label engineProgressLabelTotal;
	@FXML
	Label engineProgressLabelDetail;
	@FXML
	Label engineCurrentFileLabel;
	@FXML
	Label modelerProgressLabel;
	@FXML
	ProgressBar engineProgressBarOverall;
	@FXML
	ProgressBar engineProgressBarDetail;
	@FXML
	ProgressBar modelerProgressBar;

	private Authentication authentication;
	private IInstance engine;
	private IInstance modeler;
	private IInstance controlCenter;

	public ControlCenter() {

		Unirest.config().setObjectMapper(new JacksonObjectMapper());

		INSTANCE = this;
		this.settings = new Settings();
		this.settings.getWorkDirectory().mkdirs();
		try (InputStream input = new FileInputStream(
				new File(this.settings.getWorkDirectory() + File.separator + "klab.properties"))) {
			properties.load(input);
		} catch (Exception e) {
			// no properties and that's it
		}
	}

	/**
	 * Called by settings when workDirectory is validated but not yet accepted as
	 * settings value.
	 * 
	 * @param workDirectory
	 */
	public void changeWorkDirectory(File workDirectory) {

		properties.clear();
		try (InputStream input = new FileInputStream(new File(workDirectory + File.separator + "klab.properties"))) {
			properties.load(input);
		} catch (Exception e) {
			// no properties and that's it
		}
	}

	public Settings getSettings() {
		return this.settings;
	}

	@FXML
	private void initialize() {

		/*
		 * check Java first
		 */
		String concernMessage = JreModel.INSTANCE.concernMessage();
		if (concernMessage != null) {
			JreDialog dialog = new JreDialog();
			dialog.showAndWait();
			// this won't let us continue unless everything is OK.
		}

		/*
		 * read authentication using setting for certificate
		 */
		this.authentication = new Authentication();

		/*
		 * retrieve instances
		 */
		this.engine = ProductService.INSTANCE.getInstance(ProductService.PRODUCT_ENGINE);
		this.modeler = ProductService.INSTANCE.getInstance(ProductService.PRODUCT_MODELER);
		this.controlCenter = ProductService.INSTANCE.getInstance(ProductService.PRODUCT_CONTROL_CENTER);

		/*
		 * setup event handlers
		 */
		certificateArea.setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				if (/* event.getGestureSource() != dropCertificate && */ event.getDragboard().hasFiles()) {
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}
				event.consume();
			}
		});

		certificateArea.setOnDragDropped(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				boolean success = false;
				if (db.hasFiles()) {
					authentication.readCertificate(db.getFiles().get(0));
					if (authentication.getStatus() == Status.VALID) {
						authentication.installCertificate(db.getFiles().get(0));
					}
					success = true;
				}
				event.setDropCompleted(success);
				event.consume();
			}
		});

		/*
		 * setup auth UI
		 */
		setupAuthenticationUI();

		/*
		 * setup UI
		 */
		setupUI();

		/*
		 * enable settings callbacks
		 */
		this.settings.setActionReady(true);

	}

	public void setupAuthenticationUI() {
		if (this.authentication != null) {
			switch (this.authentication.getStatus()) {
			case ANONYMOUS:
				certContentLabel.setText("No certificate");
				certContentLabel.setTextFill(Paint.valueOf(COLOR_RED));
				certUsername.setText("Anonymous");
				certUsername.setTextFill(Paint.valueOf(COLOR_LIGHT_GREY));
				certDescription.setText("Drop a certificate file here");
				break;
			case EXPIRED:
				certContentLabel.setText("Certificate expired!");
				certContentLabel.setTextFill(Paint.valueOf(COLOR_RED));
				certUsername.setText(this.authentication.getUsername());
				certUsername.setTextFill(Paint.valueOf(COLOR_RED));
				certDescription.setText("Expired " + this.authentication.getExpiration());
				break;
			case INVALID:
				certContentLabel.setText("Invalid certificate!");
				certContentLabel.setTextFill(Paint.valueOf(COLOR_RED));
				certUsername.setText(this.authentication.getUsername());
				certUsername.setTextFill(Paint.valueOf(COLOR_RED));
				certDescription.setText("Drop a valid certificate here");
			case OFFLINE:
				certContentLabel.setText("System is offline");
				certContentLabel.setTextFill(Paint.valueOf(COLOR_RED));
				certUsername.setText(this.authentication.getUsername());
				certUsername.setTextFill(Paint.valueOf(COLOR_LIGHT_GREY));
				certDescription.setText("Check network connection");
				break;
			case VALID:
				certContentLabel.setText("Certificate is valid");
				certContentLabel.setTextFill(Paint.valueOf("#666666"));
				certUsername.setText(this.authentication.getUsername());
				certUsername.setTextFill(Paint.valueOf(COLOR_GREEN));
				certDescription.setText(
						"Expires " + this.authentication.getExpiration().toString(DateTimeFormat.mediumDate()));
				break;
			default:
				break;
			}

			int i = 0;
			for (Group group : this.authentication.getGroups()) {
				if (group.iconUrl != null && i < 9) {
					int columnIndex = i % 3;
					int rowIndex = i / 3;
					Image groupImage = new Image(group.iconUrl, 24, 24, false, false);
					ImageView groupIcon = new ImageView(groupImage);
					groupIcon.setPickOnBounds(true);
					this.groupIconArea.add(groupIcon, columnIndex, rowIndex);
					Tooltip.install(groupIcon, new Tooltip(group.description));
					i++;
				}
			}
		}

	}

	/**
	 * Reentrant UI setup, to be called as needed.
	 */
	public void setupUI() {
		// TODO Auto-generated method stub

		BuildStatus bs = ProductService.INSTANCE.getBuildStatus();

		if (engine != null && bs != null) {

			if (bs.latest < 0) {

				/*
				 * no k.LAB
				 */
				downloadButton.setDisable(true);
				engineHeader.setText("Download k.LAB");
				engineHeaderDetail.setText("Network is inaccessible");

			} else {

				boolean usingLatest = bs.chosen != bs.latest;
				boolean haveLatest = bs.installed.contains(bs.latest);

				/*
				 * using an older one or nothing installed
				 */
				if (!haveLatest) {
					downloadButton.setDisable(false);
					engineHeader.setText("Download k.LAB");
					engineHeaderDetail.setText("Build " + engine.getProduct().getBuildVersion(bs.latest) + "."
							+ bs.latest + " is available");
					engineHeaderDetail.setTextFill(Paint.valueOf(COLOR_YELLOW));
				} else {
					downloadButton.setDisable(true);
					engineHeader.setText("k.LAB is up to date");
					if (usingLatest) {
						engineHeaderDetail.setText("Latest build " + engine.getProduct().getBuildVersion(bs.latest)
								+ "." + bs.latest + " is installed");
						engineHeaderDetail.setTextFill(Paint.valueOf(COLOR_GREEN));
					} else if (bs.chosen > 0) {
						engineHeaderDetail.setText("Obsolete build " + bs.chosen + " is selected");
						engineHeaderDetail.setTextFill(Paint.valueOf(COLOR_YELLOW));
					}
				}
			}

			if (!downloadViewShown.get()) {

				switch (engine.getStatus()) {
				case STOPPED:
				case ERROR:
				case WAITING:
					engineMessageArea.setVisible(true);
					engineRuntimeArea.setVisible(false);
					downloadProgressArea.setVisible(false);
					break;
				case RUNNING:
					engineMessageArea.setVisible(false);
					engineRuntimeArea.setVisible(true);
					downloadProgressArea.setVisible(false);
					break;
				default:
					break;

				}
			}

		}
	}

	public File getWorkdir() {
		return settings.getWorkDirectory();
	}

	public URL getJreDownloadUrl() {
		try {
			return new URL(properties.getProperty(JREDIR_PROPERTY, DEFAULT_JRE_DOWNLOAD_URL));
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperty(String property, String value) {

		properties.setProperty(property, value);
		try (OutputStream input = new FileOutputStream(
				new File(this.settings.getWorkDirectory() + File.separator + "klab.properties"))) {
			properties.store(input, "Written by k.LAB Control Center on " + DateTime.now());
		} catch (Exception e) {
			// no properties and that's it
		}
	}

	public void message(String message) {
		messageLabel.setText(message == null ? "" : message);
	}

	/*
	 * -----------------------------------------------------------------------------
	 * UI callbacks
	 * -----------------------------------------------------------------------------
	 */

	@FXML
	public void buttonSettingsClicked() {
		settings.getPreferences().show();
	}

	@FXML
	public void launchHubSite() {
		if (this.authentication != null) {
			BrowserUtils.startBrowser(this.authentication.getAuthenticationEndpoint());
		}
	}

	@FXML
	public void downloadButtonClicked() {

		BuildStatus bs = ProductService.INSTANCE.getBuildStatus();

		if (!bs.installed.contains(bs.latest)) {

			if (!downloadViewShown.get()) {

				new Thread() {

					@Override
					public void run() {

						downloadViewShown.set(true);

						Platform.runLater(() -> {
							engineMessageArea.setVisible(false);
							engineRuntimeArea.setVisible(false);
							downloadProgressArea.setVisible(true);
							engineHeaderDetail.setText("Downloading build " + bs.latest);
						});

						ExecutorService executor = Executors.newFixedThreadPool(2);
						Future<Boolean> etask = executor.submit(new Callable<Boolean>() {
							@Override
							public Boolean call() throws Exception {
								return engine.download(bs.latest, new SyncListener() {

									int total;
									int sofar = 0;

									@Override
									public void transferFinished() {
									}

									@Override
									public void notifyFileProgress(String file, long bytesSoFar, long totalBytes) {
										Platform.runLater(() -> {
											engineProgressBarDetail
													.setProgress((double) bytesSoFar / (double) totalBytes);
											engineProgressLabelDetail
													.setText((bytesSoFar / 1024) + "/" + (totalBytes / 1024) + " kB");
										});
									}

									@Override
									public void notifyDownloadCount(int downloadFilecount, int deleteFileCount) {
										this.total = downloadFilecount;
									}

									@Override
									public void beforeDownload(String file) {
										sofar++;
										Platform.runLater(() -> {
											engineProgressBarOverall.setProgress((double) sofar / (double) total);
											engineProgressLabelTotal.setText("#" + sofar + " of " + total);
											engineCurrentFileLabel.setText(file);
										});
									}

									@Override
									public void beforeDelete(File localFile) {
									}

								}).isComplete();
							}
						});

						Future<Boolean> mtask = executor.submit(new Callable<Boolean>() {
							@Override
							public Boolean call() throws Exception {
								return modeler.download(bs.latest, new SyncListener() {

									@Override
									public void transferFinished() {
									}

									@Override
									public void notifyFileProgress(String file, long bytesSoFar, long totalBytes) {
									}

									@Override
									public void notifyDownloadCount(int downloadFilecount, int deleteFileCount) {
									}

									@Override
									public void beforeDownload(String file) {
									}

									@Override
									public void beforeDelete(File localFile) {
									}

								}).isComplete();
							}
						});

						/*
						 * wait until done and reset UI to new situation
						 */
						while (!(mtask.isDone() && etask.isDone())) {
							try {
								Thread.sleep(300);
							} catch (InterruptedException e) {
							}
						}

						downloadViewShown.set(false);
						
						Platform.runLater(() -> setupUI());
					}

				}.start();

			}
		}

	}

	/*
	 * -----------------------------------------------------------------------------
	 */

	@Override
	public void start(Stage primaryStage) {

		/*
		 * if (!System.getProperty("os.arch").contains("64")) { throw new
		 * RuntimeException("You need a 64 bit architecture to run k.LAB"); }
		 */
		try {
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("ControlCenter.fxml"));
			Scene scene = new Scene(root, 260, 424);
			primaryStage.setTitle("k.LAB");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icons/kdot16.png")));
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icons/kdot32.png")));
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icons/kdot64.png")));
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icons/kdot128.png")));
			scene.getStylesheets().add(getClass().getResource("css/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			// va be'
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void errorAlert(String string) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Runtime error");
		alert.setHeaderText("An unexpected error occurred:");
		alert.setContentText(string);
		alert.showAndWait();
	}

}
