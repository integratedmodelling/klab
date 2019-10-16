package org.integratedmodelling.controlcenter.product;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.controlcenter.ControlCenter;
import org.integratedmodelling.controlcenter.api.IInstance;
import org.integratedmodelling.controlcenter.api.IProduct;
import org.integratedmodelling.controlcenter.runtime.EngineInstance;
import org.integratedmodelling.controlcenter.runtime.InstallerInstance;
import org.integratedmodelling.controlcenter.runtime.ModelerInstance;

public enum ProductService {

	INSTANCE;

	public static final String PRODUCT_ENGINE = "cli";
	public static final String PRODUCT_MODELER = "kmodeler";
	public static final String PRODUCT_CONTROL_CENTER = "controlcenter";
	public static final String PRODUCTION_BRANCH = "master";
	public static final String DEVELOP_BRANCH = "develop";
	public static final String KLAB_REPOSITORY_BASE_URL = "http://www.integratedmodelling.org/downloads/products";

	/**
	 * The products we manage.
	 */
	private String[] products = new String[] { PRODUCT_ENGINE, PRODUCT_MODELER, PRODUCT_CONTROL_CENTER };
	private Map<String, IInstance> localInstances = Collections.synchronizedMap(new HashMap<>());
	private String currentBranch = null;
	private File binaryWorkspace;

	/**
	 * Set to an existing engine build number ONLY when user selects the build from
	 * the runtime chooser (in the CP). Chosen build in settings does not affect
	 * this one and is overridden by it.
	 */
	private int chosenBuild = -1;

	public static class BuildStatus {
		/**
		 * latest available online, -1 if unknown or nothing available.
		 */
		public int latest = -1;

		/**
		 * all installed.
		 */
		public List<Integer> installed = new ArrayList<>();

		/**
		 * chosen build for execution, -1 if nothing is installed. User may have chosen
		 * an earlier build either in settings (persisted) or at runtime (not
		 * persisted).
		 */
		public int chosen = -1;
	}

	private ProductService() {
		/*
		 * synchronize local products
		 */
		initialize();
	}

	/**
	 * Status of the entire system, using the engine build as representative of the
	 * modeler's as well. Integrates any choice made at runtime re: which build to
	 * use within the available builds.
	 * 
	 * @author ferdinando.villa
	 *
	 */
	public BuildStatus getBuildStatus() {

		BuildStatus ret = new BuildStatus();
		IInstance engine = getInstance(PRODUCT_ENGINE);

		if (engine != null) {

			ret.installed.addAll(engine.getInstalledBuilds());
			if (engine.getProduct().getBuilds().size() > 0) {
				ret.latest = engine.getProduct().getBuilds().get(0);
			}
		}

		if (chosenBuild >= 0) {
			ret.chosen = chosenBuild;
		} else {
			ret.chosen = ret.latest;
		}

		return ret;
	}

	/**
	 * Call at beginning.
	 * 
	 * @param branch
	 */
	public void initialize() {

		/*
		 * initialize properties
		 */
		this.currentBranch = ControlCenter.INSTANCE.getProperties()
				.getProperty(ControlCenter.PRODUCTION_BRANCH_PROPERTY, PRODUCTION_BRANCH);
		if (ControlCenter.INSTANCE.getSettings().useDevelop()) {
			this.currentBranch = ControlCenter.INSTANCE.getProperties()
					.getProperty(ControlCenter.DEVELOP_BRANCH_PROPERTY, DEVELOP_BRANCH);
		}

		this.binaryWorkspace = ControlCenter.INSTANCE.getSettings().getProductDirectory();

		for (String productId : products) {
			
			Product product = new Product(KLAB_REPOSITORY_BASE_URL + "/" + this.currentBranch, productId,
					new File(this.binaryWorkspace + File.separator + this.currentBranch));

			IInstance instance = null;

			switch (productId) {
			case PRODUCT_ENGINE:
				instance = new EngineInstance(product);
				break;
			case PRODUCT_MODELER:
				instance = new ModelerInstance(product);
				break;
			default:
				instance = new InstallerInstance(product);
			}
			localInstances.put(productId, instance);
		}
	}
	
	public boolean switchBranch(String branch) {

		if (this.currentBranch.equals(branch)) {
			return false;
		}
		
		this.currentBranch = branch;
		this.binaryWorkspace = ControlCenter.INSTANCE.getSettings().getProductDirectory();

		localInstances.clear();
		
		for (String productId : products) {
			
			Product product = new Product(KLAB_REPOSITORY_BASE_URL + "/" + this.currentBranch, productId,
					new File(this.binaryWorkspace + File.separator + this.currentBranch));

			IInstance instance = null;

			switch (productId) {
			case PRODUCT_ENGINE:
				instance = new EngineInstance(product);
				break;
			case PRODUCT_MODELER:
				instance = new ModelerInstance(product);
				break;
			default:
				instance = new InstallerInstance(product);
			}
			localInstances.put(productId, instance);
		}
		
		return true;
	}
	
	/**
	 * Return a product, which may be locally unavailable and needs syncronization.
	 * 
	 * @param id
	 * @return
	 */
	public IProduct getProduct(String id) {
		return localInstances.containsKey(id) ? localInstances.get(id).getProduct() : null;
	}

	/**
	 * Return the local instance of a product.
	 * 
	 * @param id
	 * @return
	 */
	public IInstance getInstance(String id) {
		return localInstances.get(id);
	}

	public void setChosenBuild(Integer build) {
		this.chosenBuild = build;
	}

}
