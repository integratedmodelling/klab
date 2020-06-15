package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.api.IKActorsStatement.TextBlock;
import org.integratedmodelling.kactors.kactors.Statement;

public class KActorsText extends KActorsStatement implements TextBlock {
	
	String text;
	
	public KActorsText(Statement statement, KActorCodeStatement parent) {
		super(statement, parent, Type.TEXT_BLOCK);
		this.text = statement.getText().substring(3, statement.getText().length() - 4).trim();
	}

	@Override
	public String getText() {
		return this.text;
	}

}
