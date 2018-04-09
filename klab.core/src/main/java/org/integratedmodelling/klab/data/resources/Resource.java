package org.integratedmodelling.klab.data.resources;

import java.util.ArrayList;
import java.util.List;
import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.kim.utils.Parameters;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IRuntimeProvider;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.INotification;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

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
@JsonDeserialize(using = ResourceDeserializer.class)
public class Resource implements IResource {

  private static final long serialVersionUID = -923039635832182164L;

  Version                   version;
  IMetadata                 metadata;
  String                    urn;
  String                    type;
  IGeometry                 geometry;
  Parameters                parameters;
  List<INotification>       history          = new ArrayList<>();

  // only meant to be built by the custom deserializer in this package
  Resource() {}

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
    return type;
  }

}
