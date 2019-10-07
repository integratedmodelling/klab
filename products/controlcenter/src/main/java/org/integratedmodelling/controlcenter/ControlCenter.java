package org.integratedmodelling.controlcenter;

import java.io.File;
import java.net.URL;
import java.util.Properties;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.integratedmodelling.controlcenter.settings.Settings;
import org.kordamp.ikonli.javafx.FontIcon;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ControlCenter extends Application {

	public static final String JREDIR_PROPERTY = null;

	Properties properties = new Properties();
	Settings settings = new Settings();
	
	@FXML
	FontIcon buttonSettings;

	public ControlCenter() {
		// read properties
		

	}
	
	public static File getWorkdir() {
		// TODO Auto-generated method stub
		return null;
	}

	public static URL getJreDownloadUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	public static Properties getProperties() {
		// TODO Auto-generated method stub
		return null;
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
			Scene scene = new Scene(root, 260, 380);
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
