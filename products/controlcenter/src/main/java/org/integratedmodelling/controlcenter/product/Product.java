package org.integratedmodelling.controlcenter.product;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.integratedmodelling.controlcenter.api.IProduct;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.utils.OS;

public class Product implements IProduct {

	enum Type {

		/**
		 * Jar packaging with bin/, lib/ and a main jar file with a main class in
		 * properties, OS independent distribution with potential OS-specific
		 * subcomponents to merge in from subdirs.
		 */
		JAR,

		/**
		 * Installer or binary executable packaging with a directory per supported OS.
		 */
		EXE,

		/**
		 * Eclipse packaging with a zipped or unzipped distribution per supported OS.
		 */
		ECLIPSE
	}

	private String baseUrl;
	private String productId;

	public Product(String baseUrl, String productId) {
		this.baseUrl = baseUrl;
		this.productId = productId;
		
	}
	
	@Override
	public Status getStatus() {
		return null;
	}
	
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getBuilds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getFileHashes(int build, OS os) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Properties getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Properties getBuildProperties(int build) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Version getBuildVersion(int build) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
