package org.integratedmodelling.controlcenter.jre;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.integratedmodelling.controlcenter.ControlCenter;
import org.integratedmodelling.controlcenter.runtime.Downloader;
import org.integratedmodelling.controlcenter.utils.ZipUtils;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class JreDialogController {

	@FXML
	Label message;
	@FXML
	ProgressBar progress;
	@FXML
	Button downloadButton;
	@FXML
	Button continueButton;

	AtomicBoolean downloading = new AtomicBoolean(false);

	@FXML
	private void initialize() {
		setUI();
	}

	@FXML
	private void specifyPath() {

	}

	@FXML
	private void download() {

		progress.setVisible(true);
		downloadButton.setDisable(true);
		File tempOutput;

		try {
			tempOutput = File.createTempFile("jre", ".zip");
		} catch (IOException e1) {
			return;
		}
		Downloader downloader = new Downloader(ControlCenter.INSTANCE.getJreDownloadUrl(), tempOutput, (partial, total) -> {
			Platform.runLater(() -> {
				progress.setProgress((double) partial / (double) total);
			});
		}) {

			@Override
			protected void finish() {
				try {
					downloading.set(true);
					ZipUtils.unzip(tempOutput, ControlCenter.INSTANCE.getWorkdir());
					// needed in OS. ZIP doesn't store permissions
					ControlCenter.INSTANCE.getLocalJREDirectory().setExecutable(true);
					JreModel.INSTANCE.connectLocalJre();
				} catch (IOException e) {
					downloading.set(false);
					fail(e);
				} finally {
					Platform.runLater(() -> {
						progress.setVisible(false);
						downloadButton.setDisable(false);
						setUI();
					});
				}
			}

			@Override
			protected void fail(Exception e) {
				Platform.runLater(() -> {
					progress.setStyle("-fx-base: #ff0000;");
				});
			}
		};

		downloader.startDownload();
	}

	@FXML
	private void handleContinue() {
		continueButton.getScene().getWindow().hide();
		if (JreModel.INSTANCE.concernMessage() != null) {
			System.exit(0);
		}
	}

	private void setUI() {

		String problem = JreModel.INSTANCE.concernMessage();
		message.setText(problem == null ? "Everything OK!" : problem);

		if (problem == null) {
		    downloadButton.setDisable(true);
			continueButton.setText("Continue");			
		} else {
			continueButton.setText("Exit");
		}
	}

}
