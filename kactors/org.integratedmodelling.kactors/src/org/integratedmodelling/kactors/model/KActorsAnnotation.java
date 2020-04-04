package org.integratedmodelling.kactors.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kactors.kactors.Annotation;
import org.integratedmodelling.kactors.kactors.KeyValuePair;
import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimMetadata;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.klab.utils.Parameters;

/**
 * We use KDL annotations as they're the most flexible. TODO put these in the API package and 
 * share them across the three languages.
 * 
 * @author Ferd
 *
 */
public class KActorsAnnotation extends Parameters<String> implements IKimAnnotation {

	private String name;
	private Set<String> interactiveParameters = new HashSet<>();
	
	public KActorsAnnotation(Annotation statement) {

		this.name = statement.getName().substring(1);

		if (statement.getParameters() != null) {
			if (statement.getParameters().getPairs() != null) {
				for (KeyValuePair pair : statement.getParameters().getPairs()) {
					String key = pair.getName();
					KActorsValue value = new KActorsValue(pair.getValue(), null);
					put(key == null ? IKimAnnotation.DEFAULT_PARAMETER_NAME : key, value);
				}
			}
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Parameters<String> getParameters() {
		return this;
	}

	@Override
	public int getParameterCount() {
		return size();
	}

	@Override
	public IPrototype getPrototype() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getInteractiveParameters() {
		return interactiveParameters;
	}

	@Override
	public IKimMetadata getDocumentationMetadata() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNamespace() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IKimStatement getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getResourceId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFirstLine() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLastLine() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFirstCharOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLastCharOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<IKimAnnotation> getAnnotations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDeprecation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDeprecated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getSourceCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isErrors() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWarnings() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IKimMetadata getMetadata() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IKimScope> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLocationDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void visit(Visitor visitor) {
		// TODO Auto-generated method stub
		
	}
}
