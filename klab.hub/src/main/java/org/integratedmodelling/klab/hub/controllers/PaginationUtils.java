package org.integratedmodelling.klab.hub.controllers;

import java.util.Optional;

public class PaginationUtils {
    private static boolean hasValidPaginationValues(Integer page, Integer records) {
        return (page >= 0) && (records > 0);
    }
    
    public static boolean hasValidPaginationParameters(Optional<Integer> page, Optional<Integer> records) {
        return page.isPresent() && records.isPresent() && hasValidPaginationValues(page.get(), records.get());
    }
}
