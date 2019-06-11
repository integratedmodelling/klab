package org.integratedmodelling.ml.adapters;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.StringUtils;
import org.integratedmodelling.klab.utils.Triple;
import org.integratedmodelling.klab.utils.URLUtils;
import org.integratedmodelling.klab.utils.WildcardMatcher;
import org.integratedmodelling.ml.context.WekaClassifier;
import org.integratedmodelling.ml.legacy.riskwiz.io.Converter;

import weka.classifiers.bayes.BayesNet;

public class WekaImporter implements IResourceImporter {

    @Override
    public Collection<Builder> importResources(String importLocation, IParameters<String> userData, IMonitor monitor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean canHandle(String importLocation, IParameters<String> userData) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Collection<Triple<String, String, String>> getExportCapabilities(IObservation observation) {
        List<Triple<String, String, String>> ret = new ArrayList<>();
        return ret;
    }

    @Override
    public File exportObservation(File file, IObservation observation, ILocator locator, String format,
            IMonitor monitor) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, String> getExportCapabilities(IResource resource) {

        Map<String, String> ret = new LinkedHashMap<>();

        if (resource != null) {
            if (BayesNet.class.getCanonicalName().equals(resource.getParameters().get("classifier", String.class))) {
                ret.put("bif", "BIF bayesian network (.bif)");
                ret.put("xdsl", "GENIE bayesian network (.xdsl)");
            }
            if (resource.getParameters().containsKey("instances.file")) {
                ret.put("arff", "Training dataset (ARFF)");
            }
            if (resource.getParameters().containsKey("instances.file.raw")) {
                ret.put("arff#raw", "Raw training dataset (ARFF)");
            }
        }

        ret.put("model", "WEKA Classifier (.model)");

        if (resource != null) {
            // discretizers
            if (resource.getParameters().containsKey("predicted.discretizer")) {
                ret.put("model#predicted", "WEKA discretizer for the predicted variable (.model)");
            }
            for (String key : resource.getParameters().keySet()) {
                if (WildcardMatcher.matches(key, "predictor.*.discretizer.file")) {
                    String predictor = key.split("\\.")[1];
                    ret.put("model#" + predictor,
                            "WEKA Discretizer for " + StringUtils.beautify(predictor) + " (.model)");
                }
            }
        }
        return ret;
    }

    @Override
    public boolean exportResource(File file, IResource resource, String format) {

        try {
            switch (format) {
            case "bif":
            case "xdsl":
                exportBN(resource, format, file);
                break;
            case "arff":
                FileUtils.copyFile(((Resource) resource).getLocalFile("instances.file"), file);
                break;
            case "arff#raw":
                FileUtils.copyFile(((Resource) resource).getLocalFile("instances.file.raw"), file);
                break;
            case "model":
                FileUtils.copyFile(((Resource) resource).getLocalFile("classifier.file"), file);
                break;
            case "model#predicted":
                FileUtils.copyFile(((Resource) resource).getLocalFile("predicted.discretizer.file"), file);
                break;
            default:
                if (format.startsWith("model#")) {
                    int pound = format.lastIndexOf('#');
                    String id = format.substring(pound + 1);
                    FileUtils.copyFile(((Resource) resource).getLocalFile("predictor." + id + ".discretizer.file"),
                            file);
                }
            }

            return true;

        } catch (IOException e) {
            return false;
        }
    }

    private void exportBN(IResource resource, String format, File output) throws IOException {
        
        String bif = null;
        File imported = new File(((Resource) resource).getPath() + File.separator + "import.xml");
        if (imported.exists()) {
            bif = FileUtils.readFileToString(imported);
        } else {
            WekaClassifier classifier = new WekaClassifier(((Resource) resource).getLocalFile("classifier.file"),
                    resource.getParameters().get("classifier.probabilistic", "false").equals("true"));
            BayesNet bn = (BayesNet) classifier.getClassifier();
            bif = bn.toXMLBIF03();
        }
        if ("bif".equals(format)) {
            FileUtils.writeStringToFile(output, bif);
        } else if ("xdsl".equals(format)) {
            File temp = File.createTempFile("weka", ".xml");
            FileUtils.writeStringToFile(temp, bif);
            Converter.bifToGenie(temp.toString(), output.toString());
        }
        
    }

    @Override
    public boolean importIntoResource(URL importLocation, IResource target, IMonitor monitor) {

        /*
         * BIF, XML, XDSL imports only allowed for BayesNet as far as I know.
         */
        if (BayesNet.class.getCanonicalName().equals(target.getParameters().get("classifier", String.class))) {

            String format = MiscUtilities.getFileExtension(importLocation.toString());
            File originalFile = null;
            if (!"file".equals(importLocation.getProtocol())) {
                try {
                    URLUtils.copy(importLocation, originalFile = File.createTempFile("imp", "." + format));
                } catch (IOException e) {
                    throw new KlabIOException(e);
                }
            } else {
                originalFile = new File(importLocation.getFile());
            }

            File bifFile = new File(((Resource) target).getPath() + File.separator + "import.xml");

            if ("xdsl".equals(format)) {
                Converter.genieToBif(originalFile.toString(), bifFile.toString());
            } else if ("bif".equals(format) || "xml".equals(format)) {
                try {
                    FileUtils.copyFile(originalFile, bifFile);
                } catch (IOException e) {
                    throw new KlabIOException(e);
                }
            }

            return new WekaValidator().validateImport(target, monitor);
        }

        return false;
    }

    @Override
    public boolean resourceCanHandle(IResource resource, String importLocation) {
        // TODO Auto-generated method stub
        return false;
    }

}
