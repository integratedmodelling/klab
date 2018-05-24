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

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.part.ShowInContext;

public class KlabNavigator extends CommonNavigator  {

    static Viewer _viewer;

    public KlabNavigator() {
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

    @Override
    public void dispose() {
//        Activator.getDefault().removeThinklabEventListener(this);
        super.dispose();
    }
    
    @Override
    public void selectReveal(ISelection selection) {
        super.selectReveal(selection);
    }

    @Override
    public boolean show(ShowInContext context) {
        return super.show(context);
    }

    @Override
    public void setFocus() {
        super.setFocus();
        refresh();
    }

//    @Override
//    public void handleThinklabEvent(ThinklabEvent event) {
//        if (event instanceof ModelModifiedEvent) {
//            refresh();
//        }
//    }

}
