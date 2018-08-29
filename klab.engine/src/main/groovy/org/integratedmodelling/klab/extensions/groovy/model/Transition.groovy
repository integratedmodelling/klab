package org.integratedmodelling.klab.extensions.groovy.model

import org.integratedmodelling.klab.api.data.ILocator
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException

class Transition {

    ILocator transition;

    Transition(ILocator transition) {
        this.transition = transition;
    }

    def getStart() {
		throw new KlabUnimplementedException("groovy.Transition");
//        return new Date(transition.getTime().getStart());
    }

    def getEnd() {
		throw new KlabUnimplementedException("groovy.Transition");
//        return new Date(transition.getTime().getEnd());
    }

    Object asType(Class cls) {
		throw new KlabUnimplementedException("groovy.Transition");
//        if (cls == Date) {
//            def mid = transition.getTime().getStart().getMillis()  + (transition.getTime().getEnd().getMillis() - transition.getTime().getStart().getMillis())/2;
//            return new Date(mid as Long);
//        }
//        return null;
    }

    public String toString() {
		throw new KlabUnimplementedException("groovy.Transition");
//        return transition.toString();
    }

    def minus(Object o) {
		throw new KlabUnimplementedException("groovy.Transition");
		
//        if (o instanceof Integer) {
//            ILocator t = transition;
//            for (i in 0..((Integer)o)) {
//                t = t.previous();
//            }
//            return new Transition(t);
//        }
//        return null;
    }
    
    def getDays() {
		throw new KlabUnimplementedException("groovy.Transition");
//        return ((org.integratedmodelling.common.model.runtime.Transition)transition).getDays();
    }

    def getMinutes() {
		throw new KlabUnimplementedException("groovy.Transition");
//        return ((org.integratedmodelling.common.model.runtime.Transition)transition).getMinutes();
    }

    def getSeconds() {
		throw new KlabUnimplementedException("groovy.Transition");
//        return ((org.integratedmodelling.common.model.runtime.Transition)transition).getSeconds();
    }

    def getWeeks() {
		throw new KlabUnimplementedException("groovy.Transition");
//        return ((org.integratedmodelling.common.model.runtime.Transition)transition).getWeeks();
    }

    def getHours() {
		throw new KlabUnimplementedException("groovy.Transition");
//        return ((org.integratedmodelling.common.model.runtime.Transition)transition).getHours();
    }

    def getYears() {
		throw new KlabUnimplementedException("groovy.Transition");
//        return ((org.integratedmodelling.common.model.runtime.Transition)transition).getYears();
    }

    
}
