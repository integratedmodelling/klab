package org.integratedmodelling.controlcenter.product;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import org.integratedmodelling.controlcenter.ControlCenter;
import org.integratedmodelling.controlcenter.api.IInstance;
import org.integratedmodelling.controlcenter.api.IProduct;

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

	private ProductService() {

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

		/*
		 * synchronize local products
		 */
		initialize(this.currentBranch);

	}

	public void setToBranch(String branch) {
		initialize(branch);
	}

	/**
	 * Call at beginning and when the branch settings is changed, followed by
	 * ControlCenter.INSTANCE.setupUI().
	 * 
	 * @param branch
	 */
	public void initialize(String branch) {
		for (String productId : products) {
			IProduct product = new Product(KLAB_REPOSITORY_BASE_URL + "/" + branch, productId,
					new File(this.binaryWorkspace + File.separator + branch));
			localInstances.put(productId, new Instance(product));
		}
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

}
