package org.integratedmodelling.klab.ide.navigator.e3;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.navigator.ILinkHelper;

public class LinkHelper implements ILinkHelper {

	@Override
	public IStructuredSelection findSelection(IEditorInput anInput) {
		
		IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findEditor(anInput);
		StyledText text = (StyledText)editor.getAdapter(Control.class);
		int caret = text.getCaretOffset();
		
		System.out.println("FUCKARET IS " + caret);
		
		IFile file = ResourceUtil.getFile(anInput);
		if (file != null) {
			return new StructuredSelection(file);
		}
		return StructuredSelection.EMPTY;
//		return null;
	}

	@Override
	public void activateEditor(IWorkbenchPage aPage, IStructuredSelection aSelection) {
		System.out.println("shittuuu " + aSelection);
		
//		if (aSelection == null || aSelection.isEmpty())
//			return;
//		if (aSelection.getFirstElement() instanceof IFile) {
//			IEditorInput fileInput = new FileEditorInput((IFile) aSelection.getFirstElement());
//			IEditorPart editor = null;
//			if ((editor = aPage.findEditor(fileInput)) != null)
//				aPage.bringToTop(editor);
//		}
	}

}
