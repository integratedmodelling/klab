package org.integratedmodelling.klab.ide.navigator.e3;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.viewers.ContentViewer;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.integratedmodelling.klab.ide.navigator.model.EKimObject;
import org.integratedmodelling.klab.ide.navigator.model.ENamespace;
import org.integratedmodelling.klab.ide.navigator.model.ENavigatorItem;
import org.integratedmodelling.klab.ide.navigator.model.EResourceFolder;
import org.integratedmodelling.klab.ide.navigator.model.EScriptFolder;
import org.integratedmodelling.klab.ide.navigator.model.ETestFolder;
import org.integratedmodelling.klab.ide.navigator.model.documentation.EDocumentationFolder;
import org.integratedmodelling.klab.ide.navigator.model.documentation.EReferencesPage;

public class KlabViewerComparator extends ViewerComparator {

	static Map<Class<? extends ENavigatorItem>, Integer> categories = new HashMap<>();
	static {
		categories.put(ENamespace.class, 0);
		categories.put(EDocumentationFolder.class, 1);
		categories.put(EResourceFolder.class, 2);
		categories.put(EScriptFolder.class, 3);
		categories.put(ETestFolder.class, 4);
        categories.put(EReferencesPage.class, 4);
	}

	public KlabViewerComparator() {
	}

	public KlabViewerComparator(Comparator<? super String> comparator) {
		super(comparator);
	}

	// Had to copy unchanged from superclass so that following private method 
	// can be added (buggy in original code)
	@Override
    public int compare(Viewer viewer, Object e1, Object e2) {
        int cat1 = category(e1);
        int cat2 = category(e2);

        if (cat1 != cat2) {
			return cat1 - cat2;
		}

        String name1 = getLabel(viewer, e1);
        String name2 = getLabel(viewer, e2);

        // use the comparator to compare the strings
        return getComparator().compare(name1, name2);
    }

	// Necessary ugliness due to non-working Eclipse bugfix below
	private String getLabel(Viewer viewer, Object e1) {
		String name1;
		if (viewer == null || !(viewer instanceof ContentViewer)) {
			name1 = e1.toString();
		} else {
			IBaseLabelProvider prov = ((ContentViewer) viewer)
					.getLabelProvider();
			if (prov instanceof ILabelProvider) {
				ILabelProvider lprov = (ILabelProvider) prov;
				if (lprov instanceof DecoratingLabelProvider) {
					// Bug 364735: use the real label provider to avoid unstable
					// sort behavior if the decoration is running while sorting.
					// decorations are usually visual aids to the user and
					// shouldn't be used in ordering.
					// FV: this does not fix anything as the decorating label provider is not
					// a decorating label provider.
					DecoratingLabelProvider dprov = (DecoratingLabelProvider) lprov;
					lprov = dprov.getLabelProvider();
				}
				name1 = lprov.getText(e1);
			} else {
				name1 = e1.toString();
			}
		}
		if (name1 == null) {
			name1 = "";//$NON-NLS-1$
		}
		
		// the only case so far where the bug shows.
		if (name1.startsWith("> ")) {
			name1 = name1.substring(2);
		}
		
		return name1;
	}
	
	
	@Override
	public int category(Object element) {
		Integer cat = categories.get(element.getClass());
		if (cat == null && element instanceof EKimObject) {
			/*
			 * TODO if alphabetical sorting action is selected, just return
			 * 0 and let the default take over.
			 */
			cat = ((EKimObject) element).getFirstLine();
		}
		return cat == null ? 0 : cat;
	}

}
