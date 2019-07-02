package org.integratedmodelling.klab.model;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.data.Metadata;

public abstract class KimObject implements IKimObject {

	private IKimStatement statement;
	private boolean deprecated;
	private IMetadata metadata = new Metadata();
	protected List<IKimObject> children = new ArrayList<>();
	private List<IAnnotation> annotations = new ArrayList<>();
	private boolean errors;
	
	@Override
	public boolean isErrors() {
		return errors;
	}

	public void setErrors(boolean errors) {
		this.errors = errors;
	}

	public KimObject(IKimStatement statement) {
		this.statement = statement;
		if (statement != null) {
			for (IKimAnnotation annotation : statement.getAnnotations()) {
				annotations.add(new Annotation(annotation));
			}
		}
	}

	@Override
	public List<IKimObject> getChildren() {
		return children;
	}

	@Override
	public IKimStatement getStatement() {
		return statement;
	}

	@Override
	public List<IAnnotation> getAnnotations() {
		return annotations;
	}

	protected void setStatement(IKimStatement statement) {
		this.statement = statement;
	}

	public IMetadata getMetadata() {
		return metadata;
	}

	public String toString() {
		return statement.toString();
	}

	@Override
	public boolean isDeprecated() {
		return deprecated;
	}

	public void setDeprecated(boolean deprecated) {
		this.deprecated = deprecated;
	}

	abstract public INamespace getNamespace();
}
