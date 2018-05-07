package org.integratedmodelling.klab.ide.navigator.parts;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.navigator.IDescriptionProvider;

class ViewerLabelProvider extends LabelProvider implements IDescriptionProvider {
    
    WorkbenchLabelProvider delegate = new WorkbenchLabelProvider();
    
	public Image getImage(Object element) {
		return delegate.getImage(element);
	}
	public String getText(Object element) {
		return delegate.getText(element);
	}
    @Override
    public String getDescription(Object anElement) {
        // TODO Auto-generated method stub
        return delegate.getText(anElement);
    }
}