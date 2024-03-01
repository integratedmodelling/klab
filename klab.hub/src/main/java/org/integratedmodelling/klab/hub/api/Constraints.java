package org.integratedmodelling.klab.hub.api;

public interface Constraints {

    /**
     * allows alphanumeric, dot-separated usernames of up to 24 characters per "word".
     */
    String USERNAME_PATTERN = "^[A-Za-z0-9]{3,24}((\\.)[A-Za-z0-9]{2,24})*$";

    String DATASOURCE_NAME_PATTERN = "^[-_A-Za-z0-9]*$";

    // NOTE: don't use ^...$ because it needs to be inserted into another regex
    String POSTGRESQL_TABLE_NAME_PATTERN = "[_A-Za-z0-9]+";

    /**
     * These are to be used in *cleansing* rather than *validation*. We should silently rename,
     * rather than complaining to the user about stuff they don't care about (and we should definitely
     * not tell them to rename their files).
     */
    String FILENAME_ILLEGAL_CHARACTERS = "[^-_.A-Za-z0-9]";

    int USERNAME_LENGTH = 50;
    
    String EMAIL_PATTERN = "^(([^<>()[\\]\\\\.,;:\\s@\"]+(\\.[^<>()[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

}