package org.integratedmodelling.controlcenter.jre;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.controlcenter.ControlCenter;
import org.integratedmodelling.klab.utils.OS;

public enum JreModel {

	INSTANCE;

	File jreDirectory;
	boolean haveSpecifiedJre;
	boolean haveJavaHome;
	boolean haveKlabSetting;
	boolean isPublicJavaOk;
	Set<Action> possibleActions = new HashSet<>();

	enum Action {
		INSTALL_LOCAL, SPECIFY_PUBLIC_JRE, EXIT_AND_FIX_PUBLIC_INSTALLATION
	}

	private JreModel() {
		refresh();
	}

	public void refresh() {

		haveKlabSetting = ControlCenter.INSTANCE.getProperties().getProperty(ControlCenter.JREDIR_PROPERTY) != null;
		jreDirectory = new File(ControlCenter.INSTANCE.getProperties().getProperty(ControlCenter.JREDIR_PROPERTY,
				ControlCenter.INSTANCE.getWorkdir() + File.separator + "jre" + File.separator + "bin"));
		haveSpecifiedJre = jreDirectory.exists();
		haveJavaHome = System.getenv("JAVA_HOME") != null;
		if (haveJavaHome) {
			isPublicJavaOk = new File(System.getenv("JAVA_HOME") + File.separator + "jre" + File.separator + "bin")
					.isDirectory();
			if (!haveSpecifiedJre) {
				jreDirectory = new File(System.getenv("JAVA_HOME") + File.separator + "jre" + File.separator + "bin");
			}
		}
	}

	public String concernMessage() {

		String ret = null;

		if (haveKlabSetting && !haveSpecifiedJre) {
			ret = "Your k.LAB settings specify a JRE that does not seem to exist.";
		} else if (haveJavaHome && !isPublicJavaOk) {
			ret = "Your JAVA_HOME environmental variable seems to point to a non-existing installation.";
		} else if (jreDirectory == null || !haveJavaHome && !haveSpecifiedJre) {
			ret = "You don't seem to have Java installed.";
		}

		return ret;
	}

	public String getJavaExecutable() {
		return jreDirectory + File.separator + "java" + (OS.get() == OS.WIN ? ".exe" : "");
	}

	public void connectLocalJre() {
		this.jreDirectory = new File(
				ControlCenter.INSTANCE.getWorkdir() + File.separator + "jre" + File.separator + "bin");
		refresh();
	}

}
