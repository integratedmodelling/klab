package org.integratedmodelling.klab.ide.navigator.model.documentation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.integratedmodelling.klab.api.documentation.IDocumentation.Trigger;
import org.integratedmodelling.klab.client.documentation.ProjectDocumentation;
import org.integratedmodelling.klab.ide.navigator.model.ENavigatorItem;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.utils.StringUtil;

public class EDocumentationScope extends ENavigatorItem {

	private Trigger trigger;
	private ProjectDocumentation documentation;
	private File file;

	protected EDocumentationScope(EDocumentationFolder folder, ProjectDocumentation documentation, Trigger trigger,
			File file) {
		super(folder.getName() + "_" + trigger, folder);
		this.trigger = trigger;
		this.documentation = documentation;
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
		for (String page : documentation.getItems()) {
			for (Trigger trigger : documentation.getTriggers(page)) {
				if (trigger == this.trigger) {
					ret.add(new EDocumentationPage(this, file, page, trigger, documentation));
				}
			}
		}

		return ret.toArray(new ENavigatorItem[0]);
	}

	@Override
	public boolean hasEChildren() {
		return true;
	}

	public String getName() {
		return StringUtil.capitalize(trigger.name().toLowerCase());
	}

}
