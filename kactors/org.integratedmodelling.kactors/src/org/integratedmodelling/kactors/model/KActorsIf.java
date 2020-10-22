package org.integratedmodelling.kactors.model;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.api.IKActorsStatement.If;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.kactors.ElseIfStatementBody;
import org.integratedmodelling.kactors.kactors.IfStatement;
import org.integratedmodelling.klab.utils.Pair;

public class KActorsIf extends KActorsStatement implements If {

	IKActorsValue condition;
	IKActorsStatement body;
	IKActorsStatement otherwise;
	List<Pair<IKActorsValue, IKActorsStatement>> elseifs = new ArrayList<>();

	public KActorsIf(IfStatement ifStatement, KActorCodeStatement parent) {

		super(ifStatement, parent, Type.IF_STATEMENT);

		if (ifStatement.getExpression() != null) {
			this.condition = new KActorsValue(IKActorsValue.Type.EXPRESSION,
					ifStatement.getExpression().substring(1, ifStatement.getExpression().length() - 1));
		} else if (ifStatement.getLiteral() != null) {
			this.condition = new KActorsValue(ifStatement.getLiteral(), this);
		} else if (ifStatement.getVariable() != null) {
			this.condition = new KActorsValue(IKActorsValue.Type.IDENTIFIER, ifStatement.getVariable());
		}

		if (ifStatement.getBody() != null) {
			this.body = KActorsStatement.create(ifStatement.getBody(), this);
		}

		if (ifStatement.getElseCall() != null) {
			this.otherwise = KActorsStatement.create(ifStatement.getElseCall(), this);
		}
		if (ifStatement.getElseIfBody() != null) {
			for (ElseIfStatementBody elseif : ifStatement.getElseIfBody()) {
				IKActorsValue elifCondition = null;
				if (elseif.getExpression() != null) {
					elifCondition = new KActorsValue(IKActorsValue.Type.EXPRESSION, elseif.getExpression());
				} else if (elseif.getLiteral() != null) {
					elifCondition = new KActorsValue(elseif.getLiteral(), this);
				} else if (elseif.getVariable() != null) {
					elifCondition = new KActorsValue(IKActorsValue.Type.IDENTIFIER, elseif.getVariable());
				}
				elseifs.add(new Pair<>(elifCondition, KActorsStatement.create(elseif.getBody(), this)));
			}
		}
	}

	@Override
	public IKActorsValue getCondition() {
		return condition;
	}

	@Override
	public IKActorsStatement getThen() {
		return body;
	}

	@Override
	public List<Pair<IKActorsValue, IKActorsStatement>> getElseIfs() {
		return elseifs;
	}

	@Override
	public IKActorsStatement getElse() {
		return otherwise;
	}

}
