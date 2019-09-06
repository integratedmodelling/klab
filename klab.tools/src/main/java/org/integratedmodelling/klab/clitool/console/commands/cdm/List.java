package org.integratedmodelling.klab.clitool.console.commands.cdm;

import java.io.IOException;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodellling.cdm.CDMComponent;

import ucar.nc2.Variable;
import ucar.nc2.dataset.NetcdfDataset;

public class List implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) throws Exception {

		boolean verbose = call.getParameters().get("verbose", false);
		return describe(call.getParameters().get("url", String.class), verbose);
	}

	private String describe(String url, boolean verbose) {

		String ret = "";
		NetcdfDataset ncd = null;
		try {
			ncd = CDMComponent.openAuthenticated(url);
			ret += ncd.getDetailInfo() + "\n\n";
			ret += "VARIABLES:\n";
			for (Variable variable : ncd.getVariables()) {
				ret += "  " + variable.getShortName() + "(full name: " + variable.getFullName() + "): "
						+ variable.getDescription() + "\n";
			}

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
