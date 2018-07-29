package org.integratedmodelling.klab.common;

import java.util.logging.Level;

import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.klab.api.errormanagement.ICompileNotification;
import org.integratedmodelling.klab.rest.CompileNotificationReference;

public class CompileNotification implements ICompileNotification {

    private Level level;
    private String namespaceId;
    private IKimStatement statement;
    private String message;
    
    private CompileNotification() {}

    public static CompileNotification create(Level level, String message, String namespace, IKimStatement statement) {
        CompileNotification ret = new CompileNotification();
        ret.level = level;
        ret.message = message;
        ret.namespaceId = namespace;
        ret.statement = statement;
        return ret;
    }
    
    // TODO allow passing a kim object and set the URN from there
    public static CompileNotification error(String message, String namespace, IKimStatement statement) {
        CompileNotification ret = new CompileNotification();
        ret.level = Level.SEVERE;
        ret.message = message;
        ret.namespaceId = namespace;
        ret.statement = statement;
        return ret;
    }

    public static CompileNotification warning(String message, String namespace, IKimStatement statement) {
        CompileNotification ret = new CompileNotification();
        ret.level = Level.WARNING;
        ret.message = message;
        ret.namespaceId = namespace;
        ret.statement = statement;
        return ret;
    }

    public static CompileNotification info(String message, String namespace, IKimStatement statement) {
        CompileNotification ret = new CompileNotification();
        ret.level = Level.INFO;
        ret.message = message;
        ret.namespaceId = namespace;
        ret.statement = statement;
        return ret;
    }
    
    public static CompileNotification debug(String message, String namespace, IKimStatement statement) {
        CompileNotification ret = new CompileNotification();
        ret.level = Level.FINE;
        ret.message = message;
        ret.namespaceId = namespace;
        ret.statement = statement;
        return ret;
    }

    public CompileNotificationReference getReference() {
        CompileNotificationReference ret = new CompileNotificationReference();
        ret.setFirstLine(statement.getFirstLine());
        ret.setLastLine(statement.getLastLine());
        ret.setStartOffset(statement.getFirstCharOffset());
        ret.setEndOffset(statement.getLastCharOffset());
        ret.setMessage(message);
        ret.setLevel(level.intValue());
        return ret;
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public String getNamespaceId() {
        return namespaceId;
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
