package org.integratedmodelling.klab.ide.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.client.utils.JsonUtils;
import org.integratedmodelling.klab.ide.model.KlabPeer.Sender;
import org.integratedmodelling.klab.ide.navigator.model.EProject;
import org.integratedmodelling.klab.ide.navigator.model.beans.EResourceReference;
import org.integratedmodelling.klab.ide.navigator.model.beans.ETaskReference;
import org.integratedmodelling.klab.rest.ResourceReference;

/**
 * Holder of state for sessions and projects. Singleton available through the
 * Activator independent from engine status.
 * 
 * @author ferdinando.villa
 *
 */
public class Klab {

	KlabPeer peer = new KlabPeer(Sender.ANY, (message) -> handleMessage(message));

	/*
	 * initial resource synchronization, happens once at launch before the engine is
	 * connected
	 */
	Set<String> projectsSynchronized = Collections.synchronizedSet(new HashSet<>());

	/*
	 * All resources read or imported, along with their status, indexed by project
	 * ID and by URN within each project. Used by the navigator and the Resources
	 * view.
	 */
	private Map<String, Map<String, EResourceReference>> resourceCatalog = Collections.synchronizedMap(new HashMap<>());

	/*
	 * all tasks in the session, indexed by ID of root context. Each task reference
	 * is linked to child descriptors for dataflow, artifacts produced and log
	 * entries. Used to populate the task tree in the runtime view. Maintains
	 * chronological order.
	 */
	private Map<String, List<ETaskReference>> taskCatalog = Collections.synchronizedMap(new LinkedHashMap<>());

	private void synchronizeProjectResources(EProject project) {
		if (!projectsSynchronized.contains(project.getName())) {
			projectsSynchronized.add(project.getName());
			File resourceFolder = new File(project.getRoot() + File.separator + "resources");
			if (resourceFolder.exists()) {
				for (File rfolder : resourceFolder.listFiles()) {
					if (rfolder.isDirectory()) {
						File rdesc = new File(rfolder + File.separator + "resource.json");
						if (rdesc.exists()) {
							ResourceReference resource = JsonUtils.load(rdesc, ResourceReference.class);
							Map<String, EResourceReference> catalog = resourceCatalog.get(project.getName());
							if (catalog == null) {
								catalog = new LinkedHashMap<>();
								resourceCatalog.put(project.getName(), catalog);
							}
							catalog.put(resource.getUrn(), new EResourceReference(resource));
						}
					}
				}
			}
		}
	}

	/**
	 * Get all the resources in a project, with their updated status.
	 * 
	 * @param project
	 * @return
	 */
	public Collection<EResourceReference> getProjectResources(EProject project) {
		synchronizeProjectResources(project);
		return resourceCatalog.containsKey(project.getName()) ? resourceCatalog.get(project.getName()).values()
				: new ArrayList<>();
	}

	/*
	 * This does all the work of keeping the books in order, recording any
	 * modification, and notifying the UI any time a change must be reported.
	 */
	private void handleMessage(IMessage message) {

	}

}
