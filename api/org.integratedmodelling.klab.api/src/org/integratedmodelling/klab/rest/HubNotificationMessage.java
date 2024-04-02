package org.integratedmodelling.klab.rest;

import org.integratedmodelling.klab.utils.Pair;

public class HubNotificationMessage {

    public HubNotificationMessage() {
        
    }
    
    public static class Parameters extends NotificationParameters {
        public Parameters(Pair<ExtendedInfo, Object>[] info) {
            super(info, null);
        }
        public Parameters(Pair<ExtendedInfo, Object>[] info, Type type) {
            super(info, type);
        }

    }
    
    interface MessageInterface {
        HubNotificationMessage build(String msg, NotificationParameters param);
    };
    
    public enum Type {
        SUCCESS,
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
            public HubNotificationMessage build(String msg, NotificationParameters param) {
                return new HubNotificationMessage(this, Type.ERROR, msg, param.info);
            }
        },
        EXPIRED_GROUP {
            @Override
            public HubNotificationMessage build(String msg, NotificationParameters param) {
                return new HubNotificationMessage(this, Type.ERROR, msg, param.info);
            }
        },
        EXPIRING_AGREEMENT {
            @Override
            public HubNotificationMessage build(String msg, NotificationParameters param) {
                return new HubNotificationMessage(this, Type.WARNING, msg, param.info);
            }
        },
        EXPIRING_CERTIFICATE {
            @Override
            public HubNotificationMessage build(String msg, NotificationParameters param) {
                return new HubNotificationMessage(this, Type.WARNING, msg, param.info);
            }
        },
        EXPIRING_GROUP {
            @Override
            public HubNotificationMessage build(String msg, NotificationParameters param) {
                return new HubNotificationMessage(this, Type.WARNING, msg, param.info);
            }
        },
        ADMINSTRATOR_MESSAGE {
            @Override
            public HubNotificationMessage build(String msg, NotificationParameters param) {
                return new HubNotificationMessage(this, Type.INFO, msg, param.info);
            }
        },
        GROUP_MESSAGE {
            @Override
            public HubNotificationMessage build(String msg, NotificationParameters param) {
                return new HubNotificationMessage(this, Type.INFO, msg, param.info);
            }
        },
        TAG_NOTIFICATION {
            @Override
            public HubNotificationMessage build(String msg, NotificationParameters param) {
                Type type = param.type.isEmpty() ? Type.INFO : param.type.get();
                return new HubNotificationMessage(this, type , msg, param.info);
            }
        },
        CERTIFICATE_WITHOUT_AGREEMENT {
            @Override
            public HubNotificationMessage build(String msg, NotificationParameters param) {
                Type type = param.type.isEmpty() ? Type.INFO : param.type.get();
                return new HubNotificationMessage(this, type , msg, param.info);
            }
        },
        AGREEMENT_NOT_EXIST{
            @Override
            public HubNotificationMessage build(String msg, NotificationParameters param) {
                Type type = param.type.isEmpty() ? Type.INFO : param.type.get();
                return new HubNotificationMessage(this, type , msg, param.info);  
            }
        },
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
