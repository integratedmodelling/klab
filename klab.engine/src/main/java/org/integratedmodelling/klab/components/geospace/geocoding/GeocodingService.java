package org.integratedmodelling.klab.components.geospace.geocoding;

import java.util.concurrent.ExecutionException;

import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;

public abstract class GeocodingService {

	private RateLimiter rateLimiter;
	LoadingCache<String, IShape> cache;
	
	protected GeocodingService(double maxCallsPerSecond) {
		this.rateLimiter = RateLimiter.create(maxCallsPerSecond);
		cache = CacheBuilder.newBuilder().maximumSize(16).build(new CacheLoader<String, IShape>() {
			@Override
			public IShape load(String key) throws Exception {
				return null;
			}});
	}

	public IShape geocode(IEnvelope envelope, IMonitor monitor) {
		try {
			return cache.get(envelope.toString(), () -> getAnnotatedRegion(envelope, monitor));
		} catch (ExecutionException e) {
			// null was returned, nothing was cached
		}
		return null;
	}
	
	/**
	 * Convert a Lat/Lon envelope into a shape if possible. The returned shape must
	 * have the {@link IMetadata#DC_DESCRIPTION} field set in the metadata.
	 * 
	 * @param envelope
	 * @return
	 */
	public abstract IShape getAnnotatedRegion(IEnvelope envelope, IMonitor monitor);

	/**
	 * Maximum number of calls per second allowed. Values < 0 will select >1 second
	 * intervals.
	 * 
	 * @return
	 */
	public RateLimiter getRateLimiter() {
		return this.rateLimiter;
	}

}
