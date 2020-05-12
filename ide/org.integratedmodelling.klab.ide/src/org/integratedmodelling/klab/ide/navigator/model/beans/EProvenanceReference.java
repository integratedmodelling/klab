//package org.integratedmodelling.klab.ide.navigator.model.beans;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.logging.Level;
//
//import org.integratedmodelling.klab.api.runtime.rest.IProvenanceReference;
//import org.integratedmodelling.klab.ide.Activator;
//import org.integratedmodelling.klab.utils.Pair;
//
//public class EProvenanceReference implements IProvenanceReference, ERuntimeObject {
//
//	String parentTaskId;
//    String parentArtifactId;
//	String id;
//    
//	public EProvenanceReference(String parentTaskId, String parentArtifactId, String id) {
//	    this.parentTaskId = parentTaskId;
//        this.parentArtifactId = parentArtifactId;
//	    this.id = id;
//	}
//	
//	@Override
//	public ERuntimeObject getEParent(DisplayPriority priority) {
//        return priority == DisplayPriority.ARTIFACTS_FIRST
//                ? Activator.session().getObservation(parentArtifactId)
//                : Activator.session().getTask(parentTaskId);
//	}
//
//	@Override
//	public ERuntimeObject[] getEChildren(DisplayPriority priority, Level level) {
//		return new ERuntimeObject[] {};
//	}
//
//    @Override
//    public List<Pair<String, String>> getProperties() {
//        List<Pair<String,String>> ret = new ArrayList<>();
//        return ret;
//    }
//    
//    @Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + ((id == null) ? 0 : id.hashCode());
//        return result;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (obj == null)
//            return false;
//        if (getClass() != obj.getClass())
//            return false;
//        EProvenanceReference other = (EProvenanceReference) obj;
//        if (id == null) {
//            if (other.id != null)
//                return false;
//        } else if (!id.equals(other.id))
//            return false;
//        return true;
//    }
//
//}
