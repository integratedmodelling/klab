package org.integratedmodelling.controlcenter.product;

import org.integratedmodelling.controlcenter.api.IInstance;
import org.integratedmodelling.controlcenter.api.IProduct;

public class Instance implements IInstance {

	private IProduct product;

	public Instance(IProduct product) {
		this.product = product;
	}

	@Override
	public IProduct getProduct() {
		return product;
	}

	@Override
	public Status getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean start() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean stop() {
		// TODO Auto-generated method stub
		return false;
	}

}
