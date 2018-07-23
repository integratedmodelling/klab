package org.integratedmodelling.klab.ide.navigator.e3;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.navigator.CommonDragAdapterAssistant;

public class KlabDragAdapter extends CommonDragAdapterAssistant {

	public KlabDragAdapter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Transfer[] getSupportedTransferTypes() {
		return new Transfer[] {FileTransfer.getInstance(), TextTransfer.getInstance()};
	}

	@Override
	public boolean setDragData(DragSourceEvent anEvent, IStructuredSelection aSelection) {
		System.out.println("ZORBA " + anEvent);
		return true;
	}

}
