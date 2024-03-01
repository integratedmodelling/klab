package org.integratedmodelling.klab.hub.repository;

import java.util.Optional;

import org.integratedmodelling.klab.hub.agreements.dto.Agreement;
import org.springframework.stereotype.Repository;

@Repository
public interface AgreementRepository  extends ResourceRepository<Agreement, String>{

    Optional<Agreement> findById(String id);


}
