package org.integratedmodelling.controlcenter.dialogs;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Update extends AnchorPane {

	private Update() {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateDialog.fxml"));
			loader.setRoot(this);
			Parent root = loader.load();
		    Stage stage = new Stage();
		    stage.setScene(new Scene(root, 480, 198));
		    stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void show() {
		new Update();
	}
}
