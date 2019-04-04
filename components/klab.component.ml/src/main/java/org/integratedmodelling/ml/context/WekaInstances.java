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
import org.integratedmodelling.klab.api.data.ILocator;
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
import org.integratedmodelling.klab.data.classification.Discretization;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.kim.Prototype;
import org.integratedmodelling.klab.utils.Utils;
import org.integratedmodelling.ml.MLComponent;

import com.google.common.collect.Lists;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Discretize;

public class WekaInstances {

	private static final int MAX_ALLOWED_NODATA = 2;
	private IState predicted = null;
	private List<IState> predictors = new ArrayList<>();
	private List<ObservationGroup> archetypes = new ArrayList<>();
	private boolean requiresDiscretization = false;
	private Instances rawInstances, instances;
	private ArrayList<Attribute> attributes;
	private String name;
	private IRuntimeContext context;
	private IConcept weightObservable;
	private Map<String, Double> attributeWeights = new HashMap<>();
	private IServiceCall classDiscretizer;
	private Map<String, DiscretizerDescriptor> discretizers = new HashMap<>();

	/**
	 * These also end up in the resource parameters, tagged as discretizer.attribute
	 * = javaclass/options. Constructors and toString() method allow rebuilding the
	 * discretizer as is (as long as it is applied to the same instance set).
	 * 
	 * @author ferdinando.villa
	 *
	 */
	static class DiscretizerDescriptor {

		String javaClass;
		String options;
		Filter discretizer;

		public DiscretizerDescriptor(String parameter) {
			int n = parameter.indexOf('/');
			this.javaClass = parameter.substring(0, n);
			this.options = parameter.substring(n + 1);
		}

		public DiscretizerDescriptor(String javaClass, String options) {
			this.javaClass = javaClass;
			this.options = options;
		}

		public Filter getDiscretizer() {
			if (this.discretizer == null) {
				try {
					this.discretizer = Extensions.INSTANCE.createDefaultInstance(Class.forName(javaClass),
							Filter.class);
					this.discretizer.setOptions(weka.core.Utils.splitOptions(options));
				} catch (Throwable e) {
					throw new IllegalStateException("Error creating discretizer: " + e.getMessage());
				}
			}
			return this.discretizer;
		}

		public String toString() {
			return javaClass + "/" + options;
		}
	}

	Map<String, IAnnotation> annotations = new HashMap<>();
	private boolean admitsNodata;
	private boolean errorWarning;
	private Discretization predictedDiscretization;

	// TODO set these from instances unless they're set by the archetype notation
	private double predictedMin = Double.NaN;
	private double predictedMax = Double.NaN;

	public WekaInstances(IState predicted, IModel model, IRuntimeContext context, boolean mustDiscretize,
			boolean admitsNodata, IServiceCall classDiscretizer) {

		this.predicted = predicted;
		this.name = predicted.getObservable().getLocalName();
		this.context = context;
		this.requiresDiscretization = mustDiscretize;
		this.classDiscretizer = classDiscretizer;
		this.admitsNodata = admitsNodata;

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
					if (arch.containsKey("min")) {
						this.predictedMin = arch.get("min", Double.NaN);
					}
					if (arch.containsKey("max")) {
						this.predictedMax = arch.get("max", Double.NaN);
					}
					this.archetypes.add((ObservationGroup) artifact);
					if (arch.containsKey("weight")) {
						this.weightObservable = arch.get("weight", IConcept.class);
						attributeWeights.put(((ObservationGroup) artifact).getObservable().getLocalName(), 1.0);
					}
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

	public int size() {
		return getInstances().size();
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

		if (this.archetypes.isEmpty()) {
			throw new IllegalStateException("Weka: cannot build training set without at least one archetype");
		}
		if (predictors.size() < 1) {
			throw new IllegalStateException("Weka: not enough predictors to build a training set");
		}

		this.rawInstances = new Instances(name + "_instances", getAttributes(), 0);

		Map<String, Integer> stateIndex = null;

		int skipped = 0;
		for (ObservationGroup archetype : archetypes) {
			
			for (IArtifact object : archetype) {

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

				// TODO set min/max from instances if any is NaN

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
					}
				}
			}
		}

		context.getMonitor()
				.info("Weka: training set generated with " + rawInstances.size() + " instances (" + skipped + " skipped)");

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
				throw new IllegalStateException("No discretizer implementation linked to function "
						+ specification.getName() + ": check definition");
			}
			options = Kim.INSTANCE.createCommandLine(specification.getParameters(), prototype, "");
			filterClass = prototype.getExecutorClass();
		}

		options = "-R " + fieldIndex + " " + options;

		DiscretizerDescriptor descriptor = new DiscretizerDescriptor(filterClass.getCanonicalName(), options);
		try {
			descriptor.getDiscretizer().setInputFormat(this.instances);
			discretizers.put(predictor.getObservable().getLocalName(), descriptor);
		} catch (Exception e) {
			throw new IllegalStateException(
					"Error during WEKA option parsing for " + filterClass + " + : " + e.getMessage());
		}

		return descriptor.getDiscretizer();
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
							"Weka: only numeric, boolean or classification predictors are supported for now.");
				}
			}
			i++;
		}

		/*
		 * define any limits that are not already defined; don't allow the specified
		 * min/max to specify a narrower interval than the value spread.
		 */
		if (predicted.getObservable().getArtifactType() == Type.NUMBER && !Double.isNaN(ret[0])) {
			if (Double.isNaN(predictedMin) || predictedMin > ret[0]) {
				predictedMin = ret[0];
			}
			if (Double.isNaN(predictedMax) || predictedMax < ret[0]) {
				predictedMax = ret[0];
			}
		}

		return ret;
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

	/**
	 * Produce an instance from the context and a location using the discretizers
	 * for each predictor, leaving the class attribute as nodata. If nodata are not
	 * admitted and are encountered, or if too many values are nodata, the instance
	 * returned is null.
	 * 
	 * @param locator
	 * @return a new instance or null.
	 */
	public Instance getInstance(ILocator locator) {

		Instance ret = new DenseInstance(predictors.size() + 1);
		ret.setDataset(getInstances());
		ret.setClassMissing();

		int i = 1;
		int ndata = 0;
		for (IState predictor : predictors) {
			Object o = predictor.get(locator);
			if (Observations.INSTANCE.isData(o)) {

				double value = Double.NaN;

				DiscretizerDescriptor filter = discretizers.get(predictor.getObservable().getLocalName());
				Filter discretizer = null;
				if (filter != null) {
					discretizer = filter.getDiscretizer();
				}

				if (predictor.getObservable().getArtifactType() == Type.NUMBER) {
					value = o instanceof Number ? ((Number) o).doubleValue() : Double.NaN;
				} else if (predictor.getObservable().getArtifactType() == Type.BOOLEAN) {
					value = o instanceof Boolean ? (((Boolean) o) ? 1 : 0) : 0;
				} else if (predictor.getObservable().getArtifactType() == Type.CONCEPT) {
					value = predictor.getDataKey().reverseLookup(o);
				} else {
					throw new KlabUnimplementedException(
							"Weka: only numeric, boolean or classification predictors are supported for now.");
				}

				if (Double.isNaN(value)) {
					if (admitsNodata) {
						ret.setMissing(i);
					} else {
						return null;
					}
				} else {
					ret.setValue(i, value);
					ndata++;
				}

			} else if (admitsNodata) {
				ret.setMissing(i);
			} else {
				return null;
			}
			i++;
		}

		if (ndata < (predictors.size() - MAX_ALLOWED_NODATA)) {
			return null;
		}

		// go through the discretizers
		for (String obs : discretizers.keySet()) {
			if (!obs.equals(predicted.getObservable().getLocalName())) {
				try {
					Filter discretizer = discretizers.get(obs).getDiscretizer();
					if (!discretizer.input(ret)) {
						discretizer.batchFinished();
					}
					ret = discretizer.output();
				} catch (Exception e) {
					if (!errorWarning) {
						errorWarning = true;
						context.getMonitor().warn(
								"Generation of instance to classify generated an error (further errors will not be reported): "
										+ e.getMessage());
					}
				}
			}
		}
		return ret;
	}

	/**
	 * Return the distribution breakpoints for the predicted state, computing them
	 * if necessary.
	 * 
	 * TODO needs a min and a max (either from the archetype or from the call)
	 * 
	 * @return
	 */
	public Discretization getPredictedDiscretization() {

		if (this.predictedDiscretization == null) {
			DiscretizerDescriptor filter = discretizers.get(predicted.getObservable().getLocalName());
			if (filter == null) {
				throw new IllegalStateException(
						"Weka: cannot interpret a distribution if the predicted variable is not discretized.");
			}
			// no API for getCutPoints() - what a pain
			double[] cutpoints = null;
			if (filter.getDiscretizer() instanceof Discretize) {
				cutpoints = ((Discretize) filter.getDiscretizer()).getCutPoints(0);
			} else if (filter.getDiscretizer() instanceof weka.filters.supervised.attribute.Discretize) {
				cutpoints = ((Discretize) filter.getDiscretizer()).getCutPoints(0);
			} else {
				throw new KlabUnimplementedException("Weka: cannot get cut points from discretizer of class "
						+ filter.getDiscretizer().getClass().getCanonicalName() + ": please report to developers");
			}
			this.predictedDiscretization = new Discretization(predictedMin, cutpoints, predictedMax);

		}
		return this.predictedDiscretization;
	}

}
