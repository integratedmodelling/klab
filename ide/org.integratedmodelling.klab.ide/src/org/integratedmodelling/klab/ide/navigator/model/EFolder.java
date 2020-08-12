package org.integratedmodelling.klab.ide.navigator.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.integratedmodelling.klab.ide.utils.Eclipse;

/**
 * Generic resource folder, admitted in apps so far.
 * 
 * @author Ferd
 *
 */
public class EFolder extends ENavigatorItem {

    EProject project;
    File folder;

    public EFolder(EProject project, ENavigatorItem parent, File folder) {
        super(parent.id + folder, parent);
        this.project = project;
        this.folder = folder;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getAdapter(Class<T> adapter) {
		if (IFolder.class.isAssignableFrom(adapter) ) {
            return (T) Eclipse.INSTANCE.getIFolder(folder);
        }
		return null;
    }

    @Override
    public Object[] getEChildren() {
        List<Object> ret = new ArrayList<>();
        if (folder.isDirectory()) {
            for (File script : folder.listFiles()) {
                if (script.isDirectory()) {
                    ret.add(new EFolder(project, this, script));
                } else {
                	ret.add(Eclipse.INSTANCE.getIFile(script));
                }
            }
        }
        return ret.toArray();
    }

    @Override
    public boolean hasEChildren() {
        return getEChildren().length > 0;
    }

	public File getFolder() {
		return folder;
	}

}
