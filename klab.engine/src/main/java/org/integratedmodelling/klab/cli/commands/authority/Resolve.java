package org.integratedmodelling.klab.cli.commands.authority;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Authorities;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.knowledge.IAuthority.Identity;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.StringUtil;

public class Resolve implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) throws Exception {
		String authority = null;
		List<String> identities = new ArrayList<>();
		for (Object arg : call.getParameters().get("arguments", java.util.List.class)) {
			if (authority == null) {
				authority = arg.toString();
			} else {
				identities.add(arg.toString());
			}
		}

		StringBuffer ret = new StringBuffer();

		String identity = "";
		for (String id : identities) {
			identity += (identity.isEmpty() ? "" : " ") + id;
		}

		Identity resolved = Authorities.INSTANCE.getIdentity(authority, identity);
		if (resolved == null) {
			ret.append("\nIdentity " + authority + ":" + identity + ": UNKNOWN");
		}
		ret.append("\nIdentity " + authority + ":" + identity + ":\n");
		ret.append(StringUtil.indent(JsonUtils.printAsJson(resolved), 3));

		return ret.toString();
	}

}
