package org.integratedmodelling.controlcenter.product;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.integratedmodelling.controlcenter.ControlCenter;
import org.integratedmodelling.controlcenter.api.IInstance;
import org.integratedmodelling.controlcenter.api.IProduct;
import org.integratedmodelling.controlcenter.product.Distribution.SyncListener;
import org.integratedmodelling.controlcenter.product.Product.Build;

public class Instance implements IInstance {

	private Product product;
	private Status status = Status.STOPPED;

	public Instance(Product product) {
		this.product = product;
	}

	@Override
	public IProduct getProduct() {
		return product;
	}

	@Override
	public Status getStatus() {
		return status;
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

	@Override
	public List<Integer> getInstalledBuilds() {
		List<Integer> ret = new ArrayList<>();
		File ws = this.product.getLocalWorkspace();
		for (File bws : ws.listFiles()) {
			if (bws.isDirectory() && new File(bws + File.separator + "filelist.txt").exists()) {
				ret.add(Integer.parseInt(bws.getName()));
			}
		}

		/*
		 * most recent first
		 */
		ret.sort((o1, o2) -> o2.compareTo(o1));

		return ret;
	}

	@Override
	public Distribution download(int buildNumber, SyncListener listener) {

		Build build = this.product.getBuild(buildNumber);
		if (build != null) {
			
			int previous = -1;
			for (int n : getInstalledBuilds()) {
				if (n < buildNumber) {
					previous = n;
					break;
				}
			}
			
			if (previous > 0) {
				/*
				 * preload the worspace with the previous distribution for incremental
				 * download.
				 */
				File previousWorkspace = new File(this.product.getLocalWorkspace() + File.separator + previous);
				if (previousWorkspace.isDirectory()) {
					build.workspace.mkdirs();
					try {
						FileUtils.copyDirectory(previousWorkspace, build.workspace, true);
					} catch (IOException e) {
						// screw it
					}
				}
			}
			
			Distribution ret = new Distribution(build.url, build.workspace);
			ret.setListener(listener);
			ret.sync();
			return ret;
		}

		throw new RuntimeException("Internal error: no build for download");
	}

}
