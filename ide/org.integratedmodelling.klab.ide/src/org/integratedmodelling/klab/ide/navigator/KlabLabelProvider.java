package org.integratedmodelling.klab.ide.navigator;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.navigator.IDescriptionProvider;

public class KlabLabelProvider extends LabelProvider implements ILabelProvider, IDescriptionProvider {

  public KlabLabelProvider() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public String getDescription(Object anElement) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Image getImage(Object element) {
    // TODO Auto-generated method stub
    return super.getImage(element);
  }

  @Override
  public String getText(Object element) {
    // TODO Auto-generated method stub
    return super.getText(element);
  }

}
