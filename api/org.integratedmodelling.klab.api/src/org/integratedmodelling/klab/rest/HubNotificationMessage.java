package org.integratedmodelling.klab.rest;

public class HubNotificationMessage {
    
    interface MessageInterface {
        HubNotificationMessage get(String msg);
    };
    
    public enum ERROR implements MessageInterface {
        EXPIRED_CERTIFICATE {

            @Override
            public HubNotificationMessage get(String msg) {
                return new HubNotificationMessage(ERROR.EXPIRED_CERTIFICATE, msg);
            }
            
        },
        EXPIRED_GROUP {

            @Override
            public HubNotificationMessage get(String msg) {
                return new HubNotificationMessage(ERROR.EXPIRED_GROUP, msg);
            }   
        }
    }
    
    public enum WARNING implements MessageInterface {
        EXPIRING_CERTIFICATE {

            @Override
            public HubNotificationMessage get(String msg) {
                return new HubNotificationMessage(WARNING.EXPIRING_CERTIFICATE, msg);
            }
            
        },
        EXPIRING_GROUP {

            @Override
            public HubNotificationMessage get(String msg) {
                return new HubNotificationMessage(WARNING.EXPIRING_GROUP, msg);
            }
        }
    }
    
    enum INFO implements MessageInterface {
        ADMINSTRATOR_MESSAGE {

            @Override
            public HubNotificationMessage get(String msg) {
                return new HubNotificationMessage(WARNING.EXPIRING_CERTIFICATE, msg);
            }
            
        },
        GROUP_MESSAGE {

            @Override
            public HubNotificationMessage get(String msg) {
                return new HubNotificationMessage(WARNING.EXPIRING_GROUP, msg);
            }
        }
    }
    
    private MessageInterface type;
    private String msg;
    
    public HubNotificationMessage(MessageInterface type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public MessageInterface getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }
    
    
    
}
