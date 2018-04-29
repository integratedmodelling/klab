package org.integratedmodelling.klab.ide.navigator;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.navigator.IDescriptionProvider;
import org.integratedmodelling.kim.ui.navigator.KimLabelProvider;

public class KlabLabelProvider extends KimLabelProvider implements ILabelProvider, IDescriptionProvider {

  public KlabLabelProvider() {
		System.out.println("HA CREATO ANCHE ME! MI HA CREATO, DIO CANE!");
  }

  @Override
  public String getDescription(Object anElement) {
    // TODO Auto-generated method stub
    return "HOSTIA";
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
