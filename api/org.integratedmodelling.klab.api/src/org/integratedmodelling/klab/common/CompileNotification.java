package org.integratedmodelling.klab.common;

import java.util.logging.Level;

import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.klab.api.errormanagement.ICompileNotification;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.rest.CompileNotificationReference;

public class CompileNotification implements ICompileNotification {

    private Level level;
    private INamespace namespace;
    private IKimStatement statement;
    private String message;
    
    private CompileNotification() {}

    public static CompileNotification create(Level level, String message, INamespace namespace, IKimStatement statement) {
        CompileNotification ret = new CompileNotification();
        ret.level = level;
        ret.message = message;
        ret.namespace = namespace;
        ret.statement = statement;
        return ret;
    }
    
    // TODO allow passing a kim object and set the URN from there
    public static CompileNotification error(String message, INamespace namespace, IKimStatement statement) {
        CompileNotification ret = new CompileNotification();
        ret.level = Level.SEVERE;
        ret.message = message;
        ret.namespace = namespace;
        ret.statement = statement;
        return ret;
    }

    public static CompileNotification warning(String message, INamespace namespace, IKimStatement statement) {
        CompileNotification ret = new CompileNotification();
        ret.level = Level.WARNING;
        ret.message = message;
        ret.namespace = namespace;
        ret.statement = statement;
        return ret;
    }

    public static CompileNotification info(String message, INamespace namespace, IKimStatement statement) {
        CompileNotification ret = new CompileNotification();
        ret.level = Level.INFO;
        ret.message = message;
        ret.namespace = namespace;
        ret.statement = statement;
        return ret;
    }
    
    public static CompileNotification debug(String message, INamespace namespace, IKimStatement statement) {
        CompileNotification ret = new CompileNotification();
        ret.level = Level.FINE;
        ret.message = message;
        ret.namespace = namespace;
        ret.statement = statement;
        return ret;
    }

    public CompileNotificationReference getReference() {
        CompileNotificationReference ret = new CompileNotificationReference();
        ret.setFirstLine(statement.getFirstLine());
        ret.setLastLine(statement.getLastLine());
        ret.setStartOffset(statement.getFirstCharOffset());
        ret.setEndOffset(statement.getLastCharOffset());
        ret.setNamespaceId(namespace.getId());
        ret.setMessage(message);
        ret.setLevel(level.intValue());
        // TODO this is not the URN
//        ret.setStatementUrn(statement.getURI().toString());
        return ret;
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public INamespace getNamespace() {
        return namespace;
    }

    @Override
    public IKimStatement getStatement() {
        return statement;
    }

    @Override
    public String getMessage() {
        return message;
    }
    
}
