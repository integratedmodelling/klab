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
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.client.utils.JsonUtils;
import org.integratedmodelling.klab.common.CompileInfo;
import org.integratedmodelling.klab.ide.model.KlabPeer.Sender;
import org.integratedmodelling.klab.ide.navigator.e3.KlabNavigator;
import org.integratedmodelling.klab.ide.navigator.model.EModel;
import org.integratedmodelling.klab.ide.navigator.model.ENamespace;
import org.integratedmodelling.klab.ide.navigator.model.ENavigatorItem;
import org.integratedmodelling.klab.ide.navigator.model.EProject;
import org.integratedmodelling.klab.ide.navigator.model.EResource;
import org.integratedmodelling.klab.ide.navigator.model.EResourceFolder;
import org.integratedmodelling.klab.ide.navigator.model.EScriptFolder;
import org.integratedmodelling.klab.ide.navigator.model.ETestFolder;
import org.integratedmodelling.klab.ide.navigator.model.beans.EResourceReference;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.rest.Capabilities;
import org.integratedmodelling.klab.rest.LocalResourceReference;
import org.integratedmodelling.klab.rest.NamespaceCompilationResult;
import org.integratedmodelling.klab.rest.NetworkReference;
import org.integratedmodelling.klab.rest.NodeReference;
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
	private AtomicReference<NetworkReference> network = new AtomicReference<>();

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

	void updateNetwork(NetworkReference network) {
		this.network.set(network);
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

	public NetworkReference getNetwork() {
		return this.network.get();
	}
	
	/*
	 * sync the resource status and project errors with the capabilities from the
	 * engine
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
			for (NamespaceCompilationResult compilationResult : project.getCompilationReports()) {
				Kim.INSTANCE.updateErrors(compilationResult);
			}
		}
		Eclipse.INSTANCE.refreshOpenEditors();
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
		EResourceReference eref = new EResourceReference(resource, true);
		list.put(resource.getUrn(), eref);
		try {
			Eclipse.INSTANCE.getProject(resource.getProjectName()).getFolder(IKimProject.RESOURCE_FOLDER)
					.refreshLocal(IFolder.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			// just continue; this execs in a pretty controlled sandbox so it shouldn't
			// happen
		}
		KlabNavigator.refresh();
		Eclipse.INSTANCE.refreshOpenEditors();
		Eclipse.INSTANCE.notification("New resource imported",
				"Resource <b>" + resource.getUrn()
						+ "</b> is available and online.\nIt can be referenced within the <b>" + resource.getProjectName()
						+ "</b> project as <b>" + resource.getLocalName()+"</b>.");
	}

	public void notifyResourceUpdated(ResourceReference resource) {
		Map<String, EResourceReference> list = resourceCatalog.get(resource.getProjectName());
		if (list != null) {
			list.remove(resource.getUrn());
		} else {
			list = new HashMap<>();
			resourceCatalog.put(resource.getProjectName(), list);
		}
		EResourceReference eref = new EResourceReference(resource, true);
		list.put(resource.getUrn(), eref);
		try {
			Eclipse.INSTANCE.getProject(resource.getProjectName()).getFolder(IKimProject.RESOURCE_FOLDER)
					.refreshLocal(IFolder.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			// just continue; this execs in a pretty controlled sandbox so it shouldn't
			// happen
		}
		KlabNavigator.refresh();
		Eclipse.INSTANCE.refreshOpenEditors();
		Eclipse.INSTANCE.notification("Resource updated",
				"Resource <b>" + resource.getUrn() + "</b> was updated by the engine.");
	}

	public void notifyResourceDeleted(ResourceReference resource) {
		Map<String, EResourceReference> list = resourceCatalog.get(resource.getProjectName());
		if (list != null) {
			list.remove(resource.getUrn());
		}
		try {
			Eclipse.INSTANCE.getProject(resource.getProjectName()).getFolder(IKimProject.RESOURCE_FOLDER)
					.refreshLocal(IFolder.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			// just continue; this execs in a pretty controlled sandbox so it shouldn't
			// happen
		}
		Eclipse.INSTANCE.refreshOpenEditors();
		KlabNavigator.refresh();
		Eclipse.INSTANCE.notification("Resource deleted", "Resource <b>" + resource.getUrn()
				+ "</b> deleted from project <b>" + resource.getProjectName() + "</b>.");
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
			Eclipse.INSTANCE.refreshOpenEditors();
			KlabNavigator.refresh();
			break;
		case EngineDown:
			for (String project : resourceCatalog.keySet()) {
				for (String urn : resourceCatalog.get(project).keySet()) {
					resourceCatalog.get(project).get(urn).setOnline(false);
				}
			}
			resourceAdapters.clear();
			Eclipse.INSTANCE.refreshOpenEditors();
			KlabNavigator.refresh();
			break;
		case ProjectFileAdded:
			System.out.println("PITONE");
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
		String kurn = urn;
		if (urn.contains("#")) {
			kurn = kurn.substring(0, urn.indexOf("#"));
		}
		for (Map<String, EResourceReference> container : resourceCatalog.values()) {
			EResourceReference ret = container.get(kurn);
			if (ret != null) {
				return ret;
			}
		}
		return null;
	}

	public boolean hasNotifications(Object item, Level level) {

		if (item == null) {
			return level.equals(Level.SEVERE);
		} else if (item instanceof EProject) {
			for (IKimNamespace namespace : Kim.INSTANCE.getProject(((EProject) item).getName()).getNamespaces()) {
				if (hasNotifications(namespace, level)) {
					return true;
				}
			}
		} else if (item instanceof EResource) {
			if (level.equals(Level.SEVERE)) {
				return (((EResource) item).getResource().isError());
			}
		} else if (item instanceof EResourceFolder) {
			for (ENavigatorItem resource : (((EResourceFolder) item).getEChildren())) {
				if (hasNotifications(resource, level)) {
					return true;
				}
			}
		} else if (item instanceof EScriptFolder) {
			for (Object resource : (((EScriptFolder) item).getEChildren())) {
				if (hasNotifications(resource, level)) {
					return true;
				}
			}
		} else if (item instanceof ETestFolder) {
			for (ENavigatorItem resource : (((ETestFolder) item).getEChildren())) {
				if (hasNotifications(resource, level)) {
					return true;
				}
			}
		} else if (item instanceof ENamespace) {
			return hasNotifications(Kim.INSTANCE.getNamespace(((ENamespace) item).getName()), level);
		} else if (item instanceof IKimNamespace) {
			if (level.equals(Level.SEVERE) && ((IKimNamespace) item).isErrors()) {
				return true;
			}
			if (level.equals(Level.WARNING) && ((IKimNamespace) item).isWarnings()) {
				return true;
			}
			CompileInfo info = Kim.INSTANCE.getCompileInfo(((IKimNamespace) item).getName());
			if (info != null) {
				if (level.equals(Level.SEVERE)) {
					return info.getErrors().size() > 0;
				} else if (level.equals(Level.WARNING)) {
					return info.getWarnings().size() > 0;
				} else if (level.equals(Level.INFO)) {
					return info.getInfo().size() > 0;
				}
			}
		} else if (item instanceof EModel) {
			if (level.equals(Level.SEVERE) && ((EModel) item).isErrors()) {
				return true;
			}
			if (level.equals(Level.WARNING) && ((EModel) item).isWarnings()) {
				return true;
			}
			CompileInfo info = Kim.INSTANCE.getCompileInfo(((EModel) item).getNamespace());
			if (info != null) {
				if (level.equals(Level.SEVERE)) {
					return info.getErrors(((EModel) item).getName()).size() > 0;
				} else if (level.equals(Level.WARNING)) {
					return info.getWarnings(((EModel) item).getName()).size() > 0;
				} else if (level.equals(Level.INFO)) {
					return info.getInfo(((EModel) item).getName()).size() > 0;
				}
			}
		}
		return false;
	}

	public List<NodeReference> getPublishingNodes(String adapter) {
		List<NodeReference> ret = new ArrayList<>();
		NetworkReference network = this.network.get();
		if (network != null) {
			for (NodeReference node : network.getNodes().values()) {
				if (network.getPublishing().contains(node.getId()) && node.getAdapters().contains(adapter)) {
					ret.add(node);
				}
			}
		}
		return ret;
	}

	public void updateResource(LocalResourceReference resource) {
		EResourceReference res = getResource(resource.getUrn());
		if (res != null) {
			res.setOnline(resource.isOnline());
			res.setError(resource.isError());
		}
	}

}
