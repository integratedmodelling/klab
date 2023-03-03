package org.integratedmodelling.klab.api.lang.kim;

import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.api.knowledge.SemanticRole;
import org.integratedmodelling.klab.api.knowledge.SemanticType;
import org.integratedmodelling.klab.api.lang.UnarySemanticOperator;

/**
 * A IKimConcept is the declaration of a concept, i.e. a semantic expression
 * built out of known concepts and conforming to k.IM semantic constraints.
 * Concept expressions compile to this structure, which retains the final
 * concept names only as fully qualified names. External infrastructure can
 * create the actual concepts that a reasoner can operate on.
 * 
 * @author ferdinando.villa
 *
 */
public interface KKimConcept extends KKimStatement {

	enum Expression {
		SINGLETON, UNION, INTERSECTION
	}

	/**
	 * A leaf declaration contains a name (e.g. 'elevation:Geography'); all others
	 * do not. When the name is not null, there still may be a negation or a
	 * semantic operator.
	 * 
	 * @return the concept name or null.
	 */
	String getName();

	/**
	 * The type contains all declared attributes for the concept. An empty type
	 * denotes an inconsistent concept. The k.IM validator ensures that any
	 * non-empty types are internally consistent.
	 * 
	 * @return the set of types
	 */
	Set<SemanticType> getType();

	/**
	 * The main observable, which must be unique. This is null in a leaf
	 * declaration, where {@link #getName()} returns a non-null value.
	 * 
	 * @return the main observable
	 */
	KKimConcept getObservable();

	KKimConcept getContext();

	KKimConcept getInherent();

	KKimConcept getMotivation();

	KKimConcept getCausant();

	KKimConcept getCaused();

	KKimConcept getCompresent();

	KKimConcept getComparisonConcept();

	String getAuthorityTerm();

	String getAuthority();

	UnarySemanticOperator getSemanticModifier();

	KKimConcept getRelationshipSource();

	KKimConcept getRelationshipTarget();

	List<KKimConcept> getTraits();

	List<KKimConcept> getRoles();

	boolean isTemplate();

	boolean isNegated();

	String getDefinition();

	boolean is(SemanticType type);

	/**
	 * 
	 * @param visitor
	 */
	void visit(Visitor visitor);

	/**
	 * If {@link #getExpressionType()} returns anything other than
	 * {@link Expression#SINGLETON}, the operands are other declarations this is
	 * part of a union or intersection with.
	 * 
	 * @return the operands
	 */
	List<KKimConcept> getOperands();

	/**
	 * Type of expression. If anything other than {@link Expression#SINGLETON},
	 * {@link #getOperands()} will return a non-empty list.
	 * 
	 * @return the expression type
	 */
	Expression getExpressionType();

	/**
	 * Get the fundamental type of this concept - one of the concrete trait or
	 * observable types, including configuration and extent.
	 * 
	 * @return
	 */
	SemanticType getFundamentalType();

	/**
	 * Get the 'co-occurrent' (during) event type if any.
	 * 
	 * @return
	 */
	KKimConcept getCooccurrent();

	/**
	 * Get the concept that this is stated to be adjacent to if any.
	 * 
	 * @return
	 */
	KKimConcept getAdjacent();

	/**
	 * Return a string suitable for naming a k.IM object after this concept.
	 * 
	 * @return
	 */
	String getCodeName();

	SemanticRole getDistributedInherent();

	boolean isTraitObservable();

	/**
	 * Return any temporal inherency for this occurrent ('during each').
	 * 
	 * @return
	 */
	KKimConcept getTemporalInherent();

}
