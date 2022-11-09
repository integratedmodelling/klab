package org.integratedmodelling.klab.ide.navigator.e3;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.integratedmodelling.kim.api.IKimNamespace.Role;
import org.integratedmodelling.klab.api.data.CRUDOperation;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.model.EActorBehavior;
import org.integratedmodelling.klab.ide.navigator.model.ENamespace;
import org.integratedmodelling.klab.ide.navigator.model.EProject;
import org.integratedmodelling.klab.ide.navigator.model.EResource;
import org.integratedmodelling.klab.ide.navigator.model.EResourceFolder;
import org.integratedmodelling.klab.ide.navigator.model.EScript;
import org.integratedmodelling.klab.ide.navigator.model.EScriptFolder;
import org.integratedmodelling.klab.ide.navigator.model.ETestCase;
import org.integratedmodelling.klab.ide.navigator.model.ETestFolder;
import org.integratedmodelling.klab.ide.navigator.model.beans.EResourceReference;
import org.integratedmodelling.klab.ide.navigator.model.documentation.EDocumentable;
import org.integratedmodelling.klab.ide.navigator.model.documentation.EDocumentationFolder;
import org.integratedmodelling.klab.ide.navigator.model.documentation.EDocumentationItem;
import org.integratedmodelling.klab.ide.navigator.model.documentation.EDocumentationPage;
import org.integratedmodelling.klab.ide.navigator.model.documentation.EReference;
import org.integratedmodelling.klab.ide.ui.wizards.BulkImportResourceWizard;
import org.integratedmodelling.klab.ide.ui.wizards.ExportResourceWizard;
import org.integratedmodelling.klab.ide.ui.wizards.MoveResourceWizard;
import org.integratedmodelling.klab.ide.ui.wizards.NewBehaviorWizard;
import org.integratedmodelling.klab.ide.ui.wizards.NewDocumentationFolderWizard;
import org.integratedmodelling.klab.ide.ui.wizards.NewDocumentationSectionWizard;
import org.integratedmodelling.klab.ide.ui.wizards.NewNamespaceWizard;
import org.integratedmodelling.klab.ide.ui.wizards.NewProjectWizard;
import org.integratedmodelling.klab.ide.ui.wizards.NewResourceWizard;
import org.integratedmodelling.klab.ide.ui.wizards.NewScriptWizard;
import org.integratedmodelling.klab.ide.ui.wizards.PublishResourceWizard;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.ide.views.DocumentationEditor;
import org.integratedmodelling.klab.ide.views.LocalizationView;
import org.integratedmodelling.klab.ide.views.ReferencesEditor;
import org.integratedmodelling.klab.ide.views.ResourceEditor;
import org.integratedmodelling.klab.rest.NodeReference;
import org.integratedmodelling.klab.rest.ProjectModificationNotification;
import org.integratedmodelling.klab.rest.ProjectModificationRequest;
import org.integratedmodelling.klab.rest.ResourceCRUDRequest;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KlabNavigatorActions {

    public static Logger logger = LoggerFactory.getLogger(KlabNavigatorActions.class);
    
	public static void createProject() {
		WizardDialog dialog = new WizardDialog(Eclipse.INSTANCE.getShell(), new NewProjectWizard());
		dialog.create();
		dialog.open();
	}

	public static void deleteProject(EProject project) {
		if (MessageDialog.openConfirm(Eclipse.INSTANCE.getShell(), "Confirm deletion",
				"Delete project " + project.getName() + "? This action cannot be recovered.")) {
			Eclipse.INSTANCE.deleteProject(project);
			if (Activator.engineMonitor().isRunning()) {
				Activator.post(IMessage.MessageClass.ProjectLifecycle, IMessage.Type.DeleteProject,
						new ProjectModificationRequest(project.getName(), null));
			}
		}
	}

	public static void addNamespace(EProject project) {
		WizardDialog dialog = new WizardDialog(Eclipse.INSTANCE.getShell(),
				new NewNamespaceWizard(project.getProject()));
		dialog.create();
		dialog.open();
	}
	
	public static void addBehavior(EProject project) {
		WizardDialog dialog = new WizardDialog(Eclipse.INSTANCE.getShell(),
				new NewBehaviorWizard(project.getProject()));
		dialog.create();
		dialog.open();
	}


	public static void addScenario(EProject project) {

	}

	public static void deleteNamespace(ENamespace namespace, IWorkbenchPage page) {
		if (MessageDialog.openConfirm(Eclipse.INSTANCE.getShell(), "Confirm deletion",
				"Delete namespace " + namespace.getName() + "? This action cannot be recovered.")) {
			Activator.post((message) -> {
				File file = message.getPayload(ProjectModificationNotification.class).getFile();
				Activator.loader().delete(file);
				Eclipse.INSTANCE.closeEditor(file, page);
				KlabNavigator.refresh();
			}, IMessage.MessageClass.ProjectLifecycle, IMessage.Type.DeleteNamespace,
					new ProjectModificationRequest(namespace.getProject().getName(), namespace.getName()));
		}
	}

	public static void addScript(EScriptFolder folder) {
		WizardDialog dialog = new WizardDialog(Eclipse.INSTANCE.getShell(),
				new NewScriptWizard(folder, folder.getEParent(EProject.class).getProject(), Role.SCRIPT));
		dialog.create();
		dialog.open();
	}

	public static void addApplication(EScriptFolder folder) {
		WizardDialog dialog = new WizardDialog(Eclipse.INSTANCE.getShell(),
				new NewScriptWizard(folder, folder.getEParent(EProject.class).getProject(), Role.SCRIPT));
		dialog.create();
		dialog.open();
	}
	
	public static void deleteScript(EScript script, IWorkbenchPage page) {
		if (MessageDialog.openConfirm(Eclipse.INSTANCE.getShell(), "Confirm deletion",
				"Delete script " + script.getName() + "? This action cannot be recovered.")) {
			Activator.post((message) -> {
				File file = message.getPayload(ProjectModificationNotification.class).getFile();
				Activator.loader().delete(file);
				Eclipse.INSTANCE.closeEditor(file, page);
				KlabNavigator.refresh();
			}, IMessage.MessageClass.ProjectLifecycle, IMessage.Type.DeleteScript,
					new ProjectModificationRequest(script.getProject().getName(), script.getName()));
		}
	}
	
	public static void deleteBehavior(EActorBehavior script, IWorkbenchPage page) {
//		if (MessageDialog.openConfirm(Eclipse.INSTANCE.getShell(), "Confirm deletion",
//				"Delete script " + script.getName() + "? This action cannot be recovered.")) {
//			Activator.post((message) -> {
//				File file = message.getPayload(ProjectModificationNotification.class).getFile();
//				Activator.loader().delete(file);
//				Eclipse.INSTANCE.closeEditor(file, page);
//				KlabNavigator.refresh();
//			}, IMessage.MessageClass.ProjectLifecycle, IMessage.Type.DeleteScript,
//					new ProjectModificationRequest(script.getProject().getName(), script.getName()));
//		}
	}
	
	   public static void editLocalization(EActorBehavior script, IWorkbenchPage page) {

	       try {
	            IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
	                    .showView(LocalizationView.ID);
	            if (view != null) {
	                ((LocalizationView) view).loadApplication(script);
	            }
	        } catch (Exception e) {
	            Eclipse.INSTANCE.handleException(e);
	        }
	    }

	public static void addTestCase(ETestFolder folder) {
		WizardDialog dialog = new WizardDialog(Eclipse.INSTANCE.getShell(),
				new NewScriptWizard(folder, folder.getEParent(EProject.class).getProject(), Role.TESTCASE));
		dialog.create();
		dialog.open();
	}

	public static void deleteTestCase(ETestCase testCase, IWorkbenchPage page) {
		if (MessageDialog.openConfirm(Eclipse.INSTANCE.getShell(), "Confirm deletion",
				"Delete test case " + testCase.getName() + "? This action cannot be recovered.")) {
			Activator.post((message) -> {
				File file = message.getPayload(ProjectModificationNotification.class).getFile();
				Activator.loader().delete(file);
				Eclipse.INSTANCE.closeEditor(file, page);
				KlabNavigator.refresh();
			}, IMessage.MessageClass.ProjectLifecycle, IMessage.Type.DeleteTestCase,
					new ProjectModificationRequest(testCase.getProject().getName(), testCase.getName()));
		}
	}

	public static void importResources(EResourceFolder folder) {
		WizardDialog dialog = new WizardDialog(Eclipse.INSTANCE.getShell(), new BulkImportResourceWizard(folder));
		dialog.create();
		dialog.open();
	}

	public static void createResource(EResourceFolder folder) {
		WizardDialog dialog = new WizardDialog(Eclipse.INSTANCE.getShell(), new NewResourceWizard(folder));
		dialog.setPageSize(800, 550);
		dialog.create();
		dialog.open();
	}

	public static void editResource(EResourceReference resource) {
		try {
			IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.showView(ResourceEditor.ID);
			if (view != null) {
				((ResourceEditor) view).loadResource(resource);
			}
		} catch (Exception e) {
			Eclipse.INSTANCE.handleException(e);
		}
	}

	public static void deleteResource(EResource resource) {
		if (MessageDialog.openConfirm(Eclipse.INSTANCE.getShell(), "Confirm deletion",
				"Delete resource " + resource.getId() + "? This action cannot be recovered.")) {
			ResourceCRUDRequest request = new ResourceCRUDRequest();
			request.setOperation(CRUDOperation.DELETE);
			request.getResourceUrns().add(resource.getResource().getUrn());
			Activator.post(IMessage.MessageClass.ResourceLifecycle, IMessage.Type.DeleteLocalResource, request);
		}
	}
	
	public static void publishLocalResource(ResourceReference resource, List<NodeReference> nodes) {
		WizardDialog dialog = new WizardDialog(Eclipse.INSTANCE.getShell(),
				new PublishResourceWizard(resource, nodes));
		dialog.create();
		dialog.open();
	}
	
	public static void exportResource(EResource resource) {
		WizardDialog dialog = new WizardDialog(Eclipse.INSTANCE.getShell(), new ExportResourceWizard(resource));
		dialog.setPageSize(800, 550);
		dialog.create();
		dialog.open();
	}

	public static void moveResource(EResource resource) {
		WizardDialog dialog = new WizardDialog(Eclipse.INSTANCE.getShell(), new MoveResourceWizard(resource));
		dialog.create();
		dialog.open();
	}

	public static void runScript(EScript script) {

	}

	public static void runTestCase(ETestCase testCase) {

	}

	public static void runTestSuite(ETestFolder folder) {

	}

	public static void viewTestReports(ETestFolder folder) {

	}

	public static void copyResource(Collection<EResource> resources, EResourceFolder folder, boolean move) {

		ResourceCRUDRequest request = new ResourceCRUDRequest();
		request.setOperation(move ? CRUDOperation.MOVE : CRUDOperation.COPY);
		EProject targetProject = folder.getEParent(EProject.class);
		for (EResource resource : resources) {
			EProject sourceProject = resource.getEParent(EProject.class);
			if (!sourceProject.equals(targetProject)) {
				request.getResourceUrns().add(resource.getId());
			}
		}
		request.setDestinationProject(targetProject.getId());
		Activator.post(IMessage.MessageClass.ResourceLifecycle,
				(move ? IMessage.Type.MoveResource : IMessage.Type.CopyResource), request);
	}

	public static void editDocumentation(EDocumentable model) {
		try {
			IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.showView(DocumentationEditor.ID);
			if (view != null) {
				((DocumentationEditor) view).setTarget(model.getDocId(), model);
			}
		} catch (PartInitException e) {
			Eclipse.INSTANCE.handleException(e);
		}
	}

	public static void editReferences(EProject project) {
		try {
			IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.showView(ReferencesEditor.ID);
			if (view != null) {
				((ReferencesEditor) view).setTarget(project);
			}
		} catch (PartInitException e) {
			Eclipse.INSTANCE.handleException(e);
		}
	}

	public static void editDocumentation(EDocumentationItem item) {
		try {
			IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.showView(DocumentationEditor.ID);
			if (view != null) {
				((DocumentationEditor) view).setTarget(item);
			}
		} catch (PartInitException e) {
			Eclipse.INSTANCE.handleException(e);
		}
	}

	public static void editReference(EReference project) {
		try {
			IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.showView(ReferencesEditor.ID);
			if (view != null) {
				((ReferencesEditor) view).setTarget(project);
			}
		} catch (PartInitException e) {
			Eclipse.INSTANCE.handleException(e);
		}
	}

	public static void addDocumentationSection(EDocumentationPage item) {
		// show wizard as below with ID disabled - choose section/trigger disabling
		// those already present
		WizardDialog dialog = new WizardDialog(Eclipse.INSTANCE.getShell(), new NewDocumentationSectionWizard(item));
		dialog.create();
		dialog.open();
	}

	public static void addDocumentationItem(EDocumentationFolder page) {
		// show wizard - choose ID (path pre-fixed, single word, path allowed) + initial
		// section/trigger
		// logger.debug("OHO " + page.getPath());
		WizardDialog dialog = new WizardDialog(Eclipse.INSTANCE.getShell(), new NewDocumentationSectionWizard(page));
		dialog.create();
		dialog.open();
	}

	public static void addDocumentationSubsection(EDocumentationFolder page) {
		// show wizard - choose single lowercase word, no path
		WizardDialog dialog = new WizardDialog(Eclipse.INSTANCE.getShell(), new NewDocumentationFolderWizard(page));
		dialog.create();
		dialog.open();
	}

    public static void deactivate(EProject project) {
        logger.debug("Project deactivated: " + project.getName());
    }

}
