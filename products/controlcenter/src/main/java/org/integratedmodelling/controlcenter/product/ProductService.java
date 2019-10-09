package org.integratedmodelling.controlcenter.product;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import org.integratedmodelling.controlcenter.ControlCenter;
import org.integratedmodelling.controlcenter.api.IInstance;
import org.integratedmodelling.controlcenter.api.IProduct;
import org.integratedmodelling.controlcenter.runtime.Instance;

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
	public static final String[] products = { PRODUCT_ENGINE, PRODUCT_MODELER, PRODUCT_CONTROL_CENTER };

	private Map<String, IInstance> localInstances = Collections.synchronizedMap(new HashMap<>());
	private String currentBranch = null;

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

		/*
		 * synchronize local products
		 */
		initialize(this.currentBranch);

	}

	public void setToBranch(String branch) {
		initialize(branch);
	}

	private void initialize(String branch) {
		for (String productId : products) {
			IProduct product = new Product(KLAB_REPOSITORY_BASE_URL, productId);
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
		return null;
	}

	/**
	 * 
	 * @param product
	 * @return
	 */
	public Future<IProduct> update(IProduct product) {
		return null;
	}

}
