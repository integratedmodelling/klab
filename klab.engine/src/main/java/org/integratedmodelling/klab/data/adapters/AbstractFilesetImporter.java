package org.integratedmodelling.klab.data.adapters;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.compress.archivers.zip.ZipUtil;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.utils.MiscUtilities;

public abstract class AbstractFilesetImporter implements IResourceImporter {

	private String[] recognizedExtensions;
	private File directory;
	private File archiveFile;

	/**
	 * Try to import the passed file, which matches one of the {@link #recognizedExtensions}. If successful,
	 * return a builder for the correspondent resource. Otherwise return null.
	 * 
	 * @param file
	 * @param userData
	 * @return a builder for the resource or null.
	 */
	protected abstract Builder importFile(File file, IParameters<String> userData);
	
	protected AbstractFilesetImporter(File directoryOrArchive, String[] recognizedExtensions) {
		this.recognizedExtensions = recognizedExtensions;
		if (directoryOrArchive.exists()) {
			if (directoryOrArchive.isDirectory()) {
				this.directory = directoryOrArchive;
			} else {
				throw new KlabUnimplementedException("importing from archive is still unimplemented");
			}
		}
	}

	@Override
	public Collection<Builder> importResources(String importLocation, IParameters<String> userData) {
		List<Builder> ret = new ArrayList<>();
		if (this.directory != null) {
			scanDirectory(this.directory, userData, ret);
		}
		return ret;
	}

	private void scanDirectory(File directory, IParameters<String> userData, List<Builder> ret) {
		
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				scanDirectory(file, userData, ret);
			} else if (file.canRead()) {
				for (String s : recognizedExtensions) {
					if (MiscUtilities.getFileExtension(file).equals(s)) {
						Builder builder = importFile(file, userData);
						if (builder != null) {
							ret.add(builder);
						}
					}
				}
			}
		}
	}

	@Override
	public boolean canHandle(String importLocation, IParameters<String> userData) {

		File file = null;
		if (importLocation.startsWith("file:")) {
			try {
				file = new File(new URL(importLocation).getFile());
			} catch (MalformedURLException e) {
				return false;
			}
		} else {
			file = Klab.INSTANCE.resolveFile(importLocation);
			if (file == null || !file.exists()) {
				return false;
			}
			if (file.exists()) {
				if (file.isDirectory()) {
					return true;
				} else {
					// TODO check for archive file
				}
			}
		}
		return false;
	}

}
