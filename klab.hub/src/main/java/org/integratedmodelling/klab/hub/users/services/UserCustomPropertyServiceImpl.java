package org.integratedmodelling.klab.hub.users.services;

import java.util.Collection;
import java.util.Optional;
import org.integratedmodelling.klab.hub.api.CustomProperties;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.exception.UserDoesNotExistException;
import org.integratedmodelling.klab.hub.repository.CustomPropertiesRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.users.requests.UserCustomPropertyRequest;
import org.integratedmodelling.klab.rest.CustomProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCustomPropertyServiceImpl implements UserCustomPropertyService {
    private UserRepository userRepository;
    private CustomPropertiesRepository customPropertiesRepository;

    @Autowired
    public UserCustomPropertyServiceImpl(UserRepository userRepository, CustomPropertiesRepository customPropertiesRepository) {
        super();
        this.userRepository = userRepository;
        this.customPropertiesRepository = customPropertiesRepository;
    }

    public Collection<CustomProperty> getAllUserCustomProperties(String username) {
        User user = userRepository.findByNameIgnoreCase(username)
                .orElseThrow(() -> new UserDoesNotExistException(username));
        return user.getCustomProperties();
    }

    public void addUserCustomProperties(UserCustomPropertyRequest request) {
        final String key = request.getKey();
        if (key == null ||  request.getValue() == null) {
            throw new BadRequestException("Malformed custom property information.");
        }
        
        Collection<User> users = userRepository.findByNameInIgnoreCase(request.getUsernames());
        if (users.isEmpty()) {
            throw new UserDoesNotExistException();
        }

        CustomProperty property = new CustomProperty(key, request.getValue(), request.isOnlyAdmin());
        users.stream().forEach(u -> u.putCustomProperty(property));
        userRepository.saveAll(users);

        Optional<CustomProperties> globalCustomProperties = customPropertiesRepository.findByName(key);
        if (globalCustomProperties.isEmpty()) {
            globalCustomProperties = Optional.of(new CustomProperties(key));
        }
        globalCustomProperties.get().setForUser(true);

        customPropertiesRepository.save(globalCustomProperties.get());
    }

    public void deleteUserCustomCustomProperties(UserCustomPropertyRequest request) {
        Collection<User> users = userRepository.findByNameInIgnoreCase(request.getUsernames());
        if (users.isEmpty()) {
            throw new UserDoesNotExistException();
        }

        users.stream().forEach(u -> u.removeCustomProperty(request.getKey()));
        userRepository.saveAll(users);
    }
}
