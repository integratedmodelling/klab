package org.integratedmodelling.controlcenter.dialogs;

import org.integratedmodelling.klab.utils.BrowserUtils;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class UpdateDialog {

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
	
}
