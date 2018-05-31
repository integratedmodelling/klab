package org.integratedmodelling.klab.data.classification;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.kim.api.IKimClassification;
import org.integratedmodelling.klab.api.data.classification.IClassification;
import org.integratedmodelling.klab.api.data.classification.IClassifier;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public class Classification implements IClassification {

	IConcept rootConcept;
	List<IClassifier> classifiers = new ArrayList<>();

	public Classification(IKimClassification classification) {
		
	}

	@Override
	public Iterator<IClassifier> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IConcept getConcept() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDiscretization() {
		// TODO Auto-generated method stub
		return false;
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
