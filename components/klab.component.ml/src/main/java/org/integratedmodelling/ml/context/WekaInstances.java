package org.integratedmodelling.ml.context;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.utils.KimUtils;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.kim.Prototype;
import org.integratedmodelling.klab.utils.Utils;
import org.integratedmodelling.ml.MLComponent;

import com.google.common.collect.Lists;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Discretize;

public class WekaInstances {

	private IState predicted = null;
	private List<IState> predictors = new ArrayList<>();
	private ObservationGroup archetype = null;
	private boolean requiresDiscretization = false;
	private Instances rawInstances, instances;
	private ArrayList<Attribute> attributes;
	private String name;
	private IRuntimeContext context;
	private IConcept weightObservable;
	private Map<String, Double> attributeWeights = new HashMap<>();
	private IServiceCall classDiscretizer;

	Map<String, IAnnotation> annotations = new HashMap<>();

	public WekaInstances(IState predicted, IModel model, IRuntimeContext context, boolean mustDiscretize,
			IServiceCall classDiscretizer) {

		this.predicted = predicted;
		this.name = predicted.getObservable().getLocalName();
		this.context = context;
		this.requiresDiscretization = mustDiscretize;
		this.classDiscretizer = classDiscretizer;

		for (IObservable dependency : model.getDependencies()) {
			IAnnotation predictor = KimUtils.findAnnotation(dependency.getAnnotations(),
					MLComponent.PREDICTOR_ANNOTATION);
			if (predictor != null) {
				IArtifact artifact = context.getArtifact(dependency.getLocalName());
				if (!(artifact instanceof IState)) {
					throw new IllegalArgumentException("Weka: predictors must be observations of qualities");
				}
				predictors.add((IState) artifact);
				annotations.put(((IState) artifact).getObservable().getLocalName(), predictor);
				attributeWeights.put(((IState) artifact).getObservable().getLocalName(), predictor.get("weight", 1.0));
			} else {
				IAnnotation arch = KimUtils.findAnnotation(dependency.getAnnotations(),
						MLComponent.ARCHETYPE_ANNOTATION);
				if (arch != null) {
					IArtifact artifact = context.getArtifact(dependency.getLocalName());
					if (!(artifact instanceof ObservationGroup)) {
						throw new IllegalArgumentException("Weka: archetypes must be observations of objects");
					}
					this.archetype = (ObservationGroup) artifact;
					this.weightObservable = arch.get("weight", IConcept.class);
					attributeWeights.put(((ObservationGroup) artifact).getObservable().getLocalName(), 1.0);
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
	 * Build if necessary and return the original, undiscretized instance set.
	 * 
	 * @return
	 */
	public Instances getRawInstances() {
		if (instances == null) {
			build();
		}
		return rawInstances;
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
			ret = new Attribute(observable.getObservable().getLocalName(),
					new ArrayList<>(observable.getDataKey().getLabels()));
			break;
		case BOOLEAN:
			ret = new Attribute(observable.getObservable().getLocalName(), Lists.newArrayList("false", "true"));
			break;
		default:
			// shouldn't happen.
			throw new IllegalStateException("WEKA learning process: occurrence state " + observable
					+ " is not numeric, categorical or boolean");
		}

		// attribute weight from predictor annotation. The archetype has always 1.
		Double weight = attributeWeights.get(observable.getObservable().getLocalName());
		ret.setWeight(weight == null ? 1.0 : weight);

		return ret;
	}

	private void build() {

		if (this.archetype == null) {
			throw new IllegalStateException("Weka: cannot build training set without an archetype");
		}
		if (predictors.size() < 1) {
			throw new IllegalStateException("Weka: not enough predictors to build a training set");
		}

		this.rawInstances = new Instances(name + "_instances", getAttributes(), 0);

		Map<String, Integer> stateIndex = null;

		int objects = 0;
		int skipped = 0;

		for (IArtifact object : this.archetype) {

			Object[] instanceValues = new Object[predictors.size() + 1];
			double instanceWeight = 1;

			if (stateIndex == null) {
				// build map of predicted/observable name to index in instance. We use one
				// artifact so the observable names are stable.
				stateIndex = new HashMap<>();
				for (IState state : ((IDirectObservation) object).getStates()) {
					if (state.getObservable().equals(predicted.getObservable())) {
						stateIndex.put(state.getObservable().getLocalName(), 0);
					} else {
						int i = 1;
						for (IState predictor : predictors) {
							if (state.getObservable().equals(predictor.getObservable())) {
								stateIndex.put(state.getObservable().getLocalName(), i);
							} else if (weightObservable != null && state.getObservable().is(weightObservable)) {
								instanceWeight = state.aggregate(((IObservation) object).getScale(), Double.class);
							}
							i++;
						}
					}
				}

				if (stateIndex.size() != predictors.size() + 1) {
					throw new IllegalStateException(
							"Weka: the archetype observations do not contain values for the learned quality and all predictors");
				}
			}

			this.rawInstances.setClassIndex(0);

			boolean ignore = false;
			for (IState state : ((IDirectObservation) object).getStates()) {
				if (stateIndex.containsKey(state.getObservable().getLocalName())) {
					Object o = state.aggregate(((IObservation) object).getScale(),
							Utils.getClassForType(state.getObservable().getArtifactType()));
					if (Observations.INSTANCE.isNodata(o)) {
						skipped++;
						ignore = true;
						break;
					} else {
						instanceValues[stateIndex.get(state.getObservable().getLocalName())] = o;
						objects++;
					}
				}
			}

			if (!ignore) {
				// remap attributes to doubles as needed; ignore instance if there are errors
				double[] values = mapValuesToDoubles(instanceValues);
				if (values != null) {
					rawInstances.add(new DenseInstance(instanceWeight, values));
				} else {
					skipped++;
					objects--;
				}
			}
		}

		context.getMonitor()
				.info("Weka: training set generated with " + objects + " instances (" + skipped + " skipped)");

		// go through discretization for each attribute, choose scheme if
		// discretization is mandatory and attribute is numeric
		this.instances = rawInstances;

		/*
		 * discretize the class attribute if requested or required
		 */
		if (requiresDiscretization || this.classDiscretizer != null) {
			try {
				this.instances = Filter.useFilter(this.instances, buildDiscretization(classDiscretizer, predicted, 1));
			} catch (Exception e) {
				throw new IllegalStateException(
						"Weka: error during discretization of class attribute: " + e.getMessage());
			}
		}

		int i = 2;
		for (IState predictor : predictors) {
			IAnnotation annotation = annotations.get(predictor.getObservable().getLocalName());
			if (predictor.getObservable().getArtifactType() == Type.NUMBER) {
				try {
					if (annotation.containsKey("discretization")) {
						// build discretizer for i-th field
						this.instances = Filter.useFilter(this.instances, buildDiscretization(
								annotation.get("discretization", IServiceCall.class), predictor, i));
					} else if (requiresDiscretization) {
						// create default discretizer for i-th field
						this.instances = Filter.useFilter(this.instances, buildDiscretization(null, predictor, i));
					}
				} catch (Exception e) {
					throw new IllegalStateException("Weka: error during discretization of "
							+ predictor.getObservable().getLocalName() + ": " + e.getMessage());
				}
			} else if (annotation.containsKey("discretization")) {
				throw new IllegalArgumentException("Weka: " + predictor.getObservable().getLocalName()
						+ ": cannot specify discretization for non-numeric predictors");
			}
			i++;
		}
	}

	private Filter buildDiscretization(IServiceCall specification, IState predictor, int fieldIndex) {

		String options = "-Y";
		Class<?> filterClass = Discretize.class;

		if (specification != null) {
			Prototype prototype = Extensions.INSTANCE.getPrototype(specification.getName());
			if (prototype == null) {
				throw new IllegalStateException(
						"No discretizer found for " + specification.getName() + ": check definition");
			}
			options = Kim.INSTANCE.createCommandLine(specification, prototype, "");
			filterClass = prototype.getExecutorClass();
		}

		options = "-R " + fieldIndex + " " + options;

		Filter filter = Extensions.INSTANCE.createDefaultInstance(filterClass, Filter.class);
		try {
			filter.setOptions(weka.core.Utils.splitOptions(options));
			filter.setInputFormat(this.instances);
		} catch (Exception e) {
			throw new IllegalStateException(
					"Error during WEKA option parsing for " + filterClass + " + : " + e.getMessage());
		}

		return filter;
	}

	private double[] mapValuesToDoubles(Object[] instanceValues) {
		double[] ret = new double[instanceValues.length];
		int i = 0;
		for (Attribute attribute : getAttributes()) {
			if (attribute.isNumeric()) {
				if (instanceValues[i] instanceof Number) {
					ret[i] = ((Number) instanceValues[i]).doubleValue();
				} else {
					return null;
				}
			} else {
				IState state = i == 0 ? predicted : predictors.get(i - 1);
				if (state.getObservable().getArtifactType() == Type.BOOLEAN) {
					ret[i] = instanceValues[i] instanceof Boolean ? (((Boolean) instanceValues[i]) ? 1 : 0) : 0;
				} else if (state.getObservable().getArtifactType() == Type.CONCEPT) {
					ret[i] = state.getDataKey().reverseLookup(instanceValues[i]);
				} else {
					// TODO eventually we can index text from non-semantic models using Weka's text
					// indexing features
					throw new KlabUnimplementedException(
							"Weka: only numeric, boolean or classification states are supported for now.");
				}
			}
			i++;
		}
		return ret;
	}

	public void requireDiscretization() {
		// TODO discretize anything that isn't discretized; set annotations for each
		// observable so it can be reconstructed
	}

	/**
	 * Export instances (raw or discretized) to an ARFF file.
	 * 
	 * @param file
	 * @throws IOException
	 */
	public void export(File file, boolean raw) {
		ArffSaver saver = new ArffSaver();
		saver.setInstances(raw ? getRawInstances() : getInstances());
		try {
			saver.setFile(file);
			saver.writeBatch();
		} catch (Exception e) {
			throw new KlabIOException(e);
		}
	}

}
