package org.integratedmodelling.controlcenter.runtime;

import java.util.function.Consumer;

import org.apache.commons.exec.CommandLine;
import org.integratedmodelling.controlcenter.product.Instance;
import org.integratedmodelling.controlcenter.product.Product;

public class InstallerInstance extends Instance {

	public InstallerInstance(Product product) {
		super(product);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected CommandLine getCommandLine(int build) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isRunning() {
		return false;
	}

	@Override
	public void pollStatus(Consumer<Status> listener) {
	}
	
}
