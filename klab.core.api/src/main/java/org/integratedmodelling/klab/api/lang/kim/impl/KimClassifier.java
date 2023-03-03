package org.integratedmodelling.klab.api.lang.kim.impl;

import java.util.ArrayList;

import org.integratedmodelling.klab.api.collections.impl.Range;
import org.integratedmodelling.klab.api.knowledge.KArtifact.Type;
import org.integratedmodelling.klab.api.lang.kim.KKimClassifier;
import org.integratedmodelling.klab.api.lang.kim.KKimConcept;
import org.integratedmodelling.klab.api.lang.kim.KKimDate;
import org.integratedmodelling.klab.api.lang.kim.KKimExpression;
import org.integratedmodelling.klab.api.lang.kim.KKimQuantity;

/**
 * Syntactic bean for a k.IM classifier, used in both classifications and lookup tables.
 * 
 * @author ferdinando.villa
 *
 */
public class KimClassifier extends KimStatement implements KKimClassifier {

    private static final long serialVersionUID = 8284840092691497201L;
    private boolean catchAll;
    private boolean catchAnything;
    private boolean negated;
    private KKimConcept conceptMatch;
    private Double numberMatch;
    private Boolean booleanMatch;
    private ArrayList<KKimClassifier> classifierMatches;
    private Range intervalMatch;
    private boolean nullMatch;
    private KKimExpression expressionMatch;
    private String stringMatch;
    private ArrayList<KKimConcept> conceptMatches;
    private KKimQuantity quantityMatch;
    private KKimDate dateMatch;
    private Type type;

    @Override
    public boolean isCatchAll() {
        return catchAll;
    }

    @Override
    public boolean isCatchAnything() {
        return catchAnything;
    }

    @Override
    public boolean isNegated() {
        return negated;
    }

    @Override
    public KKimConcept getConceptMatch() {
        return conceptMatch;
    }

    @Override
    public Double getNumberMatch() {
        return numberMatch;
    }

    @Override
    public Boolean getBooleanMatch() {
        return booleanMatch;
    }

    @Override
    public ArrayList<KKimClassifier> getClassifierMatches() {
        return classifierMatches;
    }

    @Override
    public Range getIntervalMatch() {
        return intervalMatch;
    }

    @Override
    public boolean isNullMatch() {
        return nullMatch;
    }

    @Override
    public KKimExpression getExpressionMatch() {
        return expressionMatch;
    }

    @Override
    public String getStringMatch() {
        return stringMatch;
    }

    @Override
    public ArrayList<KKimConcept> getConceptMatches() {
        return conceptMatches;
    }

    @Override
    public KKimQuantity getQuantityMatch() {
        return quantityMatch;
    }

    @Override
    public KKimDate getDateMatch() {
        return dateMatch;
    }

    @Override
    public Type getType() {
        return type;
    }

    public void setCatchAll(boolean catchAll) {
        this.catchAll = catchAll;
    }

    public void setCatchAnything(boolean catchAnything) {
        this.catchAnything = catchAnything;
    }

    public void setNegated(boolean negated) {
        this.negated = negated;
    }

    public void setConceptMatch(KKimConcept conceptMatch) {
        this.conceptMatch = conceptMatch;
    }

    public void setNumberMatch(Double numberMatch) {
        this.numberMatch = numberMatch;
    }

    public void setBooleanMatch(Boolean booleanMatch) {
        this.booleanMatch = booleanMatch;
    }

    public void setClassifierMatches(ArrayList<KKimClassifier> classifierMatches) {
        this.classifierMatches = classifierMatches;
    }

    public void setIntervalMatch(Range intervalMatch) {
        this.intervalMatch = intervalMatch;
    }

    public void setNullMatch(boolean nullMatch) {
        this.nullMatch = nullMatch;
    }

    public void setExpressionMatch(KKimExpression expressionMatch) {
        this.expressionMatch = expressionMatch;
    }

    public void setStringMatch(String stringMatch) {
        this.stringMatch = stringMatch;
    }

    public void setConceptMatches(ArrayList<KKimConcept> conceptMatches) {
        this.conceptMatches = conceptMatches;
    }

    public void setQuantityMatch(KKimQuantity quantityMatch) {
        this.quantityMatch = quantityMatch;
    }

    public void setDateMatch(KKimDate dateMatch) {
        this.dateMatch = dateMatch;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
