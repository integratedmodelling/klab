package org.integratedmodelling.kim.ui.navigator;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.ui.editor.outline.impl.OutlineNodeFactory;
import org.eclipse.xtext.ui.editor.outline.impl.OutlineNodeLabelProvider;
import com.google.inject.Inject;

// for later
public class KimLabelProvider implements ILabelProvider {

	@Inject
	private OutlineNodeFactory nodeFactory;
	
    @Inject
    private OutlineNodeLabelProvider labelProvider;

    @Override
    public void addListener(ILabelProviderListener listener) {
        // TODO Auto-generated method stub
        labelProvider.addListener(listener);
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        labelProvider.dispose();
    }

    @Override
    public boolean isLabelProperty(Object element, String property) {
        return labelProvider.isLabelProperty(element, property);
    }

    @Override
    public void removeListener(ILabelProviderListener listener) {
    	labelProvider.removeListener(listener);
    }

    @Override
    public Image getImage(Object element) {
        return labelProvider.getImage(element);
    }

    @Override
    public String getText(Object element) {
        return labelProvider.getText(element);
    }
    

}
