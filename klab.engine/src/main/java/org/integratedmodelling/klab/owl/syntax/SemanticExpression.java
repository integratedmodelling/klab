package org.integratedmodelling.klab.owl.syntax;

import org.integratedmodelling.kim.api.BinarySemanticOperator;
import org.integratedmodelling.kim.api.IKimConcept.ObservableRole;
import org.integratedmodelling.kim.api.SemanticModifier;
import org.integratedmodelling.kim.api.UnarySemanticOperator;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.klab.api.data.mediation.ICurrency;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

/**
 * A semantic expression represented as a graph built one token at a time.
 * 
 * @author Ferd
 *
 */
public class SemanticExpression {

	public class SemanticToken {

		public IConcept concept;
		public SemanticToken group;
		public IUnit unit;
		public ICurrency currency;
		public Object value;

		public boolean isEmpty() {
			return false;
		}

		public boolean is(Object o) {
			return false;
		}

		public boolean isAs(ObservableRole role) {
			return false;
		}

	}

	public class SemanticLink {

		public ObservableRole observableRole;
		public ValueOperator valueOperator;
		public UnarySemanticOperator unarySemanticOperator;
		public BinarySemanticOperator binarySemanticOperator;
		public SemanticModifier semanticModifier;
		public String syntacticElement;

		public boolean is(ObservableRole role) {
			return false;
		}

	}

	private SemanticToken head = new SemanticToken();
	private SemanticToken current;
	private String error = null;
	private Graph<SemanticToken, SemanticLink> graph = new DefaultDirectedGraph<>(SemanticLink.class);

	public SemanticExpression() {
		this.current = this.head;
		this.graph.addVertex(this.head);
	}

	public SemanticToken getCurrent() {
		return current;
	}

	public String getError() {
		return error;
	}

	public boolean accept(Object token) {
		return true;
	}

	public SemanticToken getHead() {
		return head;
	}
}
