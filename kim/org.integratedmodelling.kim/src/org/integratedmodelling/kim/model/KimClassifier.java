package org.integratedmodelling.kim.model;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IKimClassifier;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.kim.Classifier;
import org.integratedmodelling.kim.kim.ClassifierRHS;
import org.integratedmodelling.kim.kim.ConceptDeclaration;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.utils.Range;

public class KimClassifier extends KimStatement implements IKimClassifier {

    private static final long serialVersionUID = 2634906673844696880L;

    // catch everything but nodata remains nodata
    private boolean catchAll = false;
    // catch everything including nodata
    private boolean catchAnything = false;
    private boolean negated = false;
    private IKimConcept conceptMatch;
	private Double numberMatch;
	private Boolean booleanMatch;
	private Range intervalMatch;
	private boolean nullMatch;
	private ArrayList<IKimClassifier> classifierMatches;
	private ArrayList<IKimConcept> conceptMatches;
	private String stringMatch;
	private IKimExpression expressionMatch;
    private IArtifact.Type type = Type.VOID;
    
	// this produces a catch-all and should only be called when isOtherwise() == true
    public KimClassifier(Classifier statement, IKimStatement parent) {
    	super(statement, parent);
    	this.sourceCode = "*";
    	this.catchAll = true;
    }
    
    // this produces anything else
    public KimClassifier(ClassifierRHS statement, boolean negate, IKimConcept matchedConcept, IKimStatement parent) {

    	super(statement, parent);
    	IKimNamespace namespace = Kim.INSTANCE.getNamespace(statement, true);

    	this.negated = negate;
        
        if (statement == null && matchedConcept != null) {
            conceptMatch = matchedConcept;
            type = Type.CONCEPT;
            return;
        } 
        
        if (statement.isAnything()) {
        	catchAnything = true;
        	type = Type.VALUE;
        	return;
        } else if (statement.getNum() != null) {
            Number n = Kim.INSTANCE.parseNumber(statement.getNum());
            this.numberMatch = n.doubleValue();
        	type = Type.NUMBER;
        } else if (statement.getBoolean() != null) {
            this.booleanMatch = statement.getBoolean().equals("true");
        	type = Type.BOOLEAN;
        } else if (statement.getInt0() != null) {

            Number from = Kim.INSTANCE.parseNumber(statement.getInt0());
            Number to = Kim.INSTANCE.parseNumber(statement.getInt1());
            String lt = statement.getLeftLimit();
            String rt = statement.getRightLimit();
            if (lt == null)
                lt = "inclusive";
            if (rt == null)
                rt = "exclusive";
            this.intervalMatch = new Range(from.doubleValue(), to.doubleValue(), lt
                    .equals("exclusive"), rt.equals("exclusive"));
        	type = Type.RANGE;

        } else if (statement.getOp() != null) {

            Range ni = null;
            Number op = Kim.INSTANCE.parseNumber(statement.getExpression());

            if (statement.getOp().isGe()) {
                ni = new Range(op.doubleValue(), null, false, true);
            } else if (statement.getOp().isGt()) {
                ni = new Range(op.doubleValue(), null, true, true);
            } else if (statement.getOp().isLe()) {
                ni = new Range(null, op.doubleValue(), true, false);
            } else if (statement.getOp().isLt()) {
                ni = new Range(null, op.doubleValue(), true, true);
            } else if (statement.getOp().isEq()) {
                numberMatch = op.doubleValue();
            } else if (statement.getOp().isNe()) {
                this.numberMatch = op.doubleValue();
                this.negated = true;
            }

            if (ni != null) {
                this.intervalMatch = ni;
            }
            
        	type = Type.RANGE;


        } else if (statement.getNodata() != null) {

        	this.nullMatch = true;
        	type = Type.VOID;

        } else if (statement.getSet() != null) {

        	// TODO these should be validated as all of same type
            for (Object o : Kim.INSTANCE.parseList(statement.getSet(), namespace)) {
                if (o instanceof Number) {
                    addClassifier(createNumberMatcher((Number) o));
                } else if (o instanceof String) {
                    addClassifier(createStringMatcher((String) o));
                } else if (o instanceof IConcept) {
                    addClassifier(createConceptMatcher((IConcept) o));
                } else if (o == null) {
                    addClassifier(createNullMatcher());
                } else if (o instanceof List<?>) {
                    addClassifier(createMultipleMatcher((List<?>) o));
                }
            }

            type = classifierMatches.size() > 0 ? classifierMatches.get(0).getType() : Type.VOID;
            
        } else if (statement.getToResolve() != null && statement.getToResolve().size() > 0) {

            for (ConceptDeclaration cdu : statement.getToResolve()) {
                if (conceptMatches == null) {
                    conceptMatches = new ArrayList<>();
                }
                conceptMatches.add(Kim.INSTANCE.declareConcept(cdu));
            }
            
        	type = Type.CONCEPT;

        } else if (statement.getConcept() != null) {
        	
        	conceptMatch = Kim.INSTANCE.declareConcept(statement.getConcept());
        	type = Type.CONCEPT;
        
        } else if (statement.getString() != null) {
        
        	this.stringMatch = statement.getString();
        	type = Type.TEXT;

        } else if (statement.getExpr() != null) {
        
        	this.expressionMatch = new KimExpression(statement.getExpr(), null);
        	type = Type.VALUE;

        } else if (statement.isStar()) {
        
        	catchAll = true;
        	type = Type.VOID;

        }
    }

	private void addClassifier(KimClassifier c) {
		if (this.classifierMatches == null)
			this.classifierMatches = new ArrayList<>();
		this.classifierMatches.add(c);
	}
    
	private KimClassifier createMultipleMatcher(List<?> o) {
		// TODO Auto-generated method stub
		return null;
	}

	private KimClassifier createNullMatcher() {
		// TODO Auto-generated method stub
		return null;
	}

	private KimClassifier createConceptMatcher(IConcept o) {
		// TODO Auto-generated method stub
		return null;
	}

	private KimClassifier createStringMatcher(String o) {
		// TODO Auto-generated method stub
		return null;
	}

	private KimClassifier createNumberMatcher(Number o) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
    public boolean isCatchAll() {
		return catchAll;
	}

	public void setCatchAll(boolean catchAll) {
		this.catchAll = catchAll;
	}

	@Override
	public boolean isCatchAnything() {
		return catchAnything;
	}

	public void setCatchAnything(boolean catchAnything) {
		this.catchAnything = catchAnything;
	}

	@Override
	public boolean isNegated() {
		return negated;
	}

	public void setNegated(boolean negated) {
		this.negated = negated;
	}

	@Override
	public IKimConcept getConceptMatch() {
		return conceptMatch;
	}

	public void setConceptMatch(IKimConcept conceptMatch) {
		this.conceptMatch = conceptMatch;
	}

	@Override
	public Double getNumberMatch() {
		return numberMatch;
	}

	public void setNumberMatch(Double numberMatch) {
		this.numberMatch = numberMatch;
	}

	@Override
	public Boolean getBooleanMatch() {
		return booleanMatch;
	}

	public void setBooleanMatch(Boolean booleanMatch) {
		this.booleanMatch = booleanMatch;
	}

	@Override
	public Range getIntervalMatch() {
		return intervalMatch;
	}

	public void setIntervalMatch(Range intervalMatch) {
		this.intervalMatch = intervalMatch;
	}

	@Override
	public boolean isNullMatch() {
		return nullMatch;
	}

	public void setNullMatch(boolean nullMatch) {
		this.nullMatch = nullMatch;
	}

	@Override
	public ArrayList<IKimClassifier> getClassifierMatches() {
		return classifierMatches;
	}

	public void setClassifierMatches(ArrayList<IKimClassifier> classifierMatches) {
		this.classifierMatches = classifierMatches;
	}

	@Override
	public ArrayList<IKimConcept> getConceptMatches() {
		return conceptMatches;
	}

	public void setConceptMatches(ArrayList<IKimConcept> conceptMatches) {
		this.conceptMatches = conceptMatches;
	}

	@Override
	public String getStringMatch() {
		return stringMatch;
	}

	public void setStringMatch(String stringMatch) {
		this.stringMatch = stringMatch;
	}

	@Override
	public IKimExpression getExpressionMatch() {
		return expressionMatch;
	}

	public void setExpressionMatch(IKimExpression expressionMatch) {
		this.expressionMatch = expressionMatch;
	}

	@Override
	public Type getType() {
		return type;
	}

}
