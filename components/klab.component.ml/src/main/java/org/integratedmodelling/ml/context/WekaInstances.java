package org.integratedmodelling.ml.context;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.utils.KimUtils;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.ml.contextualizers.WEKAResolverLegacy.Var;

import weka.core.Attribute;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

public class WekaInstances {

	private IObservable predicted = null;
	private List<IState> predictors = new ArrayList<>();
	private ObservationGroup archetype = null;

	private Instances instances;
	private List<Attribute> attributes;

	public WekaInstances(IObservable predicted, IModel model, IRuntimeContext context) {

		this.predicted = predicted;

		for (IObservable dependency : model.getDependencies()) {
			IAnnotation predictor = KimUtils.findAnnotation(dependency.getAnnotations(), "predictor");
			if (predictor != null) {
				IArtifact artifact = context.getArtifact(dependency.getLocalName());
				if (!(artifact instanceof IState)) {
					throw new IllegalArgumentException("Weka: predictors must be observations of qualities");
				}
				predictors.add((IState) artifact);
			} else {
				IAnnotation arch = KimUtils.findAnnotation(dependency.getAnnotations(), "archetype");
				if (arch != null) {
					IArtifact artifact = context.getArtifact(dependency.getLocalName());
					if (!(artifact instanceof ObservationGroup)) {
						throw new IllegalArgumentException("Weka: archetypes must be observations of objects");
					}
					archetype = (ObservationGroup) artifact;
				}
			}
		}
	}

	/**
	 * Build if necessary and return a WEKA instances set from the model
	 * configuration and the context.
	 * 
	 * @return
	 */
	public Instances getInstances() {
		if (instances == null) {
			build();
		}
		return instances;
	}

	/**
	 * Return all attributes, creating them if necessary. Predicted attribute is
	 * first as per WEKA conventions.
	 * 
	 * @return
	 */
	public List<Attribute> getAttributes() {
		
		if (this.attributes == null) {
			this.attributes = new ArrayList<>();
			this.attributes.add(getAttribute(predicted));
			for (IState var : predictors) {
				this.attributes.add(getAttribute(var.getObservable()));
			}
		}
		return this.attributes;
	}

	private Attribute getAttribute(IObservable var) {
		// TODO Auto-generated method stub
		return null;
	}

	private void build() {
		// TODO Auto-generated method stub

	}

	/**
	 * Export to an AIRFF file.
	 * 
	 * @param file
	 * @throws IOException
	 */
	public void export(File file) {
		ArffSaver saver = new ArffSaver();
		saver.setInstances(getInstances());
		try {
			saver.setFile(file);
			// saver.setDestination(file);
			saver.writeBatch();
		} catch (Exception e) {
			throw new KlabIOException(e);
		}
	}

}
