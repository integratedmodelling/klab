package org.integratedmodelling.klab.engine.runtime.code;

import org.integratedmodelling.kim.model.Geometry;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.owl.Observable;

public abstract class Expression implements IExpression {

    public static final Context emptyContext = new ExpressionContext();
    
    static class ExpressionContext implements Context {

        Namespace namespace;
        Model model;
        Observable observable;
        Geometry geometry;
        
        @Override
        public Namespace getNamespace() {
            return namespace;
        }

        @Override
        public Model getModel() {
            return model;
        }

        @Override
        public Observable getObservable() {
            return observable;
        }

        @Override
        public Geometry getGeometry() {
            return geometry;
        }
    }

    
}
