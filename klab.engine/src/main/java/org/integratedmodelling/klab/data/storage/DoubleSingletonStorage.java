//package org.integratedmodelling.klab.data.storage;
//
//import org.integratedmodelling.klab.api.data.IGeometry;
//import org.integratedmodelling.klab.api.data.classification.IDataKey;
//import org.integratedmodelling.klab.api.knowledge.IConcept;
//import org.integratedmodelling.klab.api.knowledge.IObservable;
//import org.integratedmodelling.klab.scale.Scale;
//import org.integratedmodelling.klab.utils.Utils;
//
//public class DoubleSingletonStorage extends AbstractSingletonStorage<Double> {
//
//	public DoubleSingletonStorage(IObservable observable, Scale scale) {
//		super(observable, scale);
//	}
//
//	@Override
//	protected Double setValue(Object value) {
//		// TODO Auto-generated method stub
//		return value instanceof Number ? ((Number) value).doubleValue() : convert(value);
//	}
//
//	private double convert(Object value) {
//		// TODO distribution, promotion
//		return Double.NaN;
//	}
//
//	@Override
//	public Type getType() {
//		return Type.NUMBER;
//	}
//
//	@Override
//	public IDataKey getDataKey() {
//		return null;
//	}
//    @SuppressWarnings("unchecked")
//    @Override
//    public <T> T aggregate(IGeometry geometry, Class<? extends T> cls) {
//        if (!Number.class.isAssignableFrom(cls)) {
//            throw new IllegalArgumentException("cannot return a numeric state as a " + cls);
//        }
//        return (T)Utils.asType(value, cls);
//    }
//}
