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
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.ide.kim.KimData;
import org.integratedmodelling.klab.ide.navigator.model.EKimObject;
import org.integratedmodelling.klab.ide.navigator.model.ENamespace;
import org.integratedmodelling.klab.ide.utils.Eclipse;

public class LinkHelper implements ILinkHelper {

    @Override
    public IStructuredSelection findSelection(IEditorInput anInput) {

        IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findEditor(anInput);
        StyledText text = (StyledText) editor.getAdapter(Control.class);
        int caret = text.getCaretOffset();

        IFile file = ResourceUtil.getFile(anInput);
        if (file != null) {
            String namespaceId = Eclipse.INSTANCE.getNamespaceIdFromIFile(file);
            if (namespaceId != null) {
                IKimNamespace namespace = Kim.INSTANCE.getNamespace(namespaceId);
                if (namespace != null) {
                    Object selection = KimData.INSTANCE.findObjectAt(caret, namespace);
                    return selection == null ? StructuredSelection.EMPTY : new StructuredSelection(selection);
                }
            }
            //                return null;
        }
        return StructuredSelection.EMPTY;
    }

    @Override
    public void activateEditor(IWorkbenchPage aPage, IStructuredSelection aSelection) {

        if (aSelection == null || aSelection.isEmpty())
            return;

        if (aSelection.getFirstElement() instanceof ENamespace) {
            Eclipse.INSTANCE.openFile(((ENamespace) aSelection.getFirstElement()).getAdapter(IFile.class), 0);
        } else if (aSelection.getFirstElement() instanceof EKimObject) {
            EKimObject kob = (EKimObject) aSelection.getFirstElement();
            ENamespace kns = kob.getEParent(ENamespace.class);
            Eclipse.INSTANCE.openFile(kns.getAdapter(IFile.class), kob.getFirstLine());
        }
    }

}
