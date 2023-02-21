//package org.integratedmodelling.klab.utils;
//
//import org.integratedmodelling.klab.Concepts;
//import org.integratedmodelling.klab.api.knowledge.IConcept;
//
//public class TypeUtils extends Utils {
//
//    /**
//     * More powerful version of asType for concept-aware APIs.
//     * 
//     * @param o
//     * @param type
//     * @return
//     */
//    public static Object convert(Object o, Class<?> type) {
//        try {
//            return asType(o, type);
//        } catch (IllegalArgumentException e) {
//            if (o instanceof String && type.equals(IConcept.class)) {
//                return Concepts.c((String)o);
//            }
//        }
//        
//        throw new IllegalArgumentException("cannot interpret value " + o + " as a "
//                + type.getCanonicalName());
//    }
//    
//}
