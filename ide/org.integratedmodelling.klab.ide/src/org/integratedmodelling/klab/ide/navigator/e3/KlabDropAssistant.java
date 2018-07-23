package org.integratedmodelling.klab.ide.navigator.e3;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.CommonDropAdapterAssistant;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.model.EResourceFolder;

public class KlabDropAssistant extends CommonDropAdapterAssistant {

	public KlabDropAssistant() {
	}

	@Override
	public IStatus validateDrop(Object target, int operation, TransferData transferType) {
		System.out.println("SMERGO");
		if (target instanceof EResourceFolder) {
			if (!Activator.engine().isRunning()) {
				System.out.println("MADONNA ZANA");
//				Eclipse.INSTANCE.alert("Please ensure an engine is connected before submitting resources.");
				return Status.CANCEL_STATUS;
			} else {
				return Status.OK_STATUS;
			}
		}
		return Status.CANCEL_STATUS;
	}

	@Override
	public IStatus handleDrop(CommonDropAdapter aDropAdapter, DropTargetEvent aDropTargetEvent, Object aTarget) {
		System.out.println("DROPPED SOME SHIT: " + aDropTargetEvent);
		return null;
	}

}
