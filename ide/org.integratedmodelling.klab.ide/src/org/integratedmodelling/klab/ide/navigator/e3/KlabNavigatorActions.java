package org.integratedmodelling.klab.ide.navigator.e3;

import java.io.File;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.integratedmodelling.kim.api.IKimNamespace.Role;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.model.ENamespace;
import org.integratedmodelling.klab.ide.navigator.model.EProject;
import org.integratedmodelling.klab.ide.navigator.model.EResource;
import org.integratedmodelling.klab.ide.navigator.model.EResourceFolder;
import org.integratedmodelling.klab.ide.navigator.model.EScript;
import org.integratedmodelling.klab.ide.navigator.model.EScriptFolder;
import org.integratedmodelling.klab.ide.navigator.model.ETestCase;
import org.integratedmodelling.klab.ide.navigator.model.ETestFolder;
import org.integratedmodelling.klab.ide.ui.wizards.NewNamespaceWizard;
import org.integratedmodelling.klab.ide.ui.wizards.NewProjectWizard;
import org.integratedmodelling.klab.ide.ui.wizards.NewScriptWizard;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.rest.ProjectModificationNotification;
import org.integratedmodelling.klab.rest.ProjectModificationRequest;

public class KlabNavigatorActions {

    public static void createProject() {
        WizardDialog dialog = new WizardDialog(Eclipse.INSTANCE.getShell(), new NewProjectWizard());
        dialog.create();
        dialog.open();
    }

    public static void deleteProject(EProject project) {
        if (MessageDialog.openConfirm(Eclipse.INSTANCE.getShell(), "Confirm deletion",
                "Delete project " + project.getName() + "? This action cannot be recovered.")) {
            System.out.println("BUMMER!");
        }
    }

    public static void addNamespace(EProject project) {
        WizardDialog dialog = new WizardDialog(Eclipse.INSTANCE.getShell(),
                new NewNamespaceWizard(project.getProject()));
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

    }

    public static void editResource(EResource resource) {

    }

    public static void deleteResource(EResource resource) {

    }

    public static void runScript(EScript script) {

    }

    public static void runTestCase(ETestCase testCase) {

    }

    public static void runTestSuite(ETestFolder folder) {

    }

    public static void viewTestReports(ETestFolder folder) {

    }

}
