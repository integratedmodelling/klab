//package org.integratedmodelling.ml.contextualizers;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//import java.util.Set;
//
//import org.apache.commons.math3.distribution.EnumeratedRealDistribution;
//import org.integratedmodelling.kim.api.IKimConcept.Type;
//import org.integratedmodelling.klab.api.data.ILocator;
//import org.integratedmodelling.klab.api.knowledge.IConcept;
//import org.integratedmodelling.klab.api.observations.IDirectObservation;
//import org.integratedmodelling.klab.api.observations.IState;
//import org.integratedmodelling.klab.api.provenance.IArtifact;
//import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
//import org.integratedmodelling.klab.exceptions.KlabContextualizationException;
//import org.integratedmodelling.klab.exceptions.KlabException;
//import org.integratedmodelling.klab.exceptions.KlabIOException;
//import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
//import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
//import org.integratedmodelling.klab.exceptions.KlabValidationException;
//import org.integratedmodelling.klab.utils.MapUtils;
//import org.integratedmodelling.klab.utils.MiscUtilities;
//import org.integratedmodelling.klab.utils.Pair;
//
//import weka.classifiers.Classifier;
//import weka.classifiers.Evaluation;
//import weka.core.Attribute;
//import weka.core.DenseInstance;
//import weka.core.Instance;
//import weka.core.Instances;
//import weka.core.OptionHandler;
//import weka.core.converters.ArffSaver;
//import weka.filters.Filter;
//import weka.filters.unsupervised.attribute.Discretize;
//
///**
// * Wrapper for a WEKA classifier and instance set to produce, update and manage
// * WEKA classifiers and ARFF datasets from context information.
// * 
// * @author Ferd
// */
//public class WEKAResolverLegacy {
//
//	public class Var {
//
//		IState state;
//		Attribute attribute;
//		Map<String, Integer> legend;
//
//		public double getAttributeValue(Object inst) {
//
//			if (inst instanceof IConcept) {
//				inst = inst.toString();
//			} else if (inst instanceof Boolean) {
//				inst = ((Boolean) inst) ? "true" : "false";
//			}
//			if (legend != null) {
//				return legend.get(inst);
//			}
//			return inst instanceof Number ? ((Number) inst).doubleValue() : 0;
//		}
//	}
//
//	public static final String DISCRETIZER_SUFFIX = ".discretizer.bin";
//
//	Var predicted = null;
//	List<Var> predictors = new ArrayList<>();
//	Set<IDirectObservation> archetypes = new HashSet<>();
//	IState distributedArchetype = null;
//	Instances instances = null;
//	IMonitor monitor;
//	IState outputState = null;
//	boolean distributedArchetypeChanged = false;
//	protected Classifier classifier;
//	IConcept learnedQuality = null;
//	String name;
//	// options set from API
//	List<String> options = new ArrayList<>();
//
//	/**
//	 * if false, numeric predictors are automatically discretized
//	 */
//	boolean allowNumeric = true;
//	boolean acceptNoData = false;
//	boolean crossValidate = true;
//	boolean skipTraining = false;
//
//	/**
//	 * Number of bins for automatic discretization. Default is the WEKA default of
//	 * 10.
//	 */
//	int nDiscretizationBins = 10;
//
//	/**
//	 * if false, numeric zero values are ignored in training
//	 */
//	boolean ignoreZeroes = true;
//
//	/**
//	 * if true and archetypes are direct observations, one value per archetype is
//	 * generated, aggregating the predictors over its scale.
//	 */
//	boolean aggregateArchetype = true;
//	private IDirectObservation context;
//
//	Map<String, IState> states;
//	protected int MIN_INSTANCES_FOR_TRAINING = 5;
//	private boolean forceStaticOutput = false;
//	private double PRESENCE_PROBABILITY_THRESHOLD = 0.9;
//	private boolean optionsSet;
//	protected Discretize discretizer = null;
//
//	/*
//	 * record min/max to undiscretize
//	 */
//	double min = Double.NaN;
//	double max = Double.NaN;
//
//	public WEKAResolverLegacy(IMonitor monitor) {
//		this.monitor = monitor;
//	}
//
//	public List<Var> getPredictors() {
//		return predictors;
//	}
//
//	public void addWekaOptions(String... options) {
//		if (options != null) {
//			for (String o : options) {
//				this.options.add(o);
//			}
//		}
//	}
//
//	public Discretize getDiscretizer() {
//		return discretizer;
//	}
//
//	public Classifier getClassifier() {
//		return classifier;
//	}
//
//	/**
//	 * Call to force the output to be static.
//	 */
//	public void forceStaticOutput() {
//		forceStaticOutput = true;
//	}
//
//	/**
//	 * Set the cross-validation flag (default true).
//	 * 
//	 * @param b
//	 */
//	public void setCrossValidation(boolean b) {
//		crossValidate = b;
//	}
//
//	/**
//	 * Set whether instances need to be discretized before training.
//	 * 
//	 * @param b
//	 */
//	public void setNumericInputAllowed(boolean b) {
//		this.allowNumeric = b;
//	}
//
//	/**
//	 * Change to define how many bins are wanted when discretization is automatic.
//	 * Default is 10.
//	 * 
//	 * @param n
//	 */
//	public void setDiscretizationBinCount(int n) {
//		this.nDiscretizationBins = n;
//	}
//
//	/**
//	 * Set the minimum number of instances required for successful training. Default
//	 * is 5.
//	 * 
//	 * @param n
//	 */
//	public void setMinimumInstanceCount(int n) {
//		this.MIN_INSTANCES_FOR_TRAINING = n;
//	}
//
//	/**
//	 * Set the minimum probability of the 'true' case for a presence to be accepted.
//	 * Default is 0.9.
//	 * 
//	 * @param p
//	 */
//	public void setPresenceProbabilityThreshold(double p) {
//		this.PRESENCE_PROBABILITY_THRESHOLD = p;
//	}
//
//	public void load(File classifier, File discretizer) throws KlabException {
//
//		this.skipTraining = true;
//
//		if (discretizer != null) {
//			try (FileInputStream fis = new FileInputStream(discretizer);
//					ObjectInputStream ois = new ObjectInputStream(fis)) {
//				this.discretizer = (Discretize) ois.readObject();
//			} catch (Throwable e) {
//				throw new KlabIOException(e);
//			}
//		}
////		this.classifier = this.contextualizer.loadClassifier(classifier);
//
//		File pfile = MiscUtilities.getSidecarFile(classifier, ".properties");
//		if (pfile.exists()) {
//			try (FileInputStream inp = new FileInputStream(pfile)) {
//				Properties prop = new Properties();
//				prop.load(inp);
//				if (prop.containsKey("trained.min")) {
//					this.min = Double.parseDouble(prop.getProperty("trained.min"));
//				}
//				if (prop.containsKey("trained.max")) {
//					this.max = Double.parseDouble(prop.getProperty("trained.max"));
//				}
//			} catch (Exception e) {
//				throw new KlabIOException(e);
//			}
//		}
//
//		setClassifierOptions();
//	}
//
//	/**
//	 * Produce an instance set for training according to the learning roles in the
//	 * inputs. Archetypes are found either in the input data (presence) or in the
//	 * subjects in the context.
//	 * 
//	 * @param learningProcess
//	 *            the learning process being computed
//	 * @param context
//	 *            the context of the process
//	 * @param resolutionScope
//	 *            resolution scope (may be null)
//	 * @param inputs
//	 *            all input observables, either with role "explanatory variable" or
//	 *            "archetype". Must correspond to existing states in context.
//	 * @param outputs
//	 *            all input observables, in which the "learned variable" role will
//	 *            be looked up.
//	 * @param allowNumeric
//	 *            if false, any numeric input is automatically discretized
//	 * @param monitor
//	 *            monitor for communication
//	 * @return
//	 * @throws KlabException
//	 */
////	public void initialize(IActiveDirectObservation context, Map<String, IObservable> inputs,
////			Map<String, IObservable> outputs) throws KlabException {
////
////		this.states = States.matchStatesToInputs(context, inputs);
////
////		this.learningProcess = learningProcess;
////		this.context = context;
////		this.resolutionScope = resolutionScope;
////		this.learnedQuality = learningProcess.getModel().getObservables().get(1).getType();
////
////		for (String out : outputs.keySet()) {
////			if (learningProcess.getRolesFor(outputs.get(out)).contains(NS.LEARNED_QUALITY_ROLE)
////					|| outputs.get(out).getType().equals(this.learnedQuality)) {
////				/*
////				 * make state, set into outputState, set up observer
////				 */
////				if (this.outputState != null) {
////					throw new KlabValidationException("only one quality can be learned in a learning process");
////				}
////
////				if (outputs.get(out).getArtifactType() == IArtifact.Type.NUMBER && !allowNumeric) {
////					this.outputState = forceStaticOutput
////							? context.getStaticState(((ObservableSemantics) outputs.get(out)).forceProbabilistic())
////							: context.getState(((ObservableSemantics) outputs.get(out)).forceProbabilistic());
////				} else {
////					this.outputState = forceStaticOutput ? context.getStaticState(outputs.get(out))
////							: context.getState(outputs.get(out));
////				}
////				this.outputState.getMetadata().put(IMetadata.DC_LABEL, out);
////			}
////		}
////
////		/*
////		 * find strategy to establish archetypes. If an input is tagged as archetype, do
////		 * not look for subjects or events. Can be run multiple times; the distributed
////		 * states are only scanned once.
////		 */
////		for (String inp : states.keySet()) {
////			IState o = states.get(inp);
////			if (learningProcess.getRolesFor(o).contains(NS.ARCHETYPE_ROLE)) {
////
////				predicted = getPredictor(o);
////
////				distributedArchetype = o;
////				distributedArchetypeChanged = true;
////				distributedArchetype.addChangeListener(new ChangeListener() {
////
////					@Override
////					public void transitionDone(ITransition transaction) {
////					}
////
////					@Override
////					public void changed(int offset, Object value) {
////						distributedArchetypeChanged = true;
////					}
////				});
////			} else if (learningProcess.getRolesFor(o).contains(NS.EXPLANATORY_QUALITY_ROLE)) {
////				Var predictor = getPredictor(o);
////				if (predictor != null) {
////					predictors.add(predictor);
////				}
////			}
////		}
////
////		if (predicted == null) {
////			// happens when the archetype is an object
////			predicted = getPredictor(outputState);
////		}
////	}
//
//	public IState getOutputState() {
//		return outputState;
//	}
//
//	private ArrayList<Attribute> getAttributes() {
//		ArrayList<Attribute> ret = new ArrayList<>();
//		if (predicted != null) {
//			ret.add(predicted.attribute);
//		}
//		for (Var var : predictors) {
//			ret.add(var.attribute);
//		}
//		return ret;
//	}
//
//	private void setClassifierOptions() throws KlabInternalErrorException {
//		/*
//		 * add any options to classifier if not done already
//		 */
//		if (!optionsSet && this.classifier instanceof OptionHandler && options.size() > 0) {
//
//			try {
//				((OptionHandler) this.classifier).setOptions(options.toArray(new String[options.size()]));
//			} catch (Exception e) {
//				throw new KlabInternalErrorException(e);
//			}
//
//			optionsSet = true;
//		}
//
//	}
//
//	/*
//	 * record the match between the passed value of the archetype and all the
//	 * others.
//	 */
//	private void recordInstanceValue(Object inst, ILocator n) {
//
//		Object instanceValue = null;
//
//		if (predicted.state.getObservable().is(Type.PRESENCE) || predicted.state.getObservable().is(Type.PROBABILITY)) {
//			if (inst instanceof Boolean && ((Boolean) inst)) {
//				instanceValue = "true";
//			}
//		} else if (predicted.state.getObservable().getArtifactType() == IArtifact.Type.CONCEPT) {
//			instanceValue = ((IConcept) inst).toString();
//		} else if (predicted.state.getObservable().getArtifactType() == IArtifact.Type.NUMBER) {
//			instanceValue = ((Number) inst).doubleValue();
//		}
//
//		if (instanceValue != null) {
//
//			double[] values = new double[predictors.size() + 1];
//
//			values[0] = predicted.getAttributeValue(inst);
//
//			if (!Double.isNaN(values[0])) {
//				if (Double.isNaN(this.min) || this.min > values[0]) {
//					this.min = values[0];
//				}
//				if (Double.isNaN(this.max) || this.max < values[0]) {
//					this.max = values[0];
//				}
//			}
//
//			/*
//			 * add other predictors; if all values are not nodata or nodata are allowed and
//			 * at least one predictor is not nodata, add instance
//			 */
//			int nodata = 0;
//			for (int i = 0; i < predictors.size(); i++) {
//
//				Object value = predictors.get(i).state.get(n);
//				if (value == null || (value instanceof Number && Double.isNaN(((Number) value).doubleValue()))) {
//					nodata++;
//					values[i + 1] = Double.NaN;
//				} else {
//					values[i + 1] = predictors.get(i).getAttributeValue(value);
//				}
//			}
//
//			if (nodata == 0 || (acceptNoData && values.length > (nodata + 1))) {
//				getInstances().add(new DenseInstance(1.0, values));
//			}
//
//		}
//
//	}
//
//	/*
//	 * record values of explained variables at the covered extent of the archetype
//	 * observation.
//	 */
//	private void createInstance(IDirectObservation o) {
//
////		IState state = ((DirectObservation) o).getExistingState(learnedQuality);
////		if (state == null) {
////			return;
////		}
//
//		if (this.aggregateArchetype) {
//
//			// aggregated value of object
////			Object pvalue = States.aggregate(state, ITime.INITIALIZATION);
////
////			double[] values = new double[predictors.size() + 1];
////			values[0] = predicted.getAttributeValue(pvalue);
////
////			if (!Double.isNaN(values[0])) {
////				if (Double.isNaN(this.min) || this.min > values[0]) {
////					this.min = values[0];
////				}
////				if (Double.isNaN(this.max) || this.max < values[0]) {
////					this.max = values[0];
////				}
////			}
////
////			/*
////			 * add other predictors; if all values are not nodata or nodata are allowed and
////			 * at least one predictor is not nodata, add instance
////			 */
////			int nodata = 0;
////			for (int i = 0; i < predictors.size(); i++) {
////
////				IState view = States.getView(predictors.get(i).state, o);
////				Object value = States.aggregate(view, ITime.INITIALIZATION);
////
////				if (value == null || (value instanceof Number && Double.isNaN(((Number) value).doubleValue()))) {
////					nodata++;
////					values[i + 1] = Double.NaN;
////				} else {
////					values[i + 1] = predictors.get(i).getAttributeValue(value);
////				}
////			}
////
////			if (nodata == 0 || (acceptNoData && values.length > (nodata + 1))) {
////				getInstances().add(new DenseInstance(1.0, values));
////			}
//
//		} else {
//			throw new KlabUnsupportedFeatureException(
//					"disaggregated archetypes are still not supported in WEKA contextualizer.");
//		}
//
//	}
//
//	private Var getPredictor(IState o) throws KlabValidationException {
//
//		Var ret = null;
//		ArrayList<String> nominalValues = null;
//		switch (o.getObservable().getArtifactType()) {
//		case NUMBER:
//
//			ret = new Var();
//			ret.state = o;
//			ret.attribute = new Attribute(sanitizeName(o.getObservable().getName()));
//			break;
//
//		case CONCEPT:
//
//			ret = new Var();
//			ret.state = o;
//			nominalValues = new ArrayList<>();
////			for (IConcept c : o.getDataKey().getConceptOrder()) {
////				nominalValues.add(c.toString());
////			}
//			ret.attribute = new Attribute(sanitizeName(o.getObservable().getName()), nominalValues);
//			ret.legend = new HashMap<>();
//			for (int i = 0; i < nominalValues.size(); i++) {
//				ret.legend.put(nominalValues.get(i), i);
//			}
//			break;
//
//		case BOOLEAN:
//			ret = new Var();
//			ret.state = o;
//			nominalValues = new ArrayList<>();
//			nominalValues.add("true");
//			nominalValues.add("false");
//			ret.attribute = new Attribute(sanitizeName(o.getObservable().getName()), nominalValues);
//			ret.legend = new HashMap<>();
//			for (int i = 0; i < nominalValues.size(); i++) {
//				ret.legend.put(nominalValues.get(i), i);
//			}
//			break;
//
//		default:
//	// shouldn't happen.
//			throw new KlabValidationException(
//					"WEKA learning process: occurrence state " + o + " is not numeric, categorical or boolean");
//		}
//		return ret;
//	}
//
//	/**
//	 * Save the training set to a file. Call after {@link #train(ITransition)}
//	 * obviously.
//	 * 
//	 * @param file
//	 * @throws KlabException
//	 */
//	public void saveData(File file) throws KlabException {
//		saveData(file, getInstances());
//	}
//
//	public void saveData(File file, Instances instances) throws KlabException {
//		ArffSaver saver = new ArffSaver();
//		saver.setInstances(instances);
//		try {
//			saver.setFile(file);
//			// saver.setDestination(file);
//			saver.writeBatch();
//		} catch (Exception e) {
//			throw new KlabIOException(e);
//		}
//	}
//
//	/**
//	 * Build the instance set and train the model.
//	 * 
//	 * @throws KlabException
//	 */
//	void train(ILocator transition) throws KlabException {
//
//		monitor.info("training started");
//
//		Set<IDirectObservation> newArchetypes = new HashSet<>();
//
//		Instances instances = getInstances();
//
//		if (distributedArchetype == null) {
//			/*
//			 * Find yet-unknown archetypes in subject TODO define the predicted attribute
//			 * before creating instances
//			 */
//			// for (ISubject s : ((ISubject) context).getSubjects()) {
//			// if (learningProcess.getRolesFor(s).contains(NS.ARCHETYPE_ROLE)
//			// && !archetypes.contains(s)) {
//			// archetypes.add(s);
//			// newArchetypes.add(s);
//			// }
//			// }
//			// for (IEvent s : ((ISubject) context).getEvents()) {
//			// if (learningProcess.getRolesFor(s).contains(NS.ARCHETYPE_ROLE)
//			// && !archetypes.contains(s)) {
//			// archetypes.add(s);
//			// newArchetypes.add(s);
//			// }
//			// }
//		}
//
//		/*
//		 * follow archetypes and build attribute set.
//		 */
//		if (distributedArchetype != null) {
//
//			/*
//			 * must run only if the distributed archetype is new or has changed TODO should
//			 * keep a boolean cache to understand changed values for updateable classifiers.
//			 */
//			if (distributedArchetypeChanged) {
//				for (ILocator n : context.getScale()) {
//
//					Object inst = distributedArchetype.get(n);
//					if (ignoreZeroes && inst instanceof Number && ((Number) inst).doubleValue() == 0.0) {
//						continue;
//					}
//					if (!(inst == null || (inst instanceof Number && Double.isNaN(((Number) inst).doubleValue())))) {
//						recordInstanceValue(inst, n);
//					}
//				}
//			}
//
//		} else if (newArchetypes.size() > 0) {
//			for (IDirectObservation o : newArchetypes) {
//				createInstance(o);
//			}
//		}
//
//		if (getInstances().size() < MIN_INSTANCES_FOR_TRAINING) {
//			throw new KlabValidationException("not enough instances for training (" + getInstances().size() + ")");
//		}
//
//		if (!allowNumeric) {
//			createDiscretizer();
//		}
//
//		monitor.info("generated training set with " + getInstances().size() + " instances");
//
//		getInstances().setClassIndex(0);
////		this.classifier = this.contextualizer.createNewClassifier(getInstances());
//
//		setClassifierOptions();
//
//		try {
//			classifier.buildClassifier(getInstances());
//		} catch (Exception e) {
//			throw new KlabContextualizationException(
//					"Learning failed. Cant build classifier. Exception is " + e.getMessage());
//		}
//
//		monitor.info("learning completed.");
//
//		/*
//		 * Reporting with training instances
//		 */
//		try {
//			Evaluation eval = new Evaluation(getInstances());
//			eval.evaluateModel(classifier, getInstances());
//			// context.getContext().getReport().write("## Machine learning cross validation
//			// results on training set\n\n");
//			// context.getContext().getReport().write("<pre>\n" + eval.toSummaryString() +
//			// "\n</pre>\n");
//			// context.getContext().getReport().write("\n" + eval.toClassDetailsString() +
//			// "\n");
//			// context.getContext().getReport().write("<pre>\n\n" + eval.toMatrixString() +
//			// "\n</pre>\n");
//
//		} catch (Exception e) {
//			throw new KlabContextualizationException("Learning evaluation failed. Can't evaluate classifier");
//		}
//
//	}
//
//	public void runModel(ILocator transition) throws KlabException {
//
//		getInstances().setClassIndex(0);
//		Instance datum = new DenseInstance(predictors.size() + 1);
//		datum.setDataset(getInstances());
//		datum.setClassMissing();
//
//		// TODO must add instance to dataset if it's not trained
//
//		for (ILocator n : context.getScale()) {
//
//			// if (!context.getScale().isCovered(n)) {
//			// States.set(outputState, null);
//			// continue;
//			// }
//
//			/*
//			 * add other predictors; if all values are not nodata or nodata are allowed and
//			 * at least one predictor is not nodata, add instance
//			 */
//			int nodata = 0;
//
//			for (int i = 0; i < predictors.size(); i++) {
//
//				Object value = predictors.get(i).state.get(n);
//				if (value == null || (value instanceof Number && Double.isNaN(((Number) value).doubleValue()))) {
//					nodata++;
//					datum.setMissing(i + 1);
//				} else {
//					datum.setValue(i + 1, predictors.get(i).getAttributeValue(value));
//				}
//			}
//			// datum.dataset().setClassIndex(0);
//
//			if (discretizer != null) {
//				// discretize instance
//				discretizer.input(datum);
//				datum = discretizer.output();
//			}
//
//			if (nodata == 0 || (acceptNoData && predictors.size() > nodata)) {
//
//				try {
//					if (predicted.legend != null) {
//
//						double pred = classifier.classifyInstance(datum);
//						outputState.set(n, pred);
//
//					} else {
//
//						// double predictionIndex = classifier.classifyInstance(instance);
//						// String predictedLabel =
//						// instances.classAttribute().value((int)predictionIndex);
//						double[] dist = classifier.distributionForInstance(datum);
//
//						if (outputState.getObservable().is(Type.PRESENCE)) {
//							outputState.set(n, dist[0] > PRESENCE_PROBABILITY_THRESHOLD ? Boolean.TRUE : Boolean.FALSE);
//						} else if (outputState.getObservable().is(Type.PROBABILITY)) {
//							outputState.set(n, dist[0]);
//
//						} else if (discretizer != null
//								&& outputState.getObservable().getArtifactType() == IArtifact.Type.NUMBER) {
//
//							double[] ranges = new double[dist.length + 1];
//							double[] cutPoints = discretizer.getCutPoints(0);
//
//							/*
//							 * use actual min/max for extremes ACHTUNG min/max are NaN if model comes
//							 * pre-trained
//							 */
//							ranges[0] = this.min;
//							for (int i = 0; i < discretizer.getBins(); i++) {
//								ranges[i + 1] = i == discretizer.getBins() - 1 ? this.max : cutPoints[i];
//							}
//
//							/*
//							 * probability distribution of outcomes
//							 */
//							outputState.set(n, new EnumeratedRealDistribution(dist, ranges));
//
//						}
//
//						// System.out.println(dist + "");
//						// int pred = Utils.maxIndex(dist);
//						// if (dist[(int) pred] <= 0) {
//						// pred = Instance.missingValue();
//						// }
//						// updateStatsForClassifier(dist, instance);
//					}
//				} catch (Exception e) {
//					throw new KlabContextualizationException(e);
//				}
//			} else {
//				outputState.set(n, null);
//			}
//		}
//	}
//
//	/*
//	 * default implementation does not save anything.
//	 */
//	public Pair<String, Collection<File>> saveModel() {
//		return null;
//	}
//
//	/**
//	 * Create sidecar files for the min/max and (if relevant) the discretizer.
//	 * 
//	 * @param outfile
//	 * @return
//	 */
//	public Collection<File> createDiscretizerSidecarFiles(File outfile) {
//
//		List<File> ret = new ArrayList<>();
//
//		File outDFile = MiscUtilities.getSidecarFile(outfile, DISCRETIZER_SUFFIX);
//		try (FileOutputStream fos = new FileOutputStream(outDFile);
//				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
//			oos.writeObject(discretizer);
//		} catch (Exception e) {
//			throw new KlabException(e);
//		}
//
//		ret.add(outDFile);
//
//		File propFile = MiscUtilities.getSidecarFile(outfile, ".properties");
//		MapUtils.saveProperties(propFile, "trained.min", min, "trained.max", max);
//
//		ret.add(propFile);
//
//		return ret;
//	}
//
//	public Instances getInstances() {
//		if (this.instances == null) {
//			int capacity = 0;
//			this.instances = new Instances(name + "_instances", getAttributes(), capacity);
//			if (discretizer != null) {
//				try {
//					this.instances = Filter.useFilter(this.instances, discretizer);
//				} catch (Exception e) {
//					throw new KlabException(e);
//				}
//			}
//		}
//		return instances;
//	}
//
//	/**
//	 * Called before anything if model is pre-trained, AFTER undiscretized instances
//	 * are made if training must happen.
//	 */
//	private void createDiscretizer() {
//		// TODO also check if any attributes are numeric
//		// TODO report
//		// TODO save undiscretized instances if required
//		try {
//			if (discretizer == null) {
//				// initialize discretizer
//				this.discretizer = new Discretize();
//				this.discretizer.setBins(nDiscretizationBins);
//				// this will make exportable bin names, although they will be less
//				// informative.
//				this.discretizer.setUseBinNumbers(true);
//				discretizer.setInputFormat(this.instances);
//			}
//			this.instances = Filter.useFilter(this.instances, discretizer);
//		} catch (Exception e) {
//			throw new KlabException("discretization failed:" + e.getMessage());
//		}
//	}
//
//    private String sanitizeName(String formalName) {
//        // will work fine for Weka, but not necessarily for other
//        // software such as Genie
//        return formalName.replaceAll("-", "_");
//    }
//	
//}