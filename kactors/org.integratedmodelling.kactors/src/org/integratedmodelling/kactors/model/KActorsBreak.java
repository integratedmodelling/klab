package org.integratedmodelling.kactors.model;

import org.eclipse.emf.ecore.EObject;
import org.integratedmodelling.kactors.api.IKActorsStatement.Break;

public class KActorsBreak extends KActorsStatement implements Break {

    public KActorsBreak(EObject block, KActorCodeStatement parent) {
        super(block, parent, Type.BREAK_STATEMENT);
    }

}
