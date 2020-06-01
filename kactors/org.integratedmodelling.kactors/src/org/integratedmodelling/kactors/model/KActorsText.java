package org.integratedmodelling.kactors.model;

import java.util.function.Consumer;

import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.api.IKActorsStatement.TextBlock;
import org.integratedmodelling.kactors.kactors.Statement;

public class KActorsText extends KActorsStatement implements TextBlock {
	
	public KActorsText(Statement statement, KActorCodeStatement parent) {
		super(statement, parent, Type.TEXT_BLOCK);
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

}
