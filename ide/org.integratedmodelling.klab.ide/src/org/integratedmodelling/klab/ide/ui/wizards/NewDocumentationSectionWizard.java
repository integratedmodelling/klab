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
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.ide.navigator.model.ENavigatorItem;
import org.integratedmodelling.klab.ide.navigator.model.EProject;
import org.integratedmodelling.klab.ide.navigator.model.documentation.EDocumentationFolder;
import org.integratedmodelling.klab.ide.navigator.model.documentation.EDocumentationPage;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.ide.views.DocumentationEditor;
import org.integratedmodelling.klab.utils.StringUtils;

public class NewDocumentationSectionWizard extends Wizard implements INewWizard {

	private NewDocumentationSection page;
	private EDocumentationFolder folder;
	private EDocumentationPage item;

	public NewDocumentationSectionWizard(EDocumentationFolder folder) {
		setWindowTitle("New documentation page");
		this.folder = folder;
	}

	public NewDocumentationSectionWizard(EDocumentationPage item) {
		setWindowTitle("New documentation page section");
		this.item = item;
		this.folder = item.getEParent(EDocumentationFolder.class);
	}

	@Override
	public void addPages() {
		addPage(page = new NewDocumentationSection(this.folder, this.item));
	}

	@Override
	public boolean performFinish() {

		final String name = page.getFolderName().getText();
		final String path = item == null ? (folder.getPath() + (folder.getPath().isEmpty() ? "" : ".") + name) : name;
		if (validate(name)) {
			try {
				IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
						.showView(DocumentationEditor.ID);
				if (view != null) {
					((DocumentationEditor) view).setTarget(folder.getEParent(EProject.class), path, page.getTrigger(),
							page.getSection());
				}
			} catch (PartInitException e) {
				Eclipse.INSTANCE.handleException(e);
			}
			return true;
		}

		return false;
	}

	private boolean validate(String src) {

		if (item == null) {

			if (src.isEmpty())
				return false;

			if (StringUtils.containsAny(src, StringUtils.UPPERCASE | StringUtils.WHITESPACE | StringUtils.NONLETTERS)) {
				page.setErrorMessage("Page names must contain only lowercase letters with no whitespace");
				return false;
			}

			if (src.contains(".")) {
				page.setErrorMessage("No dots are accepted in a page name.");
				return false;
			}

			if (Kim.INSTANCE.getKimKeywords().contains(src)) {
				page.setErrorMessage("'" + src + "' is a k.IM keyword and cannot be used as a folder name.");
				return false;
			}

			for (ENavigatorItem child : folder.getEChildren()) {
				if (child instanceof EDocumentationFolder && ((EDocumentationFolder) child).getName().equals(src)) {
					page.setErrorMessage("A folder named " + src + " exists in this folder.");
					return false;
				} else if (child instanceof EDocumentationPage
						&& ((EDocumentationPage) child).getPagePath().endsWith("." + src)) {
					page.setErrorMessage("Name " + src + " conflicts with an existing documentation page.");
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

}
