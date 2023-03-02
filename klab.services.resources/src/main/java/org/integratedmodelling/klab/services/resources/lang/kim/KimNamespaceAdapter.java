package org.integratedmodelling.klab.services.resources.lang.kim;

import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IKimSymbolDefinition;
import org.integratedmodelling.kim.model.KimLoader.NamespaceDescriptor;
import org.integratedmodelling.klab.api.exceptions.KIllegalArgumentException;
import org.integratedmodelling.klab.api.lang.KAnnotation;
import org.integratedmodelling.klab.api.lang.kim.KKimStatement;
import org.integratedmodelling.klab.api.lang.kim.impl.KimConceptStatement;
import org.integratedmodelling.klab.api.lang.kim.impl.KimModelStatement;
import org.integratedmodelling.klab.api.lang.kim.impl.KimNamespace;
import org.integratedmodelling.klab.api.lang.kim.impl.KimStatement;
import org.integratedmodelling.klab.data.collections.SerializableAnnotation;

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
        copyStatementData(original, this);
        
        setName(original.getName());
        
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
        copyStatementData(statement, ret);
        return null;
    }

    private void copyStatementData(IKimStatement source, KimStatement destination) {

        destination.setFirstLine(source.getFirstLine());
        destination.setLastLine(source.getLastLine());
        destination.setFirstCharOffset(source.getFirstCharOffset());
        destination.setLastCharOffset(source.getLastCharOffset());
        destination.setSourceCode(source.getSourceCode());
        
        for (IKimAnnotation annotation : source.getAnnotations()) {
            destination.getAnnotations().add(makeSerializableAnnotation(annotation));
        }
    }

    private KKimStatement makeConceptStatement(IKimConceptStatement statement) {
        KimConceptStatement ret = new KimConceptStatement();
        copyStatementData(statement, ret);
        return ret;
    }

    private KAnnotation makeSerializableAnnotation(IKimAnnotation annotation) {
        SerializableAnnotation ret = new SerializableAnnotation();
        ret.setName(annotation.getName());
        return ret;
    }

}
