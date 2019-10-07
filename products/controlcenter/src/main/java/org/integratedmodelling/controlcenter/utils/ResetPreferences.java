package org.integratedmodelling.controlcenter.utils;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.integratedmodelling.controlcenter.settings.Settings;

public class ResetPreferences {

	public static void main(String[] args) {
	    Preferences preferences = Preferences.userNodeForPackage(Settings.class);
	    try {
			preferences.clear();
		} catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
