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
package org.integratedmodelling.klab.ide.ui.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.ide.navigator.e3.KlabNavigator;
import org.integratedmodelling.klab.ide.navigator.model.ENavigatorItem;
import org.integratedmodelling.klab.ide.navigator.model.EProject;
import org.integratedmodelling.klab.ide.navigator.model.documentation.EDocumentationFolder;
import org.integratedmodelling.klab.ide.navigator.model.documentation.EDocumentationPage;
import org.integratedmodelling.klab.utils.StringUtils;

public class NewDocumentationFolderWizard extends Wizard implements INewWizard {

	private NewDocumentationFolder page;
	private EDocumentationFolder folder;

	public NewDocumentationFolderWizard(EDocumentationFolder folder) {
		setWindowTitle("New documentation folder");
		this.folder = folder;
	}

	@Override
	public void addPages() {
		addPage(page = new NewDocumentationFolder(this.folder));
	}

	@Override
	public boolean performFinish() {

		final String name = page.getFolderName().getText();
		final String path = folder.getPath() + (folder.getPath().isEmpty() ? "" : ".") + name;
		if (validate(name)) {
			IDocumentation.getDocumentationFolder(path, folder.getEParent(EProject.class).getRoot());
			KlabNavigator.refresh();
			return true;
		}

		return false;
	}

	private boolean validate(String src) {

		if (src.isEmpty())
			return false;

		if (StringUtils.containsAny(src, StringUtils.UPPERCASE | StringUtils.WHITESPACE | StringUtils.NONLETTERS)) {
			page.setErrorMessage("Folder names must contain only lowercase letters with no whitespace");
			return false;
		}

		if (src.contains(".")) {
			page.setErrorMessage("Please create one folder at a time (no dots in the name are accepted).");
			return false;
		}
		
		if (Kim.INSTANCE.getKimKeywords().contains(src)) {
			page.setErrorMessage("'" + src + "' is a k.IM keyword and cannot be used as a folder name.");
			return false;
		}

		for (ENavigatorItem child : folder.getEChildren()) {
			if (child instanceof EDocumentationFolder && ((EDocumentationFolder) child).getName().equals(src)) {
				page.setErrorMessage("A folder named " + src + " already exists in this folder.");
				return false;
			} else if (child instanceof EDocumentationPage && ((EDocumentationPage) child).getPagePath().endsWith("." + src)) {
				page.setErrorMessage("Folder name " + src + " conflicts with an existing documentation page.");
				return false;
			}
		}

		return true;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

}
