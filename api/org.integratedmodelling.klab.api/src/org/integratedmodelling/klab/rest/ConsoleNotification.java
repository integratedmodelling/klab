package org.integratedmodelling.klab.rest;

import org.integratedmodelling.klab.api.cli.IConsole;

public class ConsoleNotification {

    public static enum NotificationType {
        NewConsole, UserCommand,
    }

    private String consoleId;
    private IConsole.Type consoleType;
    private NotificationType notificationType;
    private String payload;
    private String commandId;

    public String getConsoleId() {
        return consoleId;
    }
    public void setConsoleId(String consoleId) {
        this.consoleId = consoleId;
    }
    public IConsole.Type getConsoleType() {
        return consoleType;
    }
    public void setConsoleType(IConsole.Type consoleType) {
        this.consoleType = consoleType;
    }
    public NotificationType getNotificationType() {
        return notificationType;
    }
    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }
    public String getPayload() {
        return payload;
    }
    public void setPayload(String payload) {
        this.payload = payload;
    }
    public String getCommandId() {
        return commandId;
    }
    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

}
