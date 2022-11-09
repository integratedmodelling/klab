package org.integratedmodelling.kactors.model;

import org.eclipse.emf.ecore.EObject;
import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Visitor;
import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.kactors.MetadataPair;
import org.integratedmodelling.kactors.kactors.Statement;
import org.integratedmodelling.kactors.kactors.StatementBody;

public abstract class KActorsStatement extends KActorCodeStatement implements IKActorsStatement {

    private Type type;

    public KActorsStatement(EObject statement, KActorCodeStatement parent, Type type) {
        super(statement, parent);
        this.type = type;
    }

    public KActorsStatement(KActorCodeStatement parent, Type type) {
        super(parent.getEStatement(), parent);
        this.type = type;
    }

    public String toString() {
        return type + " statement: " + resource + ": " + firstLine;
    }

    public static KActorsStatement create(StatementBody statementBody, KActorCodeStatement parent) {
        KActorsStatement ret = null;
        if (statementBody != null) {
            if (statementBody.getGroup() != null) {
                ret = new KActorsConcurrentGroup(statementBody.getGroup(), parent);
            } else if (statementBody.getVerb() != null) {
                if (statementBody.getVerb().getName() != null && "$".equals(statementBody.getVerb().getName().trim())) {
                    // special case: it's "re-fire whatever was fired"
                    ret = new KActorsFire(parent);
                } else {
                    ret = new KActorsActionCall(statementBody.getVerb(), parent);
                }
            } else if (statementBody.getValue() != null) {
                ret = new KActorsFire(statementBody.getValue(), parent);
            }
        }
        return ret;
    }

    public static KActorsStatement create(Statement statement, KActorCodeStatement parent) {

        KActorsStatement ret = null;

        if (statement.getAssignment() != null) {
            ret = new KActorsAssignment(statement.getAssignment(), parent);
        } else if (statement.getDo() != null) {
            ret = new KActorsDo(statement.getDo(), parent);
        } else if (statement.getFor() != null) {
            ret = new KActorsFor(statement.getFor(), parent);
        } else if (statement.getIf() != null) {
            ret = new KActorsIf(statement.getIf(), parent);
        } else if (statement.getWhile() != null) {
            ret = new KActorsWhile(statement.getWhile(), parent);
        } else if (statement.getText() != null) {
            ret = new KActorsText(statement, parent);
            if (statement.getMetadata() != null) {
                for (MetadataPair pair : statement.getMetadata().getPairs()) {
                    String key = pair.getKey().substring(1);
                    boolean negative = pair.getKey().startsWith("!");
                    KActorsValue v = null;
                    if (pair.getValue() != null) {
                        v = new KActorsValue(pair.getValue(), ret);
                    } else {
                        v = new KActorsValue(!negative, ret);
                    }
                    ret.metadata.put(key, v);
                }
            }
        } else if (statement.getValue() != null) {
            ret = new KActorsFire(statement.getValue(), parent);
        } else if (statement.getVerb() != null) {
            if ("$".equals(statement.getVerb().getName().trim())) {
                // special case: it's "re-fire whatever was fired"
                ret = new KActorsFire(parent);
            } else {
                ret = new KActorsActionCall(statement.getVerb(), parent);
            }
        } else if (statement.getGroup() != null) {
            ret = new KActorsConcurrentGroup(statement.getGroup(), parent);
        } else if (statement.getInstantiation() != null) {
            ret = new KActorsInstantiation(statement.getInstantiation(), statement.getTag(), parent);
        } else if (statement.getAssert() != null) {
            ret = new KActorsAssert(statement.getAssert(), parent);
        } else if (statement.isBreak()) {
            ret = new KActorsBreak(statement, parent);
        } else if (statement.getFail() != null) {
            ret = new KActorsFail(statement.getFail(), parent);
        }

        if (ret != null) {
            // in most situation, after initialization is OK
            if (statement.getTag() != null) {
                ret.tag = statement.getTag().substring(1);
            }

        }

        return ret;
    }

    @Override
    public Type getType() {
        return type;
    }

    protected void visit(IKActorsAction action, Visitor visitor) {
        visitor.visitStatement(action, this);
        visitMetadata(metadata, visitor);
    }

    public static void visitValue(Visitor visitor, IKActorsValue value, IKActorsStatement kActorsActionCall,
            IKActorsAction action) {
        ((KActorsValue) value).visit(visitor, kActorsActionCall, action);
    }

}
