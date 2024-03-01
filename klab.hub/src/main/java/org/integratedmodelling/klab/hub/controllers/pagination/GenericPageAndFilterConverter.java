package org.integratedmodelling.klab.hub.controllers.pagination;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.integratedmodelling.klab.hub.controllers.dto.FilterCondition;
import org.integratedmodelling.klab.hub.payload.PageRequest;
import org.integratedmodelling.klab.hub.repository.support.GenericFilterCriteriaBuilder;
import org.integratedmodelling.klab.hub.service.FilterBuilderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


@Service
public class GenericPageAndFilterConverter {
    
    @Autowired
    private FilterBuilderService filterBuilderService;
    
    public Pair<Query, Pageable> genericPageAndFilterConvert(PageRequest pageRequest) {
        Pageable pageable = filterBuilderService.getPageable(pageRequest.getSize(), pageRequest.getPage(), pageRequest.getOrders());
        GenericFilterCriteriaBuilder filterCriteriaBuilder = new GenericFilterCriteriaBuilder();


        List<FilterCondition> andConditions = filterBuilderService.createFilterCondition(pageRequest.getFilterAnd());
        List<FilterCondition> orConditions = filterBuilderService.createFilterCondition(pageRequest.getFilterOr());

        Query query = filterCriteriaBuilder.addCondition(andConditions, orConditions);
        
        return Pair.of(query, pageable);
    }
    
    public Triple<Query, List<FilterCondition>, List<FilterCondition>> genericFilterConvert(PageRequest pageRequest, String... skipped) {
        GenericFilterCriteriaBuilder filterCriteriaBuilder = new GenericFilterCriteriaBuilder();

        Pair<List<FilterCondition>, List<FilterCondition>> andConditions = filterBuilderService.createFilterConditionWithSkipped(pageRequest.getFilterAnd(), skipped);
        Pair<List<FilterCondition>, List<FilterCondition>> orConditions = filterBuilderService.createFilterConditionWithSkipped(pageRequest.getFilterOr(), skipped);

        Query query = filterCriteriaBuilder.addCondition(andConditions.getLeft(), orConditions.getLeft());
        
        return Triple.of(query, andConditions.getRight(), orConditions.getRight());
    }
    
    

}
