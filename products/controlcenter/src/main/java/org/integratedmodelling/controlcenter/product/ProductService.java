package org.integratedmodelling.controlcenter.product;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import org.integratedmodelling.controlcenter.api.IInstance;
import org.integratedmodelling.controlcenter.api.IProduct;

public enum ProductService {
	
	INSTANCE;
	
	Map<String, IInstance> localInstances = Collections.synchronizedMap(new HashMap<>());
	
	private ProductService() {
		
		/*
		 * read properties
		 */
		
		/*
		 * synchronize local products
		 */
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
