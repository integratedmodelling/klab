package org.integratedmodelling.controlcenter.jre;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Dialog;

public class JreDialog extends Dialog<Boolean> {

	public JreDialog() {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("JreDialog.fxml"));
			Parent root = loader.load();
			getDialogPane().setContent(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
