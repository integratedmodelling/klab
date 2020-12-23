package org.integratedmodelling.klab.stats.services;

import org.integratedmodelling.klab.rest.StatsInstertResponse;
import org.integratedmodelling.klab.stats.api.models.StatsInsertRequest;

public interface StatsService {
	<T> StatsInstertResponse<T> insertRequest(StatsInsertRequest<T> request);

}
