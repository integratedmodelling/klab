package org.integratedmodelling.ml.context;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.utils.KimUtils;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.ml.MLComponent;

import com.google.common.collect.Lists;

import weka.core.Attribute;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

public class WekaInstances {

	private IState predicted = null;
	private List<IState> predictors = new ArrayList<>();
	private ObservationGroup archetype = null;

	private Instances instances;
	private ArrayList<Attribute> attributes;
	private String name;
	private IRuntimeContext context;

	public WekaInstances(IState predicted, IModel model, IRuntimeContext context) {

		this.predicted = predicted;
		this.name = predicted.getObservable().getLocalName();
		this.context = context;
		
		for (IObservable dependency : model.getDependencies()) {
			IAnnotation predictor = KimUtils.findAnnotation(dependency.getAnnotations(), MLComponent.PREDICTOR_ANNOTATION);
			if (predictor != null) {
				IArtifact artifact = context.getArtifact(dependency.getLocalName());
				if (!(artifact instanceof IState)) {
					throw new IllegalArgumentException("Weka: predictors must be observations of qualities");
				}
				predictors.add((IState) artifact);
			} else {
				IAnnotation arch = KimUtils.findAnnotation(dependency.getAnnotations(), MLComponent.ARCHETYPE_ANNOTATION);
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
	public ArrayList<Attribute> getAttributes() {
		
		if (this.attributes == null) {
			this.attributes = new ArrayList<>();
			this.attributes.add(getAttribute(predicted));
			for (IState var : predictors) {
				this.attributes.add(getAttribute(var));
			}
		}
		return this.attributes;
	}

	private Attribute getAttribute(IState observable) {
		
		Attribute ret = null;
		switch (observable.getObservable().getArtifactType()) {
		case NUMBER:
			ret = new Attribute(observable.getObservable().getLocalName());
			break;
		case CONCEPT:
			ret = new Attribute(observable.getObservable().getLocalName(), new ArrayList<>(observable.getDataKey().getLabels()));
			break;
		case BOOLEAN:
			ret = new Attribute(observable.getObservable().getLocalName(), Lists.newArrayList("true", "false"));
			break;
		default:
	// shouldn't happen.
			throw new IllegalStateException(
					"WEKA learning process: occurrence state " + observable + " is not numeric, categorical or boolean");
		}
		return ret;
	}

	private void build() {
		
		if (this.archetype == null) {
			throw new IllegalStateException("Weka: cannot build training set without an archetype");
		}
		if (predictors.size() < 1) {
			throw new IllegalStateException("Weka: not enough predictors to build a training set");
		}

		this.instances = new Instances(name + "_instances", getAttributes(), 0);

		for (IArtifact object : this.archetype) {
			for (IState state : ((IDirectObservation) object).getStates()) {
				// TODO
//				Object o = state.aggregate(context.getScale(), ...);
			}
			
		}
	}
	
	public void requireDiscretization() {
		// TODO discretize anything that isn't discretized; set annotations for each observable so it can be reconstructed
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
