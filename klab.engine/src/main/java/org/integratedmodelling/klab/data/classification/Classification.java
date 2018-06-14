package org.integratedmodelling.klab.data.classification;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.kim.api.IKimClassification;
import org.integratedmodelling.kim.api.IKimClassifier;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.data.classification.IClassification;
import org.integratedmodelling.klab.api.data.classification.IClassifier;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Pair;

public class Classification implements IClassification {

	IConcept rootConcept;
	boolean discretization;
	List<Pair<IConcept, IClassifier>> classifiers = new ArrayList<>();

	public Classification(IKimClassification classification) {

		this.discretization = classification.isDiscretization();
		for (Pair<IKimConcept, IKimClassifier> classifier : classification) {
			IConcept concept = Concepts.INSTANCE.declare(classifier.getFirst());
			IClassifier clsf = new Classifier(classifier.getSecond());
			if (concept == null) {
				throw new KlabValidationException("classification: concept declaration is illegal: " + classifier.getFirst());
			}
			classifiers.add(new Pair<>(concept, clsf));
		}
	}

	@Override
	public Iterator<IClassifier> iterator() {
		List<IClassifier> ret = new ArrayList<>();
		for (Pair<IConcept, IClassifier> cls : classifiers) {
			ret.add(cls.getSecond());
		}
		return ret.iterator();
	}

	@Override
	public IConcept getConcept() {
		return rootConcept;
	}

	@Override
	public boolean isDiscretization() {
		return discretization;
	}

	@Override
	public boolean isContiguousAndFinite() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IConcept classify(Object object, IMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double undiscretize(IConcept object) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getNumericValue(IConcept o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int classifyToIndex(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static Classification create(IConcept rootClass) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addClassifier(Object create, IConcept c) {
		// TODO Auto-generated method stub

	}

}
