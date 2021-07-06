package org.integratedmodelling.tables.commands.cdm;

import java.io.IOException;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.exceptions.KlabIOException;

import ucar.nc2.Variable;
import ucar.nc2.dataset.NetcdfDataset;

public class List implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {

		boolean verbose = call.getParameters().get("verbose", false);
		String ret = "";
		for (Object id : call.getParameters().get("arguments", java.util.List.class)) {
			ret += (ret.isEmpty() ? "" : "\n\n") + id + ":\n" + describe(id.toString(), verbose);
		}
		return ret;
	}

	private String describe(String url, boolean verbose) {

		String ret = "";
		NetcdfDataset ncd = null;
		try {
			ncd = NetcdfDataset.openDataset(url);
			ret += ncd.getDetailInfo() + "\n\n";
			ret += "VARIABLES:\n";
			for (Variable variable : ncd.getVariables()) {
				ret += "  " + variable.getShortName() + "(full name: " + variable.getFullName() + "): "
						+ variable.getDescription() + "\n";
			}

		} catch (IOException e) {
			throw new KlabIOException(e);
		} finally {
			if (null != ncd)
				try {
					ncd.close();
				} catch (IOException e) {
					throw new KlabIOException(e);
				}
		}

		return ret;
	}

}

