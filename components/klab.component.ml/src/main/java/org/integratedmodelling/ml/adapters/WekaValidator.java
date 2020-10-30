package org.integratedmodelling.ml.adapters;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.URLUtils;
import org.integratedmodelling.ml.context.WekaClassifier;

import weka.classifiers.bayes.BayesNet;

public class WekaValidator implements IResourceValidator {

	@Override
	public Builder validate(URL url, IParameters<String> userData, IMonitor monitor) {

		IResource.Builder ret = Resources.INSTANCE.createResourceBuilder().withType(IArtifact.Type.VALUE);

		try {

			File file = URLUtils.getFileForURL(url);
			monitor.info("Validating " + file + " as WEKA resource");
			ret.withParameter("fileUrl", url).withLocalName(MiscUtilities.getFileName(url.getFile()));

			if (file.toString().endsWith("xdsl")) {
				monitor.info("Importing GENIE file " + file + " as a WEKA resource");
				String wekaClass = userData.get("classifier", BayesNet.class.getCanonicalName());
				if (!wekaClass.equals(BayesNet.class.getCanonicalName())) {
					ret.addError("Cannot import a XDSL file as a classifier that is not a BayesNet");
				} else {

				}
			} else if (file.toString().endsWith("bif")) {
				monitor.info("Importing BIF file " + file + " as a WEKA resource");
				String wekaClass = userData.get("classifier", BayesNet.class.getCanonicalName());
				if (!wekaClass.equals(BayesNet.class.getCanonicalName())) {
					ret.addError("Cannot import a XDSL file as a classifier that is not a BayesNet");
				} else {

				}

			} else {
				monitor.info("Importing WEKA classifier from " + file + " as a WEKA resource");
			}

			if (ret.hasErrors()) {
				monitor.info("WEKA resource has errors: validation failed");
			}

		} catch (Throwable e) {
			ret.addError("Errors validating resource: " + e.getMessage());
		}

		return ret;
	}

	@Override
	public List<Operation> getAllowedOperations(IResource resource) {

		List<Operation> ret = new ArrayList<>();

		if (resource != null) {
			boolean isBayes = BayesNet.class.getCanonicalName().equals(resource.getParameters().get("classifier"));
			File importFile = new File(resource.getLocalPath() + File.separator + "import.xml");
			if (isBayes && importFile.exists()) {
				ret.add(new ResourceReference.OperationReference("Learn CPTs",
						"Learn the CPTs from the original instances. Perform this operation after making modifications to the"
								+ " model, for example after importing a BIF file.",
						true));
			}
		}
		
		return ret;
	}

	@Override
	public IResource performOperation(IResource resource, String operationName, IMonitor monitor) {
		// TODO Auto-generated method stub
		return resource;
	}

	@Override
	public boolean canHandle(File resource, IParameters<String> parameters) {
		if (resource == null) {
			return false;
		}
		String extension = MiscUtilities.getFileExtension(resource);
		if (extension != null) {
			return extension.toLowerCase().equals("bif") || extension.toLowerCase().equals("xdsl")
					|| extension.toLowerCase().equals("model");
		}

		return false;
	}

	@Override
	public Collection<File> getAllFilesForResource(File file) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean validateImport(IResource resource, IMonitor monitor) {

		WekaClassifier classifier = null;
		File imported = new File(((Resource) resource).getPath() + File.separator + "import.xml");
		if (imported.exists()) {
			try {
				classifier = new WekaClassifier(imported, resource.getParameters().get("classifier", String.class),
						resource.getParameters().get("classifier.probabilistic", "false").equals("true"));
			} catch (Throwable e) {
				monitor.error(e);
				return false;
			}
		} else {
			return false;
		}

		return classifier != null && classifier.getClassifier() != null;
	}

}
