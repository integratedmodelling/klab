package org.integratedmodelling.klab.node.controllers;

import java.io.File;
import java.util.Map;

import javax.ws.rs.QueryParam;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.services.IConfigurationService;
import org.integratedmodelling.klab.common.monitoring.TicketManager;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.engine.extensions.Component;
import org.integratedmodelling.klab.node.auth.Role;
import org.integratedmodelling.klab.rest.TicketResponse;
import org.integratedmodelling.klab.utils.FileUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// FIXME this must be admin, at the moment it won't let my admin user in
//@Secured({ Role.ADMINISTRATOR, Role.SYSTEM })
@Secured(Role.ENGINE)
public class AdminController {

	@GetMapping(value = API.NODE.ADMIN.COMPONENT_SETUP, produces = "application/json")
	public TicketResponse.Ticket setupComponent(@PathVariable String component) {
		Component comp = Extensions.INSTANCE.getComponent(component);
		if (comp != null) {
			return TicketManager.encode(comp.setup());
		}
		return null;
	}

	@GetMapping(value = API.NODE.ADMIN.GET_PROPERTY, produces = "text/plain")
	public String getProperty(@PathVariable String property) {
		return Configuration.INSTANCE.getProperty(property, "Not found");
	}

	@PutMapping(value = API.NODE.ADMIN.SET_PROPERTY)
	public void setProperty(@PathVariable String property, @QueryParam(value = "value") String value) {
		Configuration.INSTANCE.getProperties().setProperty(property, value);
		Configuration.INSTANCE.save();
	}

	@GetMapping(value = API.NODE.ADMIN.GET_LOG, produces = "text/plain")
	public String getLog(@PathVariable int lines) {
		String ret = "No logs available or configured";
		File file = new File(Configuration.INSTANCE.getProperty(IConfigurationService.KLAB_LOG_FILE,
				Configuration.INSTANCE.getDataPath("logs") + File.separator + "klab.log"));
		if (file.canRead()) {
			StringBuffer sbuf = new StringBuffer(4096);
			for (String line : FileUtils.tailFile(file, lines)) {
				sbuf.append(line);
				sbuf.append("\n");
			}
			ret = sbuf.toString();
		}
		return ret;
	}

	@GetMapping(value = API.NODE.ADMIN.COMPONENT_GET_STATUS, produces = "application/json")
	public Map<String, Object> getStatus(@PathVariable String component) {
		Component comp = Extensions.INSTANCE.getComponent(component);
		if (comp != null) {
			Map<String, Object> ret = ((Metadata) comp.getStatus()).getData();
			ret.put("name", component);
			ret.put("version", comp.getVersion());
			return ret;
		}
		return null;
	}

}
