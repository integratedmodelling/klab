package org.integratedmodelling.klab.ide.navigator.e3;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.navigator.IDescriptionProvider;

public class ViewerLabelProvider extends LabelProvider implements IDescriptionProvider {
    
	public ViewerLabelProvider() {}
	
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