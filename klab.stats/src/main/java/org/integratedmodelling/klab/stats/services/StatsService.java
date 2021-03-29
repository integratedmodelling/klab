package org.integratedmodelling.klab.stats.services;

import org.integratedmodelling.klab.rest.StatsInstertResponse;
import org.integratedmodelling.klab.stats.api.models.StatsFindPageRequest;
import org.integratedmodelling.klab.stats.api.models.StatsFindPageResponse;
import org.integratedmodelling.klab.stats.api.models.StatsInsertRequest;

public interface StatsService {
	<T> StatsInstertResponse<T> insertRequest(StatsInsertRequest<T> request);
	<T> StatsFindPageResponse<T> findRequest(StatsFindPageRequest<T> request);

}
