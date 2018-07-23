package org.integratedmodelling.klab.ide.navigator.e3;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.viewers.ViewerComparator;
import org.integratedmodelling.klab.ide.navigator.model.EKimObject;
import org.integratedmodelling.klab.ide.navigator.model.ENamespace;
import org.integratedmodelling.klab.ide.navigator.model.ENavigatorItem;
import org.integratedmodelling.klab.ide.navigator.model.EResourceFolder;
import org.integratedmodelling.klab.ide.navigator.model.EScriptFolder;
import org.integratedmodelling.klab.ide.navigator.model.ETestFolder;

public class KlabViewerComparator extends ViewerComparator {

	static Map<Class<? extends ENavigatorItem>, Integer> categories = new HashMap<>();
	static {
		categories.put(ENamespace.class, 0);
		categories.put(EResourceFolder.class, 1);
		categories.put(EScriptFolder.class, 2);
		categories.put(ETestFolder.class, 3);
	}

	public KlabViewerComparator() {
	}

	public KlabViewerComparator(Comparator<? super String> comparator) {
		super(comparator);
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
