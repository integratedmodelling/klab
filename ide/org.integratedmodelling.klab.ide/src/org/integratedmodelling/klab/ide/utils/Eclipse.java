package org.integratedmodelling.klab.ide.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
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
			String rpath = namespace.getName().replace('.', '/') + ".kim";
			rpath = "src/" + rpath;
			return project.getFile(rpath);
		}
		return null;
	}

	private void error(Exception e) {
		// TODO Auto-generated method stub
		System.out.println("SHIT HANDLE ME " + e);
	}

	public void openFile(String filename) throws KlabException {
		openFile(filename, 0);
	}

}
