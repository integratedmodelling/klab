package org.integratedmodelling.klab.rest;

public class ConsoleNotification {

    public static enum ConsoleType {
        Console, Debugger
    }

    public static enum NotificationType {
        NewConsole, UserCommand,
    }

    private String consoleId;
    private ConsoleType consoleType;
    private NotificationType notificationType;
    private String payload;
    private String commandId;

    public String getConsoleId() {
        return consoleId;
    }
    public void setConsoleId(String consoleId) {
        this.consoleId = consoleId;
    }
    public ConsoleType getConsoleType() {
        return consoleType;
    }
    public void setConsoleType(ConsoleType consoleType) {
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
