package org.integratedmodelling.klab.rest;

public class HubNotificationMessage {
    

    public HubNotificationMessage() {
        
    }
    
    interface MessageInterface {
        HubNotificationMessage build(String msg);
    };
    
    public enum Type {
        ERROR,
        WARNING,
        INFO
    }
    
    public enum MessageClass implements MessageInterface{
        EXPIRED_CERTIFICATE {
            @Override
            public HubNotificationMessage build(String msg) {
                return new HubNotificationMessage(this, Type.ERROR, msg);
            }

            
        },
        EXPIRED_GROUP {
            @Override
            public HubNotificationMessage build(String msg) {
                return new HubNotificationMessage(this, Type.ERROR, msg);
            }
        },
        EXPIRING_CERTIFICATE {
            @Override
            public HubNotificationMessage build(String msg) {
                return new HubNotificationMessage(this, Type.WARNING, msg);
            }

            
        },
        EXPIRING_GROUP {
            @Override
            public HubNotificationMessage build(String msg) {
                return new HubNotificationMessage(this, Type.WARNING, msg);
            }


        },
        ADMINSTRATOR_MESSAGE {
            @Override
            public HubNotificationMessage build(String msg) {
                return new HubNotificationMessage(this, Type.INFO, msg);
            }

            
        },
        GROUP_MESSAGE {
            @Override
            public HubNotificationMessage build(String msg) {
                return new HubNotificationMessage(this, Type.INFO, msg);
            }

        }
        
    }
   
    
//    public enum WARNING implements MessageInterface {
//        EXPIRING_CERTIFICATE {
//
//            @Override
//            public HubNotificationMessage get(String msg) {
//                return new HubNotificationMessage(this.name(), msg);
//            }
//            
//        },
//        EXPIRING_GROUP {
//
//            @Override
//            public HubNotificationMessage get(String msg) {
//                return new HubNotificationMessage(this.name(), msg);
//            }
//
//        }
//    }
//    
//    enum INFO implements MessageInterface {
//        ADMINSTRATOR_MESSAGE {
//
//            @Override
//            public HubNotificationMessage get(String msg) {
//                return new HubNotificationMessage(this.name(), msg);
//            }
//            
//        },
//        GROUP_MESSAGE {
//
//            @Override
//            public HubNotificationMessage get(String msg) {
//                return new HubNotificationMessage(this.name(), msg);
//            }
//        }
//    }
    
    private Type type;
    private MessageClass messageClass;
    private String msg;
    
    public HubNotificationMessage(MessageClass messageClass, Type type, String msg) {
        this.messageClass = messageClass;
        this.type = type;
        this.msg = msg;
    }

    public MessageClass getMessageClass() {
        return messageClass;
    }

    public String getMsg() {
        return msg;
    }

    public Type getType() {
        return type;
    }
    
    
    
}
