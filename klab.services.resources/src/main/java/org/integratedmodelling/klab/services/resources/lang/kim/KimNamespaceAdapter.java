package org.integratedmodelling.klab.services.resources.lang.kim;

import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.api.IKimSymbolDefinition;
import org.integratedmodelling.kim.model.KimLoader.NamespaceDescriptor;
import org.integratedmodelling.klab.api.errormanagement.ICompileNotification;
import org.integratedmodelling.klab.api.exceptions.KIllegalArgumentException;
import org.integratedmodelling.klab.api.lang.kim.KKimStatement;
import org.integratedmodelling.klab.api.lang.kim.impl.KimConceptStatement;
import org.integratedmodelling.klab.api.lang.kim.impl.KimModelStatement;
import org.integratedmodelling.klab.api.lang.kim.impl.KimNamespace;
import org.integratedmodelling.klab.utils.Utils;

/**
 * Serializable bean that will define itself from a parser result. Will serialize to a proper
 * KimNamespace but won't deserialize as itself.
 * 
 * @author Ferd
 *
 */
public class KimNamespaceAdapter extends KimNamespace {

    private static final long serialVersionUID = 1122513844962611205L;

    public KimNamespaceAdapter(NamespaceDescriptor ns) {

        IKimNamespace original = ns.getNamespace();
        Utils.Kim.copyStatementData(original, this);

        setName(original.getName());
        for (ICompileNotification notification : ns.getIssues()) {
            // TODO
        }

        setMetadata(Utils.Kim.makeMetadata(ns.getNamespace().getMetadata()));
        
        switch(ns.getNamespace().getScope()) {
        case NAMESPACE:
            setScope(Scope.PRIVATE);
            break;
        case PROJECT:
            setScope(Scope.PROJECT_PRIVATE);
            break;
        default:
            setScope(Scope.PUBLIC);
            break;
        }
        
        for (IKimScope statement : original.getChildren()) {
            getStatements().add(makeStatement(statement));
        }
    }

    private KKimStatement makeStatement(IKimScope statement) {

        if (statement instanceof IKimConceptStatement) {
            return makeConceptStatement((IKimConceptStatement) statement);
        } else if (statement instanceof IKimModel) {
            return makeModelStatement((IKimModel) statement);
        } else if (statement instanceof IKimSymbolDefinition) {
            return makeDefineStatement((IKimSymbolDefinition) statement);
        }
        throw new KIllegalArgumentException("statement " + statement + " cannot be understood");
    }

    private KKimStatement makeDefineStatement(IKimSymbolDefinition statement) {
        // TODO Auto-generated method stub
        return null;
    }

    private KKimStatement makeModelStatement(IKimModel statement) {
        KimModelStatement ret = new KimModelStatement();
        Utils.Kim.copyStatementData(statement, ret);
        return null;
    }

    private KKimStatement makeConceptStatement(IKimConceptStatement statement) {
        KimConceptStatement ret = new KimConceptStatement();
        Utils.Kim.copyStatementData(statement, ret);
        return ret;
    }

}
