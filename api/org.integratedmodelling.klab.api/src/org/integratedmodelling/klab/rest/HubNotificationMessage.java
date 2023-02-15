package org.integratedmodelling.klab.rest;

import java.util.List;

import org.integratedmodelling.klab.utils.Pair;


public class HubNotificationMessage {
    
    public HubNotificationMessage() {
        
    }
    
    interface MessageInterface {
        HubNotificationMessage build(String msg, Pair<ExtendedInfo, Object>[] info);
    };
    
    public enum Type {
        ERROR,
        WARNING,
        INFO
    }
    
    public enum ExtendedInfo {
        GROUP_NAME,
        EXPIRATION_DATE,
        SHORT_MESSAGE,
        LARGE_MESSAGE,
    }
    
    public enum MessageClass implements MessageInterface{
        EXPIRED_CERTIFICATE {
            @Override
            public HubNotificationMessage build(String msg, Pair<ExtendedInfo, Object>[] info) {
                return new HubNotificationMessage(this, Type.ERROR, msg, info);
            }

            
        },
        EXPIRED_GROUP {
            @Override
            public HubNotificationMessage build(String msg, Pair<ExtendedInfo, Object>[] info) {
                return new HubNotificationMessage(this, Type.ERROR, msg, info);
            }
        },
        EXPIRING_CERTIFICATE {
            @Override
            public HubNotificationMessage build(String msg, Pair<ExtendedInfo, Object>[] info) {
                return new HubNotificationMessage(this, Type.WARNING, msg, info);
            }

            
        },
        EXPIRING_GROUP {
            @Override
            public HubNotificationMessage build(String msg, Pair<ExtendedInfo, Object>[] info) {
                return new HubNotificationMessage(this, Type.WARNING, msg, info);
            }


        },
        ADMINSTRATOR_MESSAGE {
            @Override
            public HubNotificationMessage build(String msg, Pair<ExtendedInfo, Object>[] info) {
                return new HubNotificationMessage(this, Type.INFO, msg, info);
            }

            
        },
        GROUP_MESSAGE {
            @Override
            public HubNotificationMessage build(String msg, Pair<ExtendedInfo, Object>[] info) {
                return new HubNotificationMessage(this, Type.INFO, msg, info);
            }

        },
        TAG_NOTIFICATION { // TODO -> Tag notifications should be more flexible
            @Override
            public HubNotificationMessage build(String msg, Pair<ExtendedInfo, Object>[] info) {
                return new HubNotificationMessage(this, Type.INFO, msg, info);
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
    private Pair<ExtendedInfo, Object>[] info;
    
    public HubNotificationMessage(MessageClass messageClass, Type type, String msg, Pair<ExtendedInfo, Object>[] info) {
        this.messageClass = messageClass;
        this.type = type;
        this.msg = msg;
        this.info = info;
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

    public Pair<ExtendedInfo, Object>[] getInfo() {
        return info;
    }

    public void setInfo(Pair<ExtendedInfo, Object>[] info) {
        this.info = info;
    }
    
    
    
}
