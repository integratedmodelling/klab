package org.integratedmodelling.controlcenter;

import java.io.File;
import java.net.URL;
import java.util.Properties;

import org.integratedmodelling.controlcenter.api.IProduct;
import org.integratedmodelling.controlcenter.auth.Authentication;
import org.integratedmodelling.controlcenter.jre.JreDialog;
import org.integratedmodelling.controlcenter.jre.JreModel;
import org.integratedmodelling.controlcenter.settings.Settings;
import org.kordamp.ikonli.javafx.FontIcon;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ControlCenter extends Application {

	public static final String JREDIR_PROPERTY = "klab.directory.jre";

	public static ControlCenter INSTANCE;
	
	Properties properties = new Properties();
	Settings settings;
	
	@FXML
	Button buttonSettings;

	@FXML
	Label certUsername;

	@FXML
	Label certDescription;

	@FXML
	Label messageLabel;

	@FXML
	FontIcon downloadIcon;

	private Authentication authentication;
	private IProduct engineProduct;
	private IProduct modelerProduct;
	private IProduct controlCenterProduct;
	
	public ControlCenter() {
		INSTANCE = this;
		this.settings = new Settings();
		// read properties
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
	}
	
	public File getWorkdir() {
		// TODO Auto-generated method stub
		return null;
	}

	public URL getJreDownloadUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	public Properties getProperties() {
		return properties;
	}

	@FXML
	public void buttonSettingsClicked() {
		settings.getPreferences().show();
	}

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
//			scene.getStylesheets().add(getClass().getResource("css/application.css").toExternalForm());
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
