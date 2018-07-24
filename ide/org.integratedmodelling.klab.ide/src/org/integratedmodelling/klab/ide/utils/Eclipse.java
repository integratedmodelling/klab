package org.integratedmodelling.klab.ide.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.wizards.datatransfer.FileSystemStructureProvider;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.model.EKimObject;
import org.integratedmodelling.klab.ide.navigator.model.ENamespace;

public enum Eclipse {

	INSTANCE;

	/**
	 * Open a file in the editor at the passed line number.
	 * 
	 * @param filename
	 * @param lineNumber
	 * @throws KlabException
	 */
	public void openFile(String filename, int lineNumber) throws KlabException {

		/*
		 * open as workspace file - otherwise xtext gives an exception
		 */
		IFile file = null;
		if (filename.startsWith("file:")) {
			URL url = null;
			try {
				url = new URL(filename);
			} catch (MalformedURLException e) {
				throw new KlabIOException(e);
			}
			filename = url.getFile().toString();
		}
		File dfile = new File(filename);
		if (dfile.exists()) {
			// full file path
			IFile[] ff = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(dfile.toURI());
			if (ff != null && ff.length > 0) {
				file = ff[0];
			}
		} else {
			Path path = new Path(filename);
			file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
		}
		openFile(file, lineNumber);
	}

	public void openFile(IFile file, int lineNumber) {

		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			if (lineNumber > 0) {
				HashMap<String, Object> map = new HashMap<>();
				map.put(IMarker.LINE_NUMBER, new Integer(lineNumber));
				IMarker marker = file.createMarker(IMarker.TEXT);
				marker.setAttributes(map);
				IDE.openEditor(page, marker);
				marker.delete();
			} else {
				IDE.openEditor(page, file);
			}
		} catch (Exception e) {
			error(e);
		}
	}

	public IFile getNamespaceIFile(EKimObject object) {
		ENamespace namespace = object.getEParent(ENamespace.class);
		if (namespace != null) {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IProject project = root.getProject(namespace.getProject().getName());
			String rpath = null;
			if (namespace.isWorldviewBound()) {
				String kimPrefix = "/";
				if (namespace.getScriptId() != null) {
					kimPrefix = IKimProject.SCRIPT_FOLDER + "/";
				} else if (namespace.getTestCaseId() != null) {
					kimPrefix = IKimProject.TESTS_FOLDER + "/";
				} else {
					// oh fuck
				}
				rpath = kimPrefix + namespace.getResourceId().substring(namespace.getResourceId().lastIndexOf('/') + 1);
			} else {
				rpath = "src/" + namespace.getName().replace('.', '/') + ".kim";
			}
			return project.getFile(rpath);
		}
		return null;
	}

	public String getNamespaceIdFromIFile(IFile file) {

		if (file.toString().endsWith(".kim")) {
			if (file.getProject() == null) {
				return null;
			}
			String project = file.getProject().getName();
			String kimPrefix = "";
			if (file.toString().contains(IKimProject.SOURCE_FOLDER)) {
				kimPrefix = IKimProject.SOURCE_FOLDER;
			} else if (file.toString().contains(IKimProject.SCRIPT_FOLDER)) {
				kimPrefix = IKimProject.SCRIPT_FOLDER;
			} else if (file.toString().contains(IKimProject.TESTS_FOLDER)) {
				kimPrefix = IKimProject.TESTS_FOLDER;
			}
			kimPrefix = project + "/" + kimPrefix + "/";
			String ret = file.toString().substring(file.toString().indexOf(kimPrefix) + kimPrefix.length());
			return ret.substring(0, ret.length() - 4).replaceAll("\\/", ".");
		}
		return null;
	}

	private void error(Exception e) {
		// TODO Auto-generated method stub
		System.out.println("SHIT, HANDLE ME: " + e);
	}

	public void openFile(String filename) throws KlabException {
		openFile(filename, 0);
	}

	/**
	 * Import an Eclipse project programmatically into the workspace. Does not check
	 * for existence and overwrites whatever is there.
	 * 
	 * @param baseDir
	 * @return
	 */
	public IProject importExistingProject(File baseDir) {

		IProject project = null;

		try {
			IProjectDescription description = ResourcesPlugin.getWorkspace()
					.loadProjectDescription(new Path(baseDir.getPath() + "/.project"));
			project = ResourcesPlugin.getWorkspace().getRoot().getProject(description.getName());
			project.create(description, null);

			IOverwriteQuery overwriteQuery = new IOverwriteQuery() {

				public String queryOverwrite(String file) {
					return ALL;
				}
			};

			ImportOperation importOperation = new ImportOperation(project.getFullPath(), baseDir,
					FileSystemStructureProvider.INSTANCE, overwriteQuery);
			importOperation.setCreateContainerStructure(false);
			importOperation.run(new NullProgressMonitor());

		} catch (Exception e) {
			error(e);
		}
		return project;
	}

	public void alert(String message) {
		try {
			IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			Shell shell = window == null ? new Shell(new Display()) : window.getShell();
			MessageDialog.openError(shell, "Error", message);
		} catch (Throwable e) {
			// last resort
			System.out.println("ALERT: " + message);
		}
	}

	public boolean confirm(String message) {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		return MessageDialog.openQuestion(shell, "Confirmation", message);
	}

	public void warning(String message) {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		MessageDialog.openWarning(shell, "Warning", message);
	}

	public void info(String message) {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		MessageDialog.openInformation(shell, "Information", message);
	}

	public void error(Object message) {
		if (message instanceof Throwable) {
			handleException((Throwable) message);
		} else {
			StatusManager.getManager().handle(new Status(IStatus.ERROR, Activator.PLUGIN_ID, message.toString()));
		}
	}

	public void beep() {
		PlatformUI.getWorkbench().getDisplay().beep();
	}

	public void handleException(Throwable e) {
		if (e instanceof CoreException) {
			StatusManager.getManager().handle((CoreException) e, Activator.PLUGIN_ID);
		} else if (e instanceof KlabException) {
			alert(e.getMessage());
			StatusManager.getManager().handle(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Exception: ", e));
		} else {
			StatusManager.getManager().handle(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Exception: ", e));
		}
	}

}
