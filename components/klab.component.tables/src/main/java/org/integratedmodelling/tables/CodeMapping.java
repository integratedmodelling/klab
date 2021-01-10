package org.integratedmodelling.tables;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualTreeBidiMap;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.exceptions.KlabIOException;

/**
 * Maps the values in a table (as strings) to other values, in either direction.
 * Initialized with a property file that besides the category mappings
 * (expressed as category.nn=X->Y) may contain a type for the values, a
 * worldview identifier and a root concept if the type is concept and the
 * worldview is set.
 * 
 * @author Ferd
 *
 */
public class CodeMapping {

	private Properties properties = new Properties();
	private BidiMap<String, String> mappings = new DualTreeBidiMap<>();
	private IArtifact.Type type = null;
	private String worldview = null;
	private IConcept rootConcept = null;

	public CodeMapping(File propertiesFile) {
		try (InputStream input = new FileInputStream(propertiesFile)) {
			properties.load(input);
			this.worldview = properties.getProperty("worldview");
			String t = properties.getProperty("type");
			if (t != null) {
				this.type = IArtifact.Type.valueOf(t.toUpperCase());
			}
			if (this.worldview != null && this.type == IArtifact.Type.CONCEPT) {
				/*
				 * check for root concept
				 */
				String rc = properties.getProperty("root.concept");
				if (rc != null) {
					this.rootConcept = Concepts.INSTANCE.declare(Concepts.INSTANCE.declare(rc));
				}
			}
			String pattern = Pattern.quote("->");
			for (Object key : properties.keySet()) {
				if (key.toString().startsWith("category.")) {
					String[] pp = properties.getProperty(key.toString()).split(pattern);
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

	public IConcept getRootConcept() {
		return rootConcept;
	}

	public String getWorldview() {
		return worldview;
	}

	public IArtifact.Type getType() {
		return type;
	}

}
