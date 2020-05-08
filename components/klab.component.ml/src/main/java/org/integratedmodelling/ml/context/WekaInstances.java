package org.integratedmodelling.ml.context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.integratedmodelling.kim.api.IKimConcept.ObservableRole;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.utils.KimUtils;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Types;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.extensions.ILanguageExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.components.runtime.observations.State;
import org.integratedmodelling.klab.data.classification.Classification;
import org.integratedmodelling.klab.data.classification.Discretization;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.kim.Prototype;
import org.integratedmodelling.klab.owl.ObservableBuilder;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Range;
import org.integratedmodelling.klab.utils.Utils;

import com.google.common.collect.Lists;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffLoader.ArffReader;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Discretize;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;

public class WekaInstances {

	private static final int MAX_ALLOWED_NODATA = 2;

	private IObservable predictedObservable = null;
	private IState predictedState = null;
	/*
	 * we either have state predictor states (i.e. distributed over the context,
	 * checked after recontextualizing to the archetype) or predictorObservables (in
	 * the archetype model, to learn a quality WITHIN the archetype).
	 */
	private List<IState> predictorStates = new ArrayList<>();
	private List<IObservable> predictorObservables = new ArrayList<>();

	/*
	 * from the above, we build a list of observables to match in the archetype,
	 * with the final name that they will have as the input of the learned resource
	 * and the final type without any unnecessary inherency. This step is done on
	 * demand when getPredictorObservables() is called.
	 */
	private List<IObservable> predictors = null;
	private IState distributedArchetype = null;
	private List<ObservationGroup> archetypes = new ArrayList<>();
	private boolean requiresDiscretization = false;
	private Instances rawInstances, instances;
	private ArrayList<Attribute> attributes;
	private String name;
	private IRuntimeScope context;
	private IConcept weightObservable;
	private Map<String, Double> attributeWeights = new HashMap<>();
	private IServiceCall classDiscretizer;
	private Map<String, DiscretizerDescriptor> discretizers = new HashMap<>();
	private Map<String, Ranges> ranges = new HashMap<>();
	private ReplaceMissingValues missingValuesFilter = new ReplaceMissingValues();
	private Map<String, IDataKey> dataKeys = new HashMap<>();

	// these are for distributed archetypes
	private double selectFraction = Double.NaN;
	IKimExpression selector = null;
	Descriptor selectorDescriptor = null;

	class Ranges {
		Range include = null;
		Range exclude = null;
	}

	/**
	 * These also end up in the resource parameters, tagged as discretizer.attribute
	 * = javaclass/options. Constructors and toString() method allow rebuilding the
	 * discretizer as is (as long as it is applied to the same instance set).
	 * 
	 * @author ferdinando.villa
	 *
	 */
	static public class DiscretizerDescriptor {

		private String javaClass;
		private String options;
		private Filter discretizer;
		private Range include;
		private Range exclude;
		private int index;

		public DiscretizerDescriptor(String javaClass, String options, int index) {
			this.javaClass = javaClass;
			this.options = options;
			this.index = index;
		}

		// used in encoder, leaves other parameters null
		public DiscretizerDescriptor(Filter discretizer) {
			this.discretizer = discretizer;
		}

		public String getJavaClass() {
			return javaClass;
		}

		public void setJavaClass(String javaClass) {
			this.javaClass = javaClass;
		}

		public String getOptions() {
			return options;
		}

		public void setOptions(String options) {
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

		public double[] getDiscretizationBreakpoints() {
			double[] ret = null;
			if (this.discretizer instanceof Discretize) {
				ret = ((Discretize) this.discretizer).getCutPoints(index - 1);
			} else if (this.discretizer instanceof weka.filters.supervised.attribute.Discretize) {
				ret = ((weka.filters.supervised.attribute.Discretize) this.discretizer).getCutPoints(index - 1);
			}
			return ret;
		}

		public String toString() {
			return javaClass + "/" + options;
		}

		public void export(File file) {
			try {
				SerializationHelper.write(file.toString(), this.discretizer);
			} catch (Exception e) {
				throw new KlabIOException(e);
			}
		}

		public Range getExclude() {
			return exclude;
		}

		public void setExclude(Range exclude) {
			this.exclude = exclude;
		}

		public Range getInclude() {
			return include;
		}

		public void setInclude(Range include) {
			this.include = include;
		}
	}

	Map<String, IAnnotation> annotations = new HashMap<>();
	private boolean admitsNodata;
	private boolean errorWarning;
	private Discretization predictedDiscretization;

	// TODO set these from instances unless they're set by the archetype notation
	private double predictedMin = Double.NaN;
	private double predictedMax = Double.NaN;
	/*
	 * if this is not null, we are learning a quality that's specified as "within"
	 * some observation. We will need that context type to remove it from the
	 * predictors before it's matched and to build appropriate states when running
	 * the resource.
	 */
	private IConcept explicitContext;

	/*
	 * any attribute that has a datakey serializes its datakey here, so we can save
	 * it with the resource and reconstruct.
	 */
	private Map<String, List<String>> keys = new HashMap<>();

	// for use in the encoder. Presets the attribute and predictor arrays.
	public WekaInstances(IContextualizationScope context, int nPredictors) {
		this.context = (IRuntimeScope) context;
		this.attributes = new ArrayList<>();
		// Add null predictors and attributes so we have the same number and order as in
		// the original instance
		for (int i = 0; i < nPredictors; i++) {
			predictorStates.add(null);
			attributes.add(null);
		}
		// one more to fit the predicted state
		attributes.add(null);
	}

	/**
	 * If true is passed, nodata are left in instances as missing values. This
	 * should always be false in training unless nothing else is possible.
	 * 
	 * @param b
	 */
	public void admitNodata(boolean b) {
		this.admitsNodata = b;
	}

	/*
	 * Call ONLY first and ONLY in encoders when attributes are not defined.
	 */
	public void setPredicted(String name, IObservable observable, IState state, @Nullable Filter discretizer) {
		this.predictedState = state;
		this.predictedObservable = observable;
		this.attributes.set(0, getAttribute(observable, state));
		if (discretizer != null) {
			discretizers.put(name, new DiscretizerDescriptor(discretizer));
		}
	}

	public void setPredictedRange(Range range) {
		this.predictedMin = range.getLowerBound();
		this.predictedMax = range.getUpperBound();
	}

	/*
	 * Call ONLY after setPredicted, ONLY once per state, ONLY in the same order as
	 * the instances used for training, and ONLY in encoders when attributes are not
	 * yet defined.
	 */
	public void addPredictor(String name, IObservable predictor, IState predictorState, int index,
			@Nullable Filter discretizer) {
		if (predictorState != null) {
			this.predictorStates.set(index, predictorState);
		} else {
			this.predictorObservables.add(predictor);
		}
		this.attributes.set(index + 1, getAttribute(predictor, predictorState));
		if (discretizer != null) {
			this.discretizers.put(name, new DiscretizerDescriptor(discretizer));
		}
	}

	// for use in learning models that produce a state (i.e. the learned quality is
	// within the context)
	public WekaInstances(IState predicted, IModel model, IRuntimeScope context, boolean mustDiscretize,
			boolean admitsNodata, IServiceCall classDiscretizer, IKimExpression selector, double selectFraction) {
		this(predicted.getObservable(), model, context, mustDiscretize, admitsNodata, classDiscretizer, selector,
				selectFraction);
		this.predictedState = predicted;
	}

	// for use in learning models
	public WekaInstances(IObservable predicted, IModel model, IRuntimeScope context, boolean mustDiscretize,
			boolean admitsNodata, IServiceCall classDiscretizer, IKimExpression selector, double selectFraction) {

		this.predictedObservable = predicted;
		this.name = predicted.getName();
		this.context = context;
		this.requiresDiscretization = mustDiscretize;
		this.classDiscretizer = classDiscretizer;
		this.admitsNodata = admitsNodata;
		this.selector = selector;
		this.selectFraction = selectFraction;

		if (this.selector != null) {
			this.selectorDescriptor = Extensions.INSTANCE
					.getLanguageProcessor(selector.getLanguage() == null ? Extensions.DEFAULT_EXPRESSION_LANGUAGE
							: selector.getLanguage())
					.describe(this.selector.getCode(), context.getExpressionContext(), false);
		}

		this.explicitContext = Observables.INSTANCE.getDirectContextType(predicted.getType());
		if (this.explicitContext != null) {
			/*
			 * we learn the quality in its context so remove.
			 */
			this.predictedObservable = ObservableBuilder.getBuilder(this.predictedObservable, context.getMonitor())
					.without(ObservableRole.CONTEXT).buildObservable();
		}

		for (IObservable dependency : model.getDependencies()) {

			IAnnotation predictor = KimUtils.findAnnotation(dependency.getAnnotations(), IModel.PREDICTOR_ANNOTATION);

			if (predictor != null) {

				IConcept predictorContext = Observables.INSTANCE.getDirectContextType(predicted.getType());

				if (this.explicitContext != null && this.explicitContext.equals(predictorContext)) {

					/*
					 * the actual observable (which we will look for in the data) will be the one
					 * without context
					 */
					IObservable actual = ObservableBuilder.getBuilder(dependency, context.getMonitor())
							.without(ObservableRole.CONTEXT).buildObservable();

					/*
					 * take the predictor from the archetype directly.
					 */
					predictorObservables.add(actual);
					annotations.put(actual.getName(), predictor);
					attributeWeights.put(actual.getName(), predictor.get("weight", 1.0));

					Ranges rng = new Ranges();
					if (predictor.containsKey("include")) {
						rng.include = predictor.get("include", Range.class);
					}
					if (predictor.containsKey("exclude")) {
						rng.exclude = predictor.get("exclude", Range.class);
					}

					ranges.put(actual.getName(), rng);

				} else {

					IArtifact artifact = context.getArtifact(dependency.getName());
					if (!(artifact instanceof IState)) {
						throw new IllegalArgumentException(
								"Weka: missing predictor or not a quality: " + dependency.getName());
					}

					predictorStates.add((IState) artifact);
					annotations.put(((IState) artifact).getObservable().getName(), predictor);
					attributeWeights.put(((IState) artifact).getObservable().getName(), predictor.get("weight", 1.0));

					Ranges rng = new Ranges();
					if (predictor.containsKey("include")) {
						rng.include = predictor.get("include", Range.class);
					}
					if (predictor.containsKey("exclude")) {
						rng.exclude = predictor.get("exclude", Range.class);
					}

					ranges.put(((IObservation) artifact).getObservable().getName(), rng);

				}

			} else {

				IAnnotation arch = KimUtils.findAnnotation(dependency.getAnnotations(), IModel.ARCHETYPE_ANNOTATION);

				if (arch != null) {

					IArtifact artifact = context.getArtifact(dependency.getName());

					if (artifact instanceof IState) {
						if (this.distributedArchetype != null) {
							throw new IllegalArgumentException(
									"Weka: only one archetype is admitted if a distributed state is used");
						}
						this.distributedArchetype = (IState) artifact;
						if (this.distributedArchetype.getObservable().getArtifactType() != predicted
								.getArtifactType()) {
							throw new IllegalStateException(
									"Weka: cannot use " + this.distributedArchetype.getObservable().getType()
											+ " as an archetype for " + predicted.getType() + ": incompatible types");
						}

						if (Double.isNaN(this.selectFraction)) {
							if (selector == null) {
								context.getMonitor().info(
										"Selecting default 5% of states for distributed state without explicit selector");
								this.selectFraction = .05;
							} else {
								this.selectFraction = 1;
							}
						}

					} else if (!(artifact instanceof ObservationGroup)) {
						throw new IllegalArgumentException("Weka: missing archetype or archetype is not countable");
					} else {
						this.archetypes.add((ObservationGroup) artifact);
					}

					if (arch.containsKey("min")) {
						this.predictedMin = arch.get("min", Double.NaN);
					}
					if (arch.containsKey("max")) {
						this.predictedMax = arch.get("max", Double.NaN);
					}
					if (arch.containsKey("weight")) {
						this.weightObservable = arch.get("weight", IConcept.class);
						attributeWeights.put(((ObservationGroup) artifact).getObservable().getName(), 1.0);
					}

					Ranges rng = new Ranges();
					if (arch.containsKey("include")) {
						rng.include = arch.get("include", Range.class);
					}
					if (arch.containsKey("exclude")) {
						rng.exclude = arch.get("exclude", Range.class);
					}

					// the artifact range is for the predicted variable, not for the archetype
					// observation
					ranges.put(predictedObservable.getName(), rng);
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
	 * first as per WEKA conventions. The same order will be saved in the resource
	 * dependencies.
	 * 
	 * @return
	 */
	public ArrayList<Attribute> getAttributes() {

		if (this.attributes == null) {
			this.attributes = new ArrayList<>();
			this.attributes.add(getAttribute(predictedObservable, predictedState));
			for (IObservable var : getPredictorObservables()) {
				this.attributes.add(getAttribute(var, getPredictorState(var)));
			}
		}
		return this.attributes;
	}

	private IState getPredictorState(IObservable var) {
		for (IState state : predictorStates) {
			if (var.getName().equals(state.getObservable().getName())) {
				return state;
			}
		}
		return null;
	}

	public int size() {
		return getInstances().size();
	}

	public DiscretizerDescriptor getDiscretization(String attribute) {
		return discretizers.get(attribute);
	}

	/**
	 * May be null if learning a contextual quality over a set of objects.
	 * 
	 * @return
	 */
	public IState getPredictedState() {
		return predictedState;
	}

	/**
	 * May return null if predictors are not states in the context but in
	 * instantiated objects.
	 * 
	 * @param attributeName
	 * @return
	 */
	public IState getPredictor(String attributeName) {
		for (IState state : predictorStates) {
			if (state.getObservable().getName().equals(attributeName)) {
				return state;
			}
		}
		return null;
	}

	/**
	 * Should never return null unless in error.
	 * 
	 * @param attributeName
	 * @return
	 */
	public IObservable getPredictorObservable(String attributeName) {
		for (IObservable state : getPredictorObservables()) {
			if (state.getName().equals(attributeName)) {
				return state;
			}
		}
		return null;
	}

	private Attribute getAttribute(IObservable observable, IState state) {

		Attribute ret = null;
		switch (observable.getArtifactType()) {
		case NUMBER:

			ret = new Attribute(observable.getName());
			break;

		case CONCEPT:

			List<String> labels = null;
			if (state == null && dataKeys.containsKey(observable.getName())) {
				labels = dataKeys.get(observable.getName()).getLabels();
			} else if (state == null) {
				labels = Types.INSTANCE.createClassification(observable).getLabels();
			} else {
				labels = new ArrayList<>(state.getDataKey().getLabels());
				keys.put(observable.getName(), state.getDataKey().getSerializedObjects());
			}
			ret = new Attribute(observable.getName(), labels);

			break;
		case BOOLEAN:

			ret = new Attribute(observable.getName(), Lists.newArrayList("false", "true"));
			break;

		default:
			// shouldn't happen.
			throw new IllegalStateException("WEKA learning process: occurrence state " + observable
					+ " is not numeric, categorical or boolean");
		}

		// attribute weight from predictor annotation. The archetype has always 1.
		Double weight = attributeWeights.get(observable.getName());
		ret.setWeight(weight == null ? 1.0 : weight);

		return ret;
	}

	private List<IObservable> getPredictorObservables() {

		if (this.predictors == null) {

			this.predictors = new ArrayList<>();

			if (predictorObservables.size() > 0) {
				for (IObservable predictor : predictorObservables) {
					/*
					 * remove the inherency before storing
					 */
					this.predictors.add(ObservableBuilder.getBuilder(predictor, context.getMonitor())
							.without(ObservableRole.CONTEXT).buildObservable());
				}
			} else {
				for (IState state : predictorStates) {
					this.predictors.add(state.getObservable());
				}
			}
		}

		return this.predictors;
	}

	private void build() {

		/*
		 * Use the archetype's datakey for the predicted state if we have a distributed
		 * archetype, to ensure we have the same mappings.
		 */
		if (distributedArchetype != null && predictedState != null && distributedArchetype.getDataKey() != null) {
			((State) predictedState).setDataKey(distributedArchetype.getDataKey());
		}

		if (this.archetypes.isEmpty() && distributedArchetype == null) {
			throw new IllegalStateException("Weka: cannot build training set without at least one archetype");
		}

		// this ensures we can just use this.predictors afterwards.
		if (getPredictorObservables().size() < 1) {
			throw new IllegalStateException("Weka: not enough predictors to build a training set");
		}

		this.rawInstances = new Instances(name + "_instances", getAttributes(), 0);

		Map<String, Integer> stateIndex = null;

		int skipped = 0;

		// for later reporting
		Set<String> pnames = new HashSet<>();
		pnames.add(predictedObservable.getName());
		for (IObservable predictor : predictors) {
			pnames.add(predictor.getName());
		}

		/*
		 * support a distributed archetype using sample and select
		 */
		if (distributedArchetype != null && !Double.isNaN(this.selectFraction)) {

			ILanguageExpression expression = null;
			List<IState> sourceStates = new ArrayList<>();
			Map<IState, String> stateIdentifiers = new HashMap<>();

			if (selector != null && selectorDescriptor != null) {
				// check inputs and see if the expr is worth anything in this context
				for (String input : selectorDescriptor.getIdentifiers()) {
					if (selectorDescriptor.isScalar(input) && context.getArtifact(input, IState.class) != null) {
						IState state = context.getArtifact(input, IState.class);
						sourceStates.add(state);
						stateIdentifiers.put(state, input);
					}
				}
				// if (sourceStates.isEmpty()) {
				// throw new KlabResourceNotFoundException(
				// "feature extractor: the selection expression does not reference any known
				// state");
				// }
				expression = selectorDescriptor.compile();
			}

			if (this.selectFraction > 0) {

				Parameters<String> parameters = new Parameters<>();
				boolean warned = false;
				for (ILocator locator : distributedArchetype.getScale()) {

					boolean sample = true;
					if (this.selectFraction < 1) {
						sample = Math.random() >= this.selectFraction;
					}

					Object[] instanceValues = new Object[predictors.size() + 1];
					double instanceWeight = 1;

					if (sample) {

						if (expression != null) {

							parameters.clear();
							for (IState state : sourceStates) {
								Object o = state.get(locator, Object.class);
								parameters.put(stateIdentifiers.get(state), o);
							}

							Object o = expression.eval(parameters, context);
							if (o == null) {
								o = Boolean.FALSE;
							}
							if (!(o instanceof Boolean)) {
								throw new KlabValidationException(
										"distributed training: selector must return true/false");
							}

							sample = (Boolean) o;

						} else if (Double.isNaN(this.selectFraction) && !warned) {
							context.getMonitor()
									.warn("point extractor: no input: specify either select or select fraction");
							warned = true;
						}

					}

					if (/* still */ sample) {

						Object value = distributedArchetype.get(locator);
						if (!Observations.INSTANCE.isNodata(value)) {

							// TODO ranges!

							instanceValues[0] = value;
							int i = 1;
							for (IState predictor : predictorStates) {
								value = predictor.get(locator);

								// TODO ranges!

								if (Observations.INSTANCE.isNodata(value)) {
									sample = false;
								}
								instanceValues[i++] = value;
							}

						} else {
							sample = false;
						}

						if (/* still */ sample) {
							double[] values = mapValuesToDoubles(instanceValues);
							if (values != null) {
								rawInstances.add(new DenseInstance(instanceWeight, values));
							} else {
								skipped++;
							}
						} else {
							skipped++;
						}
					}
				}
			}
		}

		for (ObservationGroup archetype : archetypes) {

			/*
			 * remove the inherency to check for the predicted state in case we are training
			 * "within" this particular object type.
			 */
			IObservable archetypeObservable = predictedObservable;
			IConcept inherency = Observables.INSTANCE.getDirectInherentType(predictedObservable.getType());
			if (inherency != null && archetype.getObservable().is(inherency)) {
				archetypeObservable = archetypeObservable.getBuilder(context.getMonitor())
						.without(ObservableRole.INHERENT).buildObservable();
			}

			for (IArtifact object : archetype) {

				// this is for reporting what's missing - bit of a pain
				Set<String> missing = new HashSet<>(pnames);

				Object[] instanceValues = new Object[predictors.size() + 1];
				double instanceWeight = 1;

				if (stateIndex == null) {

					// build map of predicted/observable name to index in instance. We use one
					// artifact so the observable names are stable.
					stateIndex = new HashMap<>();

					for (IState state : ((IDirectObservation) object).getStates()) {

						if (state.getObservable().equals(archetypeObservable)) {
							stateIndex.put(predictedObservable.getName(), 0);
							missing.remove(predictedObservable.getName());
						} else {
							int i = 1;
							for (IObservable predictor : predictors) {
								if (state.getObservable().equals(predictor)) {
									stateIndex.put(predictor.getName(), i);
									missing.remove(predictor.getName());
								} else if (weightObservable != null && state.getObservable().is(weightObservable)) {
									instanceWeight = state.aggregate(((IObservation) object).getScale(), Double.class);
									missing.remove(predictor.getName());
								}
								i++;
							}
						}
					}

					if (stateIndex.size() != (predictors.size() + 1)) {
						throw new IllegalStateException(
								"Weka: the archetype observations do not contain values for the learned quality and all predictors: missing "
										+ Arrays.toString(missing.toArray()));
					}
				}

				// TODO set min/max from instances if any is NaN

				boolean ignore = false;
				for (IState state : ((IDirectObservation) object).getStates()) {

					/*
					 * find the known name for this state
					 */
					String name = getObservableName(state, archetypeObservable);

					if (name != null) {
						Object o = state.aggregate(((IObservation) object).getScale(),
								Utils.getClassForType(state.getObservable().getArtifactType()));
						if (Observations.INSTANCE.isNodata(o)) {
							skipped++;
							ignore = true;
							break;
						} else {

							if (o instanceof Number) {
								Ranges rng = ranges.get(name);
								if (rng.include != null && !rng.include.contains(((Number) o).doubleValue())) {
									ignore = true;
									break;
								}
								if (rng.exclude != null && rng.exclude.contains(((Number) o).doubleValue())) {
									ignore = true;
									break;
								}
							}

							instanceValues[stateIndex.get(name)] = o;
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

		context.getMonitor().info("Weka: training set generated with " + rawInstances.size() + " instances (" + skipped
				+ " skipped because of no-data)");

		// go through discretization for each attribute, choose scheme if
		// discretization is mandatory and attribute is numeric
		this.instances = rawInstances;

		/*
		 * discretize the class attribute if requested or required
		 */
		if (requiresDiscretization || this.classDiscretizer != null) {
			try {
				this.instances = Filter.useFilter(this.instances,
						buildDiscretization(classDiscretizer, predictedObservable, 1));
			} catch (Exception e) {
				throw new IllegalStateException(
						"Weka: error during discretization of class attribute: " + e.getMessage());
			}
		}

		int i = 2;
		for (IObservable predictor : predictors) {

			IAnnotation annotation = annotations.get(predictor.getName());
			if (predictor.getArtifactType() == Type.NUMBER) {
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
					throw new IllegalStateException(
							"Weka: error during discretization of " + predictor.getName() + ": " + e.getMessage());
				}
			} else if (annotation.containsKey("discretization")) {
				throw new IllegalArgumentException(
						"Weka: " + predictor.getName() + ": cannot specify discretization for non-numeric predictors");
			}
			i++;
		}

		try {
			missingValuesFilter.setInputFormat(instances);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}

	}

	/**
	 * Return the known name linked to a state's observable in the WEKA model.
	 * 
	 * @param state
	 * @return
	 */
	private String getObservableName(IState state, IObservable archetypeObservable) {

		if (state.getObservable().equals(predictedObservable)
				|| (archetypeObservable != null && state.getObservable().equals(archetypeObservable))) {
			return predictedObservable.getName();
		}
		for (IObservable observable : getPredictorObservables()) {
			if (observable.equals(state.getObservable())) {
				return observable.getName();
			}
		}

		return null;
	}

	private Filter buildDiscretization(IServiceCall specification, IObservable predictor, int fieldIndex) {

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

		DiscretizerDescriptor descriptor = new DiscretizerDescriptor(filterClass.getCanonicalName(), options,
				fieldIndex);
		try {
			descriptor.getDiscretizer().setInputFormat(this.instances);
			discretizers.put(predictor.getName(), descriptor);
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
				IState state = i == 0 ? predictedState : predictorStates.get(i - 1);
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
		if (predictedObservable.getArtifactType() == Type.NUMBER && !Double.isNaN(ret[0])) {
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

	public Instance getInstance(IParameters<String> data) {

		Instance ret = new DenseInstance(attributes.size());
		ret.setDataset(getInstances());
		ret.setClassMissing();

		int ndata = 0;
		for (int i = 1; i < attributes.size(); i++) {

			Attribute attribute = attributes.get(i);

			Object o = data.get(attribute.name());
			if (Observations.INSTANCE.isData(o)) {

				double value = Double.NaN;

				if (attribute.type() == Attribute.NUMERIC) {
					value = o instanceof Number ? ((Number) o).doubleValue()
							: (o instanceof Boolean ? (((Boolean) o) ? 1 : 0) : Double.NaN);
				} else if (o instanceof IConcept) {
					if (dataKeys.containsKey(attribute.name())) {
						value = dataKeys.get(attribute.name()).reverseLookup(o);
					} else {
						throw new KlabInternalErrorException(
								"Weka: internal: cannot interpret value " + o + ": no datakey");
					}
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
		}

		if (ndata < (attributes.size() - 1 - MAX_ALLOWED_NODATA)) {
			return null;
		}

		// go through the discretizers
		for (String obs : discretizers.keySet()) {
			if (!obs.equals(predictedObservable.getName())) {
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

		/*
		 * go through the missing value filter
		 */
		if (missingValuesFilter != null && missingValuesFilter.input(ret)) {
			ret = missingValuesFilter.output();
		}

		return ret;
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

		Instance ret = new DenseInstance(predictorStates.size() + 1);
		ret.setDataset(getInstances());
		ret.setClassMissing();

		int i = 1;
		int ndata = 0;
		for (IState predictor : predictorStates) {

			/*
			 * This only happens with trained resources
			 */
			if (predictor == null) {
				ret.setMissing(i);
				continue;
			}

			Object o = predictor.get(locator);
			if (Observations.INSTANCE.isData(o)) {

				double value = Double.NaN;

				// DiscretizerDescriptor filter =
				// discretizers.get(predictor.getObservable().getLocalName());
				// Filter discretizer = null;
				// if (filter != null) {
				// discretizer = filter.getDiscretizer();
				// }

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

		if (ndata < (predictorStates.size() - MAX_ALLOWED_NODATA)) {
			return null;
		}

		// go through the discretizers
		for (String obs : discretizers.keySet()) {
			if (!obs.equals(predictedObservable.getName())) {
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

		/*
		 * go through the missing value filter
		 */
		if (missingValuesFilter != null && missingValuesFilter.input(ret)) {
			ret = missingValuesFilter.output();
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
			DiscretizerDescriptor filter = discretizers.get(predictedState.getObservable().getName());
			if (filter == null) {
				throw new IllegalStateException(
						"Weka: cannot interpret a distribution if the predicted variable is not discretized.");
			}
			// no API for getCutPoints() - what a pain
			double[] cutpoints = null;
			if (filter.getDiscretizer() instanceof Discretize) {
				cutpoints = ((Discretize) filter.getDiscretizer()).getCutPoints(0);
			} else if (filter.getDiscretizer() instanceof weka.filters.supervised.attribute.Discretize) {
				cutpoints = ((weka.filters.supervised.attribute.Discretize) filter.getDiscretizer()).getCutPoints(0);
			} else {
				throw new KlabUnimplementedException("Weka: cannot get cut points from discretizer of class "
						+ filter.getDiscretizer().getClass().getCanonicalName() + ": please report to developers");
			}
			this.predictedDiscretization = new Discretization(predictedMin, cutpoints, predictedMax);

		}
		return this.predictedDiscretization;
	}

	public void initializeForPrediction(File file) {

		try {

			BufferedReader reader = new BufferedReader(new FileReader(file));
			ArffReader arff = new ArffReader(reader);
			this.instances = arff.getData();
			this.instances.setClassIndex(0);
			this.missingValuesFilter.setInputFormat(this.instances);
			this.missingValuesFilter.batchFinished();

		} catch (Exception e) {
			throw new KlabIOException(e);
		}
	}

	/**
	 * True if the state we are predicting is in the context
	 * 
	 * @return
	 */
	public boolean predictedIsDistributed() {
		return predictorStates.size() > 0;
	}

	/**
	 * Never null unless in error.
	 * 
	 * @return
	 */
	public IObservable getPredictedObservable() {
		return predictedObservable;
	}

	public List<String> getDatakeyDefinitions(String name) {
		return keys.get(name);
	}

	public void setDatakey(String id, List<String> key) {

		List<IConcept> concepts = new ArrayList<>();
		for (String definition : key) {
			IObservable o = Observables.INSTANCE.declare(definition);
			if (o == null) {
				throw new KlabValidationException("resource is using obsolete or unknown concepts: " + definition);
			}
			concepts.add(o.getType());
		}

		IObservable root = null;
		if (id.equals("predicted")) {
			root = predictedObservable;
		} else {
			root = getPredictorObservable(id);
		}

		if (root == null) {
			throw new KlabValidationException("resource cannot establish observable for '" + id + "'");
		}

		this.dataKeys.put(id, new Classification(root.getType(), concepts));
	}

	public IDataKey getDatakey(String name) {
		return dataKeys.get(name);
	}

	public void setPredictedObservable(IObservable obs) {
		this.predictedObservable = obs;
	}

}
