package org.integratedmodelling.tables.adapter.cdm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.kdl.api.IKdlActuator;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.ResourceAdapter;
import org.integratedmodelling.klab.kim.Prototype;
import org.integratedmodelling.tables.adapter.TableAdapter;

@ResourceAdapter(type = "cdm", version = Version.CURRENT)
public class CDMAdapter extends TableAdapter {

	public static final String ID = "cdm";

	public CDMAdapter() {
		super(ID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return ID;
	}

	@Override
	public Collection<IPrototype> getResourceConfiguration() {
		List<IPrototype> ret = new ArrayList<>();
		for (IKdlActuator proto : Dataflows.INSTANCE.declare(
				getClass().getClassLoader().getResource("components/org.integratedmodelling.table/adapter/cdm.kdl"))
				.getActuators()) {
			ret.add(new Prototype(proto, null));
		}
		return ret;
	}
}
