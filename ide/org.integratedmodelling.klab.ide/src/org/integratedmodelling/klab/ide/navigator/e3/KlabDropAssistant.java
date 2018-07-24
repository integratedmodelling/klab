package org.integratedmodelling.klab.ide.navigator.e3;

import java.io.File;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.resources.ResourceDropAdapterAssistant;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.model.EResourceFolder;
import org.integratedmodelling.klab.ide.utils.Eclipse;

public class KlabDropAssistant extends ResourceDropAdapterAssistant {

    public KlabDropAssistant() {
    }
    
    

    @Override
    public IStatus validateDrop(Object target, int operation, TransferData transferType) {
        if (target instanceof EResourceFolder) {
            return Status.OK_STATUS;
        }
        return Status.CANCEL_STATUS;
    }

    @Override
    public IStatus handleDrop(CommonDropAdapter aDropAdapter, DropTargetEvent event, Object target) {
    	
        if (target instanceof EResourceFolder && event.data instanceof String[]) {
            File file = new File(((String[]) event.data)[0]);
            if (file.exists() && file.isFile()) {
                if (Activator.engineMonitor().isRunning()) {
                    // TODO initiate import
                	Activator.session().importFileResource(file);
                	return Status.OK_STATUS;
                } else {
                    Eclipse.INSTANCE.alert("You must be connected to an engine to import resources.");
                }
            } else {
            	/*
            	 * Check for URL - either 
            	 */
            }
        }
        return Status.CANCEL_STATUS;
    }

}
