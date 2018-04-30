package org.integratedmodelling.klab.ide.navigator.compatibility;

/*******************************************************************************
 * Copyright 2015 Geoscience Australia
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

public class ForceMainMenuProcessor {

    @Execute
    public void execute(@Optional MApplication application, @Optional EModelService modelService) {
        
        // Use this shit with the "RIGHT" f'ing ID: this.modelService.find("window.main", this.application);

        MTrimmedWindow window = (MTrimmedWindow) application.getChildren().get(0);
        if (window == null || window.getMainMenu() != null) {
            return;
        }
//        final MMenu mainMenu = modelService.createModelElement(MMenu.class);
//        mainMenu.setElementId("org.eclipse.ui.main.menu");
//        window.setMainMenu(mainMenu);
    }
}
