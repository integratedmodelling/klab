package org.integratedmodelling.controlcenter.product;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.integratedmodelling.controlcenter.api.IProduct;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.utils.NumberUtils;

public class Product implements IProduct {

	public class Build {
		
		int id;
		String url;
		Distribution distribution;
		File workspace;
		Properties properties = new Properties();
		Version version = null;
		Date time;
		boolean locallyAvailable = false;
		boolean remotelyAvailable = true;
		
		Build(int n) {
			
			this.workspace = new File(localWorkspace + File.separator + n);
			this.workspace.mkdirs();
			this.url = baseUrl + "/" + productId + "/" + n;
			this.distribution = new Distribution(this.url, this.workspace);
			try (InputStream in = new URL(this.url + "/build.properties").openStream()) {
				this.properties.load(in);
				this.version = Version.create(this.properties.getProperty(BUILD_VERSION_PROPERTY));
				this.time = DateFormat.getInstance().parse(this.properties.getProperty(BUILD_TIME_PROPERTY));
			} catch (Exception e) {
				this.remotelyAvailable = false;
			}
			if (distribution.isComplete()) {
				this.locallyAvailable = true;
			}
		}
	}

	private Status status = Status.UNKNOWN;
	private Properties properties = new Properties();
	private String baseUrl;
	private String productId;
	private String name;
	private String description;
	private Type type;
	private List<Integer> buildIds = new ArrayList<>();
	private Map<Integer, Build> builds = new HashMap<>();
	private File localWorkspace;

	public Product(String baseUrl, String productId, File ws) {

		this.baseUrl = baseUrl;
		this.productId = productId;
		this.localWorkspace = new File(ws + File.separator + productId);
		this.localWorkspace.mkdirs();

		/*
		 * try to read the product properties
		 */
		try (InputStream in = new URL(baseUrl + "/" + productId + "/product.properties").openStream()) {
			properties.load(in);
			this.name = properties.getProperty(PRODUCT_NAME_PROPERTY, productId);
			this.description = properties.getProperty(PRODUCT_DESCRIPTION_PROPERTY, "No description provided");
			this.type = Type.valueOf(properties.getProperty(PRODUCT_TYPE_PROPERTY, "UNKNOWN"));
			for (int b : NumberUtils.intArrayFromString(properties.getProperty(PRODUCT_AVAILABLE_BUILDS_PROPERTY, ""), ",")) {
				buildIds.add(b);
				builds.put(b, new Build(b));
			}
		} catch (Exception e) {
			this.status = Status.UNAVAILABLE;
		}
	}
	
	@Override
	public Status getStatus() {
		return this.status;
	}
	
	@Override
	public String getId() {
		return productId;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<Integer> getBuilds() {
		return buildIds;
	}

	@Override
	public Properties getProperties() {
		return properties;
	}

	@Override
	public Properties getBuildProperties(int build) {
		return builds.get(build).properties;
	}

	@Override
	public Version getBuildVersion(int build) {
		return builds.get(build).version;
	}
	
	@Override
	public boolean isInstalled(int build) {
		return builds.get(build) != null && builds.get(build).locallyAvailable; 
	}

	@Override
	public boolean isAvailable(int build) {
		return builds.get(build) != null && builds.get(build).remotelyAvailable; 
	}

	@Override
	public String getDescription() {
		return description;
	}
	
	public File getLocalWorkspace() {
		return localWorkspace;
	}
	
	public Build getBuild(int build) {
		return builds.get(build);
	}
}
