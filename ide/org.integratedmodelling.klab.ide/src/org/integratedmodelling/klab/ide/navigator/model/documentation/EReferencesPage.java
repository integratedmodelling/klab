package org.integratedmodelling.klab.ide.navigator.model.documentation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.integratedmodelling.klab.client.documentation.ProjectReferences;
import org.integratedmodelling.klab.ide.navigator.model.ENavigatorItem;
import org.integratedmodelling.klab.ide.utils.Eclipse;

public class EReferencesPage extends ENavigatorItem {

	private ProjectReferences references;
	private File file;

	protected EReferencesPage(ENavigatorItem parent, File file, String id, ProjectReferences documentation) {
		super(id, parent);
		this.id = id;
		this.file = file;
		this.references = documentation;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		if (IResource.class.isAssignableFrom(adapter) && adapter != IProject.class) {
			if (this.file != null) {
				return (T) Eclipse.INSTANCE.getIFile(this.file);
			}
		}
		return null;
	}

	@Override
	public ENavigatorItem[] getEChildren() {
		List<ENavigatorItem> ret = new ArrayList<>();
		for (String key : references.keySet()) {
			ret.add(new EReference(this, references, key));
		}
		return ret.toArray(new ENavigatorItem[0]);
	}

	@Override
	public boolean hasEChildren() {
		return references.size() > 0;
	}

	public String getPagePath() {
		return id;
	}
}
