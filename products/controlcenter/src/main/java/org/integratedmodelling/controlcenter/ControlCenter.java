package org.integratedmodelling.controlcenter;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.integratedmodelling.controlcenter.api.IInstance;
import org.integratedmodelling.controlcenter.auth.Authentication;
import org.integratedmodelling.controlcenter.jre.JreDialog;
import org.integratedmodelling.controlcenter.jre.JreModel;
import org.integratedmodelling.controlcenter.product.ProductService;
import org.integratedmodelling.controlcenter.settings.Settings;
import org.integratedmodelling.klab.utils.BrowserUtils;
import org.joda.time.format.DateTimeFormat;
import org.kordamp.ikonli.javafx.FontIcon;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import kong.unirest.JacksonObjectMapper;
import kong.unirest.Unirest;

public class ControlCenter extends Application {

	public static final String JREDIR_PROPERTY = "klab.directory.jre";
	public static final String PRODUCTION_BRANCH_PROPERTY = "klab.branch.production";
	public static final String DEVELOP_BRANCH_PROPERTY = "klab.branch.develop";
	private static final String DEFAULT_JRE_DOWNLOAD_URL = "http://www.integratedmodelling.org/downloads";

	public static ControlCenter INSTANCE;

	Properties properties = new Properties();
	Settings settings;

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
					System.out.println("CIAPI ISTÉS");
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

	}

	public void setupAuthenticationUI() {
		if (this.authentication != null) {
			switch (this.authentication.getStatus()) {
			case ANONYMOUS:
				certContentLabel.setText("No certificate");
				certContentLabel.setTextFill(Paint.valueOf("#f23a01"));
				certUsername.setText("Anonymous");
				certUsername.setTextFill(Paint.valueOf("#bbbbbb"));
				certDescription.setText("Drop a certificate file here");
				break;
			case EXPIRED:
				certContentLabel.setText("Certificate expired!");
				certContentLabel.setTextFill(Paint.valueOf("#f23a01"));
				certUsername.setText(this.authentication.getUsername());
				certUsername.setTextFill(Paint.valueOf("#f23a01"));
				certDescription.setText("Expired " + this.authentication.getExpiration());
				break;
			case INVALID:
				certContentLabel.setText("Invalid certificate!");
				certContentLabel.setTextFill(Paint.valueOf("#f23a01"));
				certUsername.setText(this.authentication.getUsername());
				certUsername.setTextFill(Paint.valueOf("#f23a01"));
				certDescription.setText("Drop a valid certificate here");
			case OFFLINE:
				certContentLabel.setText("System is offline");
				certContentLabel.setTextFill(Paint.valueOf("#f23a01"));
				certUsername.setText(this.authentication.getUsername());
				certUsername.setTextFill(Paint.valueOf("#bbbbbb"));
				certDescription.setText("Check network connection");
				break;
			case VALID:
				certContentLabel.setText("Certificate is valid");
				certContentLabel.setTextFill(Paint.valueOf("#666666"));
				certUsername.setText(this.authentication.getUsername());
				certUsername.setTextFill(Paint.valueOf("#28c41d"));
				certDescription.setText("Expires " + this.authentication.getExpiration().toString(DateTimeFormat.mediumDate()));
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Reentrant UI setup, to be called as needed.
	 */
	public void setupUI() {
		// TODO Auto-generated method stub

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
	
	/*
	 * -----------------------------------------------------------------------------
	 * ---
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

}
