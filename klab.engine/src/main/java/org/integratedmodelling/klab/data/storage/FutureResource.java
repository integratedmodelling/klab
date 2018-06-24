package org.integratedmodelling.klab.data.storage;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IRuntimeProvider;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

/**
 * The k.LAB resource is identified by a URN. A URN is resolved (using the
 * <code>resolve</code> API call) to a IResource; the IResource can then be
 * contextualized to a {@link IGeometry} (using the <code>get</code> API call)
 * to produce the corresponding {@link IKlabData} that will be used to build
 * {@link IArtifact artifacts}.
 * 
 * When a URN is referenced in k.IM, it is turned into a
 * {@link IComputableResource} which is passed to the {@link IRuntimeProvider
 * runtime} and turned into a KDL function call or literal, which encodes their
 * computation or resolution. Executing the KDL call as part of a
 * {@link IDataflow} builds the {@link IArtifact}.
 * 
 * @author Ferd
 *
 */
public class FutureResource implements IResource, Future<IResource> {

	private static final long serialVersionUID = -923039635832182164L;

	private String urn;
	private IResource delegate;
	private long timeout = 1000;
	private IMonitor monitor;

	public FutureResource(String urn, IMonitor monitor) {
		this.urn = urn;
		this.monitor = monitor;
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

	public List<IResource> getHistory() {
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

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IResource get() throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResource get(long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getLocalPaths() {
		return getDelegate(timeout).getLocalPaths();
	}

	@Override
	public String getLocalPath() {
		return getDelegate(timeout).getLocalPath();
	}

	@Override
	public Type getType() {
		return getDelegate(timeout).getType();
	}

}
