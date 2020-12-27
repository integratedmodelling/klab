package org.integratedmodelling.tables.adapter.xls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kdl.api.IKdlActuator;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.ResourceAdapter;
import org.integratedmodelling.klab.kim.Prototype;
import org.integratedmodelling.tables.adapter.TableAdapter;

import com.google.common.collect.Sets;

@ResourceAdapter(type = "xls", version = Version.CURRENT, canCreateEmpty = false, handlesFiles = true)
public class XLSAdapter extends TableAdapter {

	public static final String ID = "xls";
	
	/**
	 * All recognized primary file extensions - CSV and the Excel formats.
	 */
	public static Set<String> fileExtensions = Sets.newHashSet("csv", "xls", "xlsx");

	public XLSAdapter() {
		super(ID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<IPrototype> getResourceConfiguration() {
		List<IPrototype> ret = new ArrayList<>();
		for (IKdlActuator proto : Dataflows.INSTANCE.declare(
				getClass().getClassLoader().getResource("components/org.integratedmodelling.table/adapter/xls.kdl"))
				.getActuators()) {
			ret.add(new Prototype(proto, null));
		}
		return ret;
	}

	@Override
	public String getName() {
		return ID;
	}
}
