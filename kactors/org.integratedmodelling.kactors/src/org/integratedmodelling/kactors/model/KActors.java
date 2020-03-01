package org.integratedmodelling.kactors.model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.integratedmodelling.kactors.KactorsStandaloneSetup;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.kactors.Model;
import org.integratedmodelling.kactors.utils.KActorsResourceSorter;
import org.integratedmodelling.klab.api.errormanagement.ICompileNotification;
import org.integratedmodelling.klab.common.CompileNotification;

import com.google.inject.Injector;

public enum KActors {

	INSTANCE;

	class BehaviorDescriptor {
		String name;
		File file;
		List<ICompileNotification> notifications = new ArrayList<>();
		int nInfo;
		int nWarning;
		int nErrors;
		IKActorsBehavior behavior;
		String projectName;
	}

	Injector injector;
	IResourceValidator validator;

	Map<String, BehaviorDescriptor> behaviors = new HashMap<>();

	private Injector getInjector() {
		if (this.injector == null) {
			this.injector = new KactorsStandaloneSetup().createInjectorAndDoEMFRegistration();
		}
		return this.injector;
	}

	public IKActorsBehavior declare(Model model) {
		return new KActorsBehavior(model, null);
	}

	public boolean isKActorsFile(File file) {
		return file.toString().endsWith(".kactor");
	}

	private IResourceValidator getValidator() {
		if (this.validator == null) {
			this.validator = getInjector().getInstance(IResourceValidator.class);
		}
		return this.validator;
	}

	public void loadResources(List<File> behaviorFiles) {

		KActorsResourceSorter bsort = new KActorsResourceSorter(behaviorFiles);
		IResourceValidator validator = getValidator();

		for (Resource resource : bsort.getResources()) {

			BehaviorDescriptor ret = new BehaviorDescriptor();

			ret.name = ((Model) resource.getContents().get(0)).getPreamble().getName();
			ret.file = bsort.getFile(resource);
			ret.projectName = getProjectName(resource.getURI().toString());
			
			List<Issue> issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
			for (Issue issue : issues) {
				ICompileNotification notification = getNotification(ret.name, issue, ret);
				if (notification != null) {
					ret.notifications.add(notification);
				}
			}
			
			ret.behavior = new KActorsBehavior(((Model) resource.getContents().get(0)), ret);
			
			behaviors.put(ret.name, ret);
		}
	}
	
	/**
	 * Return all regular behaviors defined in the src/ directory alongside models for
	 * the project.
	 * 
	 * @param project
	 * @return
	 */
	public List<IKActorsBehavior> getBehaviors(String project) {
		List<IKActorsBehavior> ret = new ArrayList<>();
		for (BehaviorDescriptor bd : behaviors.values()) {
			if (project.equals(bd.projectName)) {
				ret.add(bd.behavior);
			}
		}
		return ret;
	}

//    public IKdlDataflow declare(Model dataflow) {
//        return new KdlDataflow(dataflow);
//    }
//
//    public Number parseNumber(org.integratedmodelling.kdl.kdl.Number number) {
//        ICompositeNode node = NodeModelUtils.findActualNodeFor(number);
//        if (number.isExponential() || number.isDecimal()) {
//            return Double.parseDouble(node.getText().trim());
//        } 
//        return Integer.parseInt(node.getText().trim());
//    }
//    
//    public Object parseValue(Value value) {
//        if (value.getLiteral() != null) {
//            return parseLiteral(value.getLiteral());
//        } else if (value.getFunction() != null) {
//        	if (value.getFunction().getParameters() == null) {
//        		return value.getFunction().getName();
//        	} else {
//        		// TODO!
//        	}
//        } else if (value.getList() != null) {
//            List<Object> ret = new ArrayList<>();
//            for (Value val : value.getList().getContents()) {
//                if (!(val.getLiteral() != null && val.getLiteral().isComma())) {
//                    ret.add(parseValue(val));
//                }
//            }
//            return ret;
//        } 
//        
//        return null;
//    }
//    
//    private Object parseLiteral(Literal literal) {
//        if (literal.getBoolean() != null) {
//            return Boolean.parseBoolean(literal.getBoolean());
//        } else if (literal.getNumber() != null) {
//            return parseNumber(literal.getNumber());
//        } else if (literal.getId() != null) {
//            return literal.getId();
//        } else if (literal.getString() != null) {
//            return literal.getString();
//        } else if (literal.getFrom() != null) {
//            Range range = new Range();
//            range.low = parseNumber(literal.getFrom()).doubleValue();
//            if (literal.getTo() != null) {
//                range.high = parseNumber(literal.getTo()).doubleValue();
//            }
//            return range;
//        }
//        return null;
//    }

	/**
	 * Get the project name from the string form of any Xtext resource URI.
	 * 
	 * @param resourceURI
	 * @return
	 */
	public String getProjectName(String resourceURI) {

		String ret = null;
		try {
			URL url = new URL(resourceURI);
			String path = url.getPath();
			Properties properties = null;
			URL purl = null;
			while ((path = chopLastPathElement(path)) != null) {
				purl = new URL(url.getProtocol(), url.getAuthority(), url.getPort(),
						path + "/META-INF/klab.properties");
				try (InputStream is = purl.openStream()) {
					properties = new Properties();
					properties.load(is);
					break;
				} catch (IOException exception) {
					continue;
				}
			}
			if (properties != null) {
				ret = path.substring(path.lastIndexOf('/') + 1);
			}

		} catch (Exception e) {
			// just return null;
		}

		return ret;
	}

	private ICompileNotification getNotification(String name, Issue issue, BehaviorDescriptor desc) {

		Level level = null;
		ICompileNotification ret = null;

		switch (issue.getSeverity()) {
		case ERROR:
			desc.nErrors ++;
			level = Level.SEVERE;
			break;
		case INFO:
			desc.nInfo ++;
			level = Level.INFO;
			break;
		case WARNING:
			desc.nWarning ++;
			level = Level.WARNING;
			break;
		default:
			break;
		}

		if (level != null) {
			ret = CompileNotification.create(level, issue.getMessage(), name, KActorStatement.createDummy(issue));
		}

		return ret;
	}

	private String chopLastPathElement(String path) {
		int idx = path.lastIndexOf('/');
		if (idx <= 0) {
			return null;
		}
		return path.substring(0, idx);
	}
}
