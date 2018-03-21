package org.integratedmodelling.klab.components.runtime.observations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.raw.IObservationData;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.components.runtime.RuntimeContext;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.utils.NameGenerator;

/**
 * Simple abstract observation data class for storage components. Just implements the basic iterator
 * functions and access to simple final fields.
 * 
 * @author Ferd
 *
 */
public abstract class ObservationData implements IObservationData {

  private IGeometry      geometry;
  private RuntimeContext runtimeContext;
  IMetadata              metadata = new Metadata();
  private String         token    = "o" + NameGenerator.shortUUID();

  // all observation data in a group share the same list and contain their index in it; established
  // at chain()
  List<IObservationData> group    = null;
  // first observation in a group has idx = -1; the others have their own index
  int                    idx      = -1;

  protected ObservationData(IGeometry geometry, RuntimeContext context) {
    this.geometry = geometry;
    this.runtimeContext = context;
  }

  public String getId() {
    return token;
  }

  @Override
  public IGeometry getGeometry() {
    return geometry;
  }

  @Override
  public IMetadata getMetadata() {
    return metadata;
  }

  public IRuntimeContext getRuntimeContext() {
    return this.runtimeContext;
  }

  public void chain(IObservationData data) {
    if (group == null) {
      group = new ArrayList<>();
    }
    group.add(data);
    ((ObservationData) data).group = group;
    ((ObservationData) data).idx = group.size() - 1;
  }

  public boolean hasNext() {
    return group != null && group.size() > idx;
  }

  public ObservationData next() {
    if (!hasNext()) {
      throw new NoSuchElementException(
          "ObservationData.next() called when hasNext() returns false");
    }
    return (ObservationData) group.get(idx + 1);
  }

  public ObservationData getParent() {
    // TODO Auto-generated method stub USE THE GRAPH IN THE CONTEXT
    return null;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((token == null) ? 0 : token.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ObservationData other = (ObservationData) obj;
    if (token == null) {
      if (other.token != null)
        return false;
    } else if (!token.equals(other.token))
      return false;
    return true;
  }

}
