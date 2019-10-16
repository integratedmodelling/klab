package org.integratedmodelling.controlcenter;

import java.io.IOException;

import org.integratedmodelling.klab.utils.BrowserUtils;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;

public class UpdateDialog extends Dialog<Boolean> {

	private static final String KLAB_DOWNLOAD_URL = "http://integratedmodelling.org/statics/pages/gettingstarted.html";
	
	@FXML
	Button continueButton;
	
	@FXML
	private void handleExit() {
		Platform.exit();
		System.exit(0);
	}
	
	@FXML
	private void handleContinue() {
		continueButton.getScene().getWindow().hide();
	}
	
	@FXML
	private void openDownloadSite() {
		BrowserUtils.startBrowser(KLAB_DOWNLOAD_URL);
	}
	
	public UpdateDialog() {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateDialog.fxml"));
			Parent root = loader.load();
			getDialogPane().setContent(root);
			setResultConverter(buttonType -> {
				// SomeDataType someData = ... ;
				return true;
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
