package org.integratedmodelling.klab.engine.indexing;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.BinarySemanticOperator;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.Modifier;
import org.integratedmodelling.kim.api.UnarySemanticOperator;
import org.integratedmodelling.klab.api.services.IIndexingService;

public class SearchMatch implements IIndexingService.Match {

    String id;
    String name;
    String description = "";
    float score;
    Type matchType;
    Map<String, String> indexableFields = new HashMap<>();
    Set<IKimConcept.Type> conceptType = EnumSet.noneOf(IKimConcept.Type.class);
    Set<IKimConcept.Type> semantics = EnumSet.noneOf(IKimConcept.Type.class);
    UnarySemanticOperator unaryOperator = null;
    BinarySemanticOperator binaryOperator = null;
    Modifier modifier = null;
    
    boolean isAbstract = false;

    public SearchMatch() {
    }

    public SearchMatch(Type matchType, Set<org.integratedmodelling.kim.api.IKimConcept.Type> conceptType) {
        this.matchType = matchType;
        this.conceptType.addAll(conceptType);
    }

    public SearchMatch(UnarySemanticOperator op) {
    	this.unaryOperator = op;
    	this.matchType = Type.PREFIX_OPERATOR;
    	this.id = this.name = op.declaration[0];
    }

    public SearchMatch(BinarySemanticOperator op) {
    	this.binaryOperator = op;
    	this.matchType = Type.INFIX_OPERATOR;
    	this.id = this.name = op.name().toLowerCase();
	}
    
    public SearchMatch(Modifier op) {
    	this.modifier = op;
    	this.matchType = Type.MODIFIER;
    	this.id = this.name = modifier.declaration[0];
	}

	@Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public float getScore() {
        return score;
    }

    @Override
    public Type getMatchType() {
        return matchType;
    }

    @Override
    public Set<org.integratedmodelling.kim.api.IKimConcept.Type> getConceptType() {
        return conceptType;
    }

    @Override
    public Map<String, String> getIndexableFields() {
        return indexableFields;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description == null ? "" : description;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public void setMatchType(Type matchType) {
        this.matchType = matchType;
    }

    public void setIndexableFields(Map<String, String> indexableFields) {
        this.indexableFields = indexableFields;
    }

    public void setConceptType(Set<IKimConcept.Type> conceptType) {
        this.conceptType = conceptType;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

	public Set<IKimConcept.Type> getSemantics() {
		return semantics;
	}

	public void setSemantics(Set<IKimConcept.Type> semantics) {
		this.semantics = semantics;
	}

	public UnarySemanticOperator getUnaryOperator() {
		return unaryOperator;
	}

	public void setUnaryOperator(UnarySemanticOperator unaryOperator) {
		this.unaryOperator = unaryOperator;
	}

	public BinarySemanticOperator getBinaryOperator() {
		return binaryOperator;
	}

	public void setBinaryOperator(BinarySemanticOperator binaryOperator) {
		this.binaryOperator = binaryOperator;
	}

	public Modifier getModifier() {
		return modifier;
	}

	public void setModifier(Modifier modifier) {
		this.modifier = modifier;
	}

}
