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
import java.util.logging.Level;

import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.client.utils.JsonUtils;
import org.integratedmodelling.klab.ide.model.KlabPeer.Sender;
import org.integratedmodelling.klab.ide.navigator.e3.KlabNavigator;
import org.integratedmodelling.klab.ide.navigator.model.EProject;
import org.integratedmodelling.klab.ide.navigator.model.beans.EResourceReference;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.rest.Capabilities;
import org.integratedmodelling.klab.rest.CompileNotificationReference;
import org.integratedmodelling.klab.rest.LocalResourceReference;
import org.integratedmodelling.klab.rest.NamespaceCompilationResult;
import org.integratedmodelling.klab.rest.ProjectReference;
import org.integratedmodelling.klab.rest.ResourceAdapterReference;
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
	 * connected. These get updated when a Capabilities bean is received.
	 */
	Set<String> projectsSynchronized = Collections.synchronizedSet(new HashSet<>());

	/*
	 * All resources read or imported, along with their status, indexed by project
	 * ID and by URN within each project. Used by the navigator and the Resources
	 * view.
	 */
	private Map<String, Map<String, EResourceReference>> resourceCatalog = Collections.synchronizedMap(new HashMap<>());
	private List<ResourceAdapterReference> resourceAdapters = new ArrayList<>();
	private Map<String, NamespaceCompilationResult> namespaceStatus = new HashMap<>();

	class CompileInfo {
		List<CompileNotificationReference> errors = new ArrayList<>();
		List<CompileNotificationReference> warnings = new ArrayList<>();
		List<CompileNotificationReference> info = new ArrayList<>();
	}

	private Map<String, CompileInfo> compileInfo = Collections.synchronizedMap(new HashMap<>());

	public void synchronizeProjectResources(String projectName, File projectRoot) {

		if (!projectsSynchronized.contains(projectName)) {

			List<String> urns = new ArrayList<>();

			projectsSynchronized.add(projectName);
			File resourceFolder = new File(projectRoot + File.separator + "resources");
			if (resourceFolder.exists()) {
				for (File rfolder : resourceFolder.listFiles()) {
					if (rfolder.isDirectory()) {
						File rdesc = new File(rfolder + File.separator + "resource.json");
						if (rdesc.exists()) {
							ResourceReference resource = JsonUtils.load(rdesc, ResourceReference.class);
							// FIXME this should be removed once the local name is mandatory on creation
							if (resource.getLocalName() == null) {
								resource.setLocalName(
										org.integratedmodelling.klab.utils.Path.getLast(resource.getUrn(), ':'));
							}
							Map<String, EResourceReference> catalog = resourceCatalog.get(projectName);
							if (catalog == null) {
								catalog = new LinkedHashMap<>();
								resourceCatalog.put(projectName, catalog);
							}
							catalog.put(resource.getUrn(), new EResourceReference(resource));
							urns.add(resource.getUrn());
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
		synchronizeProjectResources(project.getName(), project.getRoot());
		return resourceCatalog.containsKey(project.getName()) ? resourceCatalog.get(project.getName()).values()
				: new ArrayList<>();
	}

	/**
	 * Get all project resources
	 * 
	 * @return
	 */
	public Collection<EResourceReference> getProjectResources() {
		List<EResourceReference> ret = new ArrayList<>();
		for (String s : resourceCatalog.keySet()) {
			ret.addAll(resourceCatalog.get(s).values());
		}
		return ret;
	}

	/*
	 * sync the resource status with the capabilities from the engine
	 */
	private void synchronizeProjectResources(List<ProjectReference> localWorkspaceProjects) {
		for (ProjectReference project : localWorkspaceProjects) {
			if (resourceCatalog.containsKey(project.getName())) {
				for (LocalResourceReference resource : project.getLocalResources()) {
					if (resourceCatalog.get(project.getName()).containsKey(resource.getUrn())) {
						resourceCatalog.get(project.getName()).get(resource.getUrn()).setOnline(resource.isOnline());
					}
				}
			}
		}
	}

	/**
	 * All the resource adapters supported by the engine.
	 * 
	 * @return resource adapters
	 */
	public Collection<ResourceAdapterReference> getResourceAdapters() {
		return resourceAdapters;
	}

	public ResourceAdapterReference getResourceAdapter(String adapterId) {
		for (ResourceAdapterReference adapter : resourceAdapters) {
			if (adapter.getName().equals(adapterId)) {
				return adapter;
			}
		}
		return null;
	}
	
	/*
	 * called by the session peer, the true receiver for the message
	 */
	public void notifyResourceImport(ResourceReference resource) {
		Map<String, EResourceReference> list = resourceCatalog.get(resource.getProjectName());
		if (list == null) {
			list = new HashMap<>();
			resourceCatalog.put(resource.getProjectName(), list);
		}
		list.put(resource.getUrn(), new EResourceReference(resource, true));
		KlabNavigator.refresh();
		Eclipse.INSTANCE.notification("New resource imported",
				"The resource with URN " + resource.getUrn()
						+ " is now available and online. It can be referenced within the " + resource.getProjectName()
						+ " project as " + resource.getLocalName());
	}

	public void notifyResourceDeleted(ResourceReference resource) {
		Map<String, EResourceReference> list = resourceCatalog.get(resource.getProjectName());
		if (list == null) {
			list = new HashMap<>();
			resourceCatalog.remove(resource.getProjectName(), list);
		}
		KlabNavigator.refresh();
		Eclipse.INSTANCE.notification("Resource deleted", "The resource with URN " + resource.getUrn()
				+ " was deleted from project " + resource.getProjectName());
	}

	/*
	 * This does all the work of keeping the books in order, recording any
	 * modification, and notifying the UI any time a change must be reported.
	 */
	private void handleMessage(IMessage message) {
		switch (message.getType()) {
		case EngineUp:
			synchronizeProjectResources(message.getPayload(Capabilities.class).getLocalWorkspaceProjects());
			resourceAdapters.addAll(message.getPayload(Capabilities.class).getResourceAdapters());
			KlabNavigator.refresh();
			break;
		case EngineDown:
			for (String project : resourceCatalog.keySet()) {
				for (String urn : resourceCatalog.get(project).keySet()) {
					resourceCatalog.get(project).get(urn).setOnline(false);
				}
			}
			resourceAdapters.clear();
			KlabNavigator.refresh();
			break;
		case ProjectFileAdded:

			break;
		default:
			break;
		}
	}

	public void setNamespaceStatus(String name, NamespaceCompilationResult report) {
		this.namespaceStatus.put(name, report);
	}

	/**
	 * Return the latest available compilation report for this namespace from the
	 * engine.
	 * 
	 * @param name
	 * @return
	 */
	public NamespaceCompilationResult getNamespaceStatus(String name) {
		return this.namespaceStatus.get(name);
	}

	/**
	 * Find the first project containing the URN and return the corresponding
	 * resource.
	 * 
	 * @param urn
	 * @return
	 */
	public EResourceReference getResource(String urn) {
		for (Map<String, EResourceReference> container : resourceCatalog.values()) {
			EResourceReference ret = container.get(urn);
			if (ret != null) {
				return ret;
			}
		}
		return null;
	}

	public void resetCompileNotifications(String namespaceId) {
		compileInfo.put(namespaceId, new CompileInfo());
	}

	public void recordCompileNotification(CompileNotificationReference inot) {

		CompileInfo ci = compileInfo.get(inot.getNamespaceId());

		if (inot.getLevel() == Level.SEVERE.intValue()) {
			ci.errors.add(inot);
		} else if (inot.getLevel() == Level.WARNING.intValue()) {
			ci.warnings.add(inot);
		} else if (inot.getLevel() == Level.INFO.intValue()) {
			ci.info.add(inot);
		}
	}

	public List<CompileNotificationReference> getWarnings(String namespaceId) {
		return compileInfo.containsKey(namespaceId) ? compileInfo.get(namespaceId).warnings : new ArrayList<>();
	}

	public List<CompileNotificationReference> getErrors(String namespaceId) {
		return compileInfo.containsKey(namespaceId) ? compileInfo.get(namespaceId).errors : new ArrayList<>();
	}

}
