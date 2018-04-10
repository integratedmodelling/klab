package org.integratedmodelling.klab.data.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.INotification;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.kim.utils.Parameters;
import org.integratedmodelling.kim.validation.KimNotification;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IRuntimeProvider;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * The k.LAB resource is identified by a URN. A URN is resolved (using the <code>resolve</code> API
 * call) to a IResource; the IResource can then be contextualized to a {@link IGeometry} (using the
 * <code>get</code> API call) to produce the corresponding {@link IKlabData} that will be used to
 * build {@link IArtifact artifacts}.
 * 
 * When a URN is referenced in k.IM, it is turned into a {@link IComputableResource} which is passed
 * to the {@link IRuntimeProvider runtime} and turned into a KDL function call or literal, which
 * encodes their computation or resolution. Executing the KDL call as part of a {@link IDataflow}
 * builds the {@link IArtifact}.
 * 
 * @author Ferd
 *
 */
@JsonSerialize(using = ResourceSerializer.class)
@JsonDeserialize(using = ResourceDeserializer.class)
public class Resource implements IResource {

  private static final long serialVersionUID = -923039635832182164L;

  Version                   version;
  IMetadata                 metadata;
  String                    urn;
  String                    adapterType;
  IGeometry                 geometry;
  Parameters                parameters;
  long                      resourceTimestamp;
  List<INotification>       history          = new ArrayList<>();
  List<INotification>       notifications    = new ArrayList<>();

  // only meant to be built by the custom deserializer in this package
  Resource() {}

  /**
   * Create a resource with the passed URN and a list of errors.
   * 
   * @param urn
   * @param errors
   * @return
   */
  public static Resource error(String urn, List<Throwable> errors) {
    Resource ret = new Resource();
    ret.urn = urn;
    for (Throwable t : errors) {
      ret.notifications.add(new KimNotification(t.getMessage(), Level.SEVERE));
    }
    return ret;
  }

  @Override
  public Version getVersion() {
    return version;
  }

  @Override
  public IMetadata getMetadata() {
    return metadata;
  }

  @Override
  public String getUrn() {
    return urn;
  }

  @Override
  public List<INotification> getHistory() {
    return history;
  }

  @Override
  public IGeometry getGeometry() {
    return geometry;
  }

  @Override
  public IParameters getParameters() {
    return parameters;
  }

  @Override
  public String getAdapterType() {
    return adapterType;
  }

  public long getResourceTimestamp() {
    return resourceTimestamp;
  }

  @Override
  public boolean hasErrors() {
    if (notifications != null) {
      for (INotification notification : notifications) {
        if (notification.getLevel() == Level.SEVERE) {
          return true;
        }
      }
    }
    return false;
  }

}
