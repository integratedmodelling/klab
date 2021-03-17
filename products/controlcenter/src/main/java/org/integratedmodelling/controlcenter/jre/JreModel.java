package org.integratedmodelling.controlcenter.jre;

import java.io.File;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.integratedmodelling.controlcenter.ControlCenter;
import org.integratedmodelling.klab.utils.OS;

public enum JreModel {

	INSTANCE;

	File jreDirectory;
	boolean haveSpecifiedJre;
	// boolean haveJavaHome;
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
		Properties properties = ControlCenter.INSTANCE.getProperties();
		// try to find klab settings
		haveKlabSetting = properties.getProperty(ControlCenter.JREDIR_PROPERTY) != null;
		// if is a refresh with a jre setted, we don't want to change it
		if (jreDirectory == null) {
			// is not a refresh
			jreDirectory = new File(
					properties.getProperty(ControlCenter.JREDIR_PROPERTY, ControlCenter.INSTANCE.getWorkdir() + File.separator + "jre" + File.separator + "bin"));
		}
		haveSpecifiedJre = jreDirectory.exists() && jreDirectory.isDirectory();
		if (haveSpecifiedJre) {
			// if jre is detected, we don't need to check the public java
			isPublicJavaOk = true;
		} else {
		    isPublicJavaOk = false;
			/*
		    // try to find a solution using the $JAVA_HOME or the java.home system property
			String javaHome = System.getenv("JAVA_HOME");
			if ((javaHome = System.getenv("JAVA_HOME")) == null) {
				javaHome = System.getProperty("java.home");
			}
			if (haveJavaHome = javaHome != null) {
				// try to find bin directory
				// before if is JRE...
				String binPath = javaHome + File.separator + "bin";
				isPublicJavaOk = new File(binPath).isDirectory();
				if (!isPublicJavaOk) {
					// ...else if is JDK, we search jre/bin directory
					binPath = javaHome + File.separator + "jre" + File.separator + "bin";
					isPublicJavaOk = new File(binPath).isDirectory();
				}
				if (!haveSpecifiedJre) {
					jreDirectory = new File(binPath);
				}
			}
			*/
		}
	}

	public String concernMessage() {
	    /*
		String ret = null;

		if (haveKlabSetting && !haveSpecifiedJre) {
			ret = "Your k.LAB settings specify a JRE that does not seem to exist.";
		} else if (haveJavaHome && !isPublicJavaOk) {
			ret = "Your java executable does not seem to be standard distribution.";
		} else if (jreDirectory == null || !haveJavaHome && !haveSpecifiedJre) {
			ret = "You don't seem to have Java installed.";
		}

		return ret;
		*/
	    return isPublicJavaOk ? null : "Download OpenJDK JRE";
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
