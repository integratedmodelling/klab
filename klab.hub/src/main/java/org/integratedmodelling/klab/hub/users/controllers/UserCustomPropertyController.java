package org.integratedmodelling.klab.hub.users.controllers;

import java.util.Collection;

import org.integratedmodelling.klab.hub.users.services.UserCustomPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserCustomPropertyController {

    private UserCustomPropertyService userCustomPropertyService;

    @Autowired
    public UserCustomPropertyController(UserCustomPropertyService userCustomPropertyService) {
        super();
        this.userCustomPropertyService = userCustomPropertyService;
    }

    public class UserCustomPropertyRequest {
        private Collection<String> usernames;
        private String key;
        private String value;
        private boolean onlyAdmin;

        public UserCustomPropertyRequest() {}

        public UserCustomPropertyRequest(Collection<String> usernames, String key, String value, boolean onlyAdmin) {
            this.usernames = usernames;
            this.key = key;
            this.value = value;
            this.onlyAdmin = onlyAdmin;
        }

        public UserCustomPropertyRequest(Collection<String> usernames, String key) {
            this.usernames = usernames;
            this.key = key;
        }

        public Collection<String> getUsernames() {
            return usernames;
        }

        public void setUsernames(Collection<String> usernames) {
            this.usernames = usernames;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public boolean isOnlyAdmin() {
            return onlyAdmin;
        }

        public void setOnlyAdmin(boolean onlyAdmin) {
            this.onlyAdmin = onlyAdmin;
        }
    }
}
