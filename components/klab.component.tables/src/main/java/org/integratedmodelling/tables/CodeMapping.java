package org.integratedmodelling.tables;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualTreeBidiMap;
import org.integratedmodelling.klab.exceptions.KlabIOException;

public class CodeMapping {

	Properties properties = new Properties();
	BidiMap<String, String> mappings = new DualTreeBidiMap<>();
	
	public CodeMapping(File propertiesFile) {
		try (InputStream input = new FileInputStream(propertiesFile)) {
			properties.load(input);
		} catch (Exception e) {
			throw new KlabIOException(e);
		}
	}
	
	
	
}
