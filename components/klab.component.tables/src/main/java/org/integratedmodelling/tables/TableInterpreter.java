package org.integratedmodelling.tables;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.Set;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.exceptions.KlabIOException;

/**
 * @author Ferd
 *
 */
public abstract class TableInterpreter implements ITableInterpreter {
	
	
	
	IGeometry mergeGeometry(IResource resource, IResource distributing) {

		/*
		 * space must create the union of all the represented shapes after joining through attribute
		 */
		
		return null;
	}

	@Override
	public void categorize(IResource resource, IParameters<String> parameters) {
		// TODO Auto-generated method stub
		ITable<?> table = this.getTable(resource, null);
		Set<Object> categories = new LinkedHashSet<>();
		for (Object o : table.asList(parameters.get("dimension"))) {
			categories.add(o);
		}
		File file = new File(resource.getLocalPath() + File.separator + "code_" + parameters.get("categorization") + ".properties");
		try (OutputStream out = new FileOutputStream(file)) {
			PrintWriter printer = new PrintWriter(out);
			for (Object o : categories) {
				printer.println("category." + o + "=");
			}
		} catch (Exception e) {
			throw new KlabIOException(e);
		}
	}

}
