package org.integratedmodelling.klab.ide.navigator.e3;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.navigator.ILinkHelper;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.ide.kim.KimData;
import org.integratedmodelling.klab.ide.navigator.model.EKimObject;
import org.integratedmodelling.klab.ide.navigator.model.ENamespace;
import org.integratedmodelling.klab.ide.navigator.model.ENavigatorItem;
import org.integratedmodelling.klab.ide.navigator.model.EProject;
import org.integratedmodelling.klab.ide.navigator.model.EResource;
import org.integratedmodelling.klab.ide.navigator.model.documentation.EDocumentationItem;
import org.integratedmodelling.klab.ide.navigator.model.documentation.EReference;
import org.integratedmodelling.klab.ide.navigator.model.documentation.EReferencesPage;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.ide.views.ResourceEditor;

public class LinkHelper implements ILinkHelper {

	@Override
	public IStructuredSelection findSelection(IEditorInput anInput) {

		IFile file = ResourceUtil.getFile(anInput);
		if (file == null) {
			return StructuredSelection.EMPTY;
		}

		IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findEditor(anInput);
		StyledText text = (StyledText) editor.getAdapter(Control.class);
		if (text == null) {
			
			try {
				
				file.getProject().refreshLocal(IFolder.DEPTH_INFINITE, null);
				editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findEditor(anInput);
				text = (StyledText) editor.getAdapter(Control.class);
				
			} catch (CoreException e) {
			}

			if (text /* still */ == null) {
				return StructuredSelection.EMPTY;
			}
		}
		int caret = text.getCaretOffset();

		String namespaceId = Eclipse.INSTANCE.getNamespaceIdFromIFile(file);
		if (namespaceId != null) {
			IKimNamespace namespace = Kim.INSTANCE.getNamespace(namespaceId);
			if (namespace != null) {

				ENavigatorItem selection = KimData.INSTANCE.findObjectAt(caret, namespace);
				if (selection == null) {
					return StructuredSelection.EMPTY;
				}

				List<Object> treePath = new ArrayList<>();
				while (selection != null) {
					treePath.add(0, selection);
					selection = selection.getEParent();
				}
				return new TreeSelection(new TreePath(treePath.toArray()));
			}
		}

		return StructuredSelection.EMPTY;
	}

	@Override
	public void activateEditor(IWorkbenchPage aPage, IStructuredSelection aSelection) {

		if (aSelection == null || aSelection.isEmpty()) {
			return;
		}

		if (aSelection.getFirstElement() instanceof ENamespace) {
			Eclipse.INSTANCE.openFile(((ENamespace) aSelection.getFirstElement()).getIFile(), 0);
		} else if (aSelection.getFirstElement() instanceof EKimObject) {
			EKimObject kob = (EKimObject) aSelection.getFirstElement();
			ENamespace kns = kob.getEParent(ENamespace.class);
			Eclipse.INSTANCE.openFile(kns.getIFile(), kob.getFirstLine());
		} else if (aSelection.getFirstElement() instanceof EResource) {
			try {
				IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
						.showView(ResourceEditor.ID);
				if (view != null) {
					((ResourceEditor) view).loadResource(((EResource) aSelection.getFirstElement()).getResource());
				}
			} catch (PartInitException e) {
				Eclipse.INSTANCE.handleException(e);
			}
		} else if (aSelection.getFirstElement() instanceof EDocumentationItem) {
			KlabNavigatorActions.editDocumentation((EDocumentationItem) aSelection.getFirstElement());
		} else if (aSelection.getFirstElement() instanceof EReference) {
			KlabNavigatorActions.editReference((EReference) aSelection.getFirstElement());
		} else if (aSelection.getFirstElement() instanceof EReferencesPage) {
			KlabNavigatorActions
					.editReferences(((EReferencesPage) aSelection.getFirstElement()).getEParent(EProject.class));
		}
	}

}
