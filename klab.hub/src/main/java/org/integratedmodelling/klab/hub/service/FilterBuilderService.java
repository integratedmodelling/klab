package org.integratedmodelling.klab.hub.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;
import org.integratedmodelling.klab.hub.controllers.dto.FilterCondition;
import org.integratedmodelling.klab.hub.enums.FilterOperationEnum;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Service for Filtering Page
 * This class is used to extract any filters requested by the client.
 */
@Service
public class FilterBuilderService {

    private static final int DEFAULT_SIZE_PAGE = 20;
    private final String FILTER_SHEARCH_DELIMITER = "&";
    private final String FILTER_CONDITION_DELIMITER = "\\|";


    /**
     * Prepare filter condition.  extract the different filters used in the controller via @RequestParam
     *
     * @param criteria search Criteria.
     * @return a list of {@link FilterCondition}
     */
    public List<FilterCondition> createFilterCondition(String criteria) {
        List<FilterCondition> filters = new ArrayList<>();

        try {

            if (criteria != null && !criteria.isEmpty()) {

                List<String> values = split(criteria, FILTER_SHEARCH_DELIMITER);
                if (!values.isEmpty()) {
                    values.forEach(x -> {
                        List<String> filter = split(x, FILTER_CONDITION_DELIMITER);
                        if (FilterOperationEnum.fromValue(filter.get(1)) != null) {
                            filters.add(new FilterCondition(filter.get(0), FilterOperationEnum.fromValue(filter.get(1)), getValue(filter.get(2))));
                        }
                    });
                }
            }

            return filters;

        } catch (Exception ex) {
            throw new BadRequestException("Cannot create condition filter " + ex.getMessage());
        }

    }
    
    /**
     * Prepare filter condition.  extract the different filters used in the controller via @RequestParam
     *
     * @param criteria search Criteria.
     * @return a list of {@link FilterCondition}
     */
    public Pair<List<FilterCondition>, List<FilterCondition>> createFilterConditionWithSkipped(String criteria, String... skipped) {
        List<FilterCondition> filters = new ArrayList<>();
        List<FilterCondition> skippedFilters = new ArrayList<>();
        
        if (skipped == null || skipped.length == 0) {
            return Pair.of(createFilterCondition(criteria),skippedFilters);
        } else {
            try {
                if (criteria != null && !criteria.isEmpty()) {
                    List<String> values = split(criteria, FILTER_SHEARCH_DELIMITER);
                    if (!values.isEmpty()) {
                        values.forEach(x -> {
                            List<String> filter = split(x, FILTER_CONDITION_DELIMITER);
                            if (FilterOperationEnum.fromValue(filter.get(1)) != null) {
                                if (Arrays.stream(skipped).anyMatch(filter.get(0)::equals)) {
                                    skippedFilters.add(new FilterCondition(filter.get(0), FilterOperationEnum.fromValue(filter.get(1)), getValue(filter.get(2))));
                                } else {
                                    filters.add(new FilterCondition(filter.get(0), FilterOperationEnum.fromValue(filter.get(1)), getValue(filter.get(2))));
                                }
                            }
                        });
                    }
                }
                return Pair.of(filters,skippedFilters);
            } catch (Exception ex) {
                throw new BadRequestException("Cannot create condition filter " + ex.getMessage());
            }
        }
    }


    private static List<String> split(String search, String delimiter) {
        return Stream.of(search.split(delimiter))
                .collect(Collectors.toList());
    }
    
    private static Object getValue(String sValue) {
        Object value;
        if (sValue != null) {
            if (sValue.startsWith("$DATE$")) {
                value = LocalDate.parse(sValue.substring(6));
            } else {
                value = sValue;
            }
        } else {
            return null;
        }
        return value;
    }


    /**
     * Get request pageable. Page Request Builder. custom pageable
     *
     * @param size  the number of items to collect
     * @param page  page number
     * @param order search order filter (eg: field|ASC)
     * @return PageRequest
     */
    public PageRequest getPageable(int size, int page, String order) {

        int pageSize = (size <= 0) ? DEFAULT_SIZE_PAGE : size;
        int currentPage = (page <= 0) ? 1 : page;

        try {
            if (order != null && !order.isEmpty()) {

                final String FILTER_CONDITION_DELIMITER = "\\|";

                List<String> values = split(order, FILTER_CONDITION_DELIMITER);
                String column = values.get(0);
                String sortDirection = values.get(1);

                if (sortDirection.equalsIgnoreCase("ASC")) {
                    return PageRequest.of((currentPage - 1), pageSize, Sort.by(Sort.Direction.ASC, column));
                } else if (sortDirection.equalsIgnoreCase("DESC")) {
                    return PageRequest.of((currentPage - 1), pageSize, Sort.by(Sort.Direction.DESC, column));
                } else {
                    throw new IllegalArgumentException(String.format("Value for param 'order' is not valid : %s , must be 'asc' or 'desc'", sortDirection));
                }

            } else {
                return PageRequest.of((currentPage - 1), pageSize);
            }
        } catch (Exception ex) {
            throw new BadRequestException("Cannot create condition filter " + ex.getMessage());
        }
    }


}