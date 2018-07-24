/*******************************************************************************
 *  Copyright (C) 2007, 2015:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.ide.navigator.e3;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.navigator.CommonNavigator;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.ide.model.KlabPeer;
import org.integratedmodelling.klab.ide.model.KlabPeer.Sender;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.rest.Capabilities;
import org.integratedmodelling.klab.rest.ProjectReference;

public class KlabNavigator extends CommonNavigator  {

    static Viewer _viewer;
    KlabPeer klab;
    
    public KlabNavigator() {
		klab = new KlabPeer(Sender.ANY, (message) -> handleMessage(message));
    }
    
    private void handleMessage(IMessage message) {
    	switch (message.getType()) {
		case EngineDown:
			final Capabilities capabilities = message.getPayload(Capabilities.class);
			for (ProjectReference project : capabilities.getLocalWorkspaceProjects()) {
				IProject p = Eclipse.INSTANCE.getProject(project.getName());
			}
			break;
		case EngineUp:
			break;
		default:
			break;
    	
    	}
	}

	@Override
	protected Object getInitialInput() {
    	return ResourcesPlugin.getWorkspace().getRoot();
	}

	public static void refresh() {
        if (_viewer != null) {
            Display.getDefault().asyncExec(new Runnable() {
                @Override
                public void run() {
                    _viewer.refresh();
                }
            });
        }
    }

}
