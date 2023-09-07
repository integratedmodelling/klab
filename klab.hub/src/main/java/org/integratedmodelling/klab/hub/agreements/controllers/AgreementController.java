package org.integratedmodelling.klab.hub.agreements.controllers;

import org.apache.commons.lang3.tuple.Pair;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.agreements.services.AgreementService;
import org.integratedmodelling.klab.hub.api.Agreement;
import org.integratedmodelling.klab.hub.controllers.pagination.GenericPageAndFilterConverter;
import org.integratedmodelling.klab.hub.enums.FilterOperationEnum;
import org.integratedmodelling.klab.hub.payload.PageRequest;
import org.integratedmodelling.klab.hub.payload.PageResponse;
import org.integratedmodelling.klab.hub.service.FilterBuilderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.shaded.json.JSONObject;

@RestController
public class AgreementController {
    
    
    private final AgreementService agreementService;    
    private final GenericPageAndFilterConverter genericPageAndFilterConverter;
    
    @Autowired
    public AgreementController(AgreementService agreementService, GenericPageAndFilterConverter genericPageAndFilterConverter) {
        super();
        this.agreementService = agreementService;        
        this.genericPageAndFilterConverter = genericPageAndFilterConverter;
    }
    
    @GetMapping(API.HUB.GET_AGREEMENT_ID)
    public ResponseEntity<?> getAgreement(@PathVariable String id) {
        
        JSONObject agreement = new JSONObject().appendField("agreement", agreementService.getAgreement(id));
        return new ResponseEntity<>(agreement, HttpStatus.OK);        
    }
    
    /**
     * 
     * Function for get agreements with pagination and filter management in back-end
     * 
     * Following documentation: https://dzone.com/articles/advanced-search-amp-filtering-api-using-spring-dat
     * 
     * FilterBuilderService: In this class is the algorithm to treat filter, if something need to change, change in this class.
     *  {@link FilterBuilderService}
     * 
     * FilterOperationEnum: Enum with all the possibilities of filter (eq, gt, notLike) {@link FilterOperationEnum}
     * 
     * 
     * @param pageRequest Object with page (int), size (int), filterAnd (String like agreementLevel|eq|PROFIT&agreementType|eq|USER), 
     *        filterOr (String as filterAnd), orders (String like agreementLevel|ASC)     *        
     *        !!!This object must be codify, if in the FRONT_END in the GET function you use params attribute, it will be automatic conversion
     *           See the example in loadAgreementTemplate actions!!!
     *        
     * @return ResponseEntity
     * 
     */
    @GetMapping(API.HUB.GET_AGREEMENT)
    public ResponseEntity<?> getAgreements(PageRequest pageRequest) {
        
        PageResponse<Agreement> response = new PageResponse<>();
        
        /* Call function to convert pageRequest object in <Query, Pageable> pair, where query has the filters and pageable, the pagination properties*/
        Pair<Query, Pageable> pair= genericPageAndFilterConverter.genericPageAndFilterConvert(pageRequest);
        
        /* Call getPage function, to findAll elements applying the filters and the pagination given in the pageRequest*/
        Page<Agreement> pg = agreementService.getPage(pair.getLeft(), pair.getRight());        
        
        response.setPageStats(pg);
        
        return new ResponseEntity<>(response, HttpStatus.OK);        
    }
    

}
