package org.integratedmodelling.klab.hub.agreements.services;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.hub.agreements.dto.Agreement;
import org.integratedmodelling.klab.hub.enums.AgreementLevel;
import org.integratedmodelling.klab.hub.enums.AgreementType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public abstract interface AgreementService {
    
    abstract Agreement getAgreement(String id);

    List<Agreement> createAgreement(AgreementType agreementType, AgreementLevel agreementLevel);

    Set<Agreement> updateAgreementValidDate(Set<Agreement> agreements, Date validDate);

    List<Agreement> updateAgreement(Agreement agreement);
    
    /**
     * @param query custom query
     * @return list of Agreement
     */
    List<Agreement> getAll(Query query);
    
    
    /**
     * Get all custom paginate data for entity Agreement
     *
     * @param query    custom query
     * @param pageable pageable param
     * @return Page of entity Agreement
     */
    Page<Agreement> getPage(Query query, Pageable pageable);



}
