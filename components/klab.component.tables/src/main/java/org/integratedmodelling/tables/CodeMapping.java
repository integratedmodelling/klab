package org.integratedmodelling.tables;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualTreeBidiMap;
import org.integratedmodelling.klab.exceptions.KlabIOException;

public class CodeMapping {

	Properties properties = new Properties();
	BidiMap<String, String> mappings = new DualTreeBidiMap<>();
	
	public CodeMapping(File propertiesFile) {
		try (InputStream input = new FileInputStream(propertiesFile)) {
			properties.load(input);
			for (Object key : properties.keySet()) {
				if (key.toString().startsWith("category.")) {
					String[] pp = properties.getProperty(key.toString()).split(Pattern.quote("->"));
					if (pp.length > 1 && !pp[1].isEmpty()) {
						mappings.put(pp[0], pp[1]);
					}
				}
			}
		} catch (Exception e) {
			throw new KlabIOException(e);
		}
	}

	public Object map(Object value) {
		return value == null ? null : mappings.get(value.toString());
	}

	public Object reverseMap(Object value) {
		return value == null ? null : mappings.inverseBidiMap().get(value.toString());
	}
	
	
	
}
