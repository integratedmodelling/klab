package org.integratedmodelling.klab.ide.navigator.model.documentation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.integratedmodelling.klab.api.documentation.IDocumentation.Trigger;
import org.integratedmodelling.klab.client.documentation.ProjectDocumentation;
import org.integratedmodelling.klab.ide.navigator.model.ENavigatorItem;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.utils.Path;

public class EDocumentationPage extends ENavigatorItem {

	private String id;
	private Trigger trigger;
	private ProjectDocumentation documentation;
	private File file;

	EDocumentationPage(ENavigatorItem parent, File file, String id, Trigger trigger,
			ProjectDocumentation documentation) {
		super(id, parent);
		this.id = id;
		this.documentation = documentation;
		this.trigger = trigger;
		this.file = file;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		if (IFile.class.isAssignableFrom(adapter)) {
			if (this.file != null) {
				return (T) Eclipse.INSTANCE.getIFile(this.file);
			}
		}
		return null;
	}

	@Override
	public ENavigatorItem[] getEChildren() {
		List<ENavigatorItem> ret = new ArrayList<>();
		for (String ites : documentation.getSections(id, trigger)) {
			ret.add(new EDocumentationItem(id, ites, trigger, documentation, this));
		}
		return ret.toArray(new ENavigatorItem[0]);
	}

	@Override
	public boolean hasEChildren() {
		return true;
	}

	public String getPagePath() {
		return id;
	}

	public String getName() {
		return Path.getLast(getPagePath(), '.');
	}
}
