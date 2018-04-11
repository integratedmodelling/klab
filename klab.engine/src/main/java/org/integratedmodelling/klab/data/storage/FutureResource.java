package org.integratedmodelling.klab.data.storage;

import java.util.List;
import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.INotification;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IRuntimeProvider;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;

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
public class FutureResource implements IResource {

  private static final long serialVersionUID = -923039635832182164L;
  
  private String urn;
  private IResource delegate;
  private long timeout = 1000;
  
  public FutureResource(String urn) {
    
  }

  private IResource getDelegate(long timeout) {
    if (delegate == null) {
      while (!Resources.INSTANCE.getLocalResourceCatalog().containsKey(urn)) {
        try {
          Thread.sleep(timeout);
        } catch (InterruptedException e) {
          // boh
        }
      }
      this.delegate = Resources.INSTANCE.getLocalResourceCatalog().get(urn);
    }
    return delegate;
  }
  
  public String getUrn() {
    return getDelegate(timeout).getUrn();
  }

  public IGeometry getGeometry() {
    return getDelegate(timeout).getGeometry();
  }

  public Version getVersion() {
    return getDelegate(timeout).getVersion();
  }

  public String getAdapterType() {
    return getDelegate(timeout).getAdapterType();
  }

  public IMetadata getMetadata() {
    return getDelegate(timeout).getMetadata();
  }

  public List<INotification> getHistory() {
    return getDelegate(timeout).getHistory();
  }

  public IParameters getParameters() {
    return getDelegate(timeout).getParameters();
  }

  public long getResourceTimestamp() {
    return getDelegate(timeout).getResourceTimestamp();
  }

  @Override
  public boolean hasErrors() {
    return getDelegate(timeout).hasErrors();
  }


}
