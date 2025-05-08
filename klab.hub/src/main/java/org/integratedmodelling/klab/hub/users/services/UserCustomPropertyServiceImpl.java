package org.integratedmodelling.klab.hub.users.services;

import java.util.Collection;
import java.util.Optional;

import org.integratedmodelling.klab.hub.customProperties.dto.CustomProperty;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.repository.CustomPropertyRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.integratedmodelling.klab.hub.users.exceptions.UserDoesNotExistException;
import org.integratedmodelling.klab.hub.users.requests.UserCustomPropertyRequest;
import org.integratedmodelling.klab.rest.CustomPropertyRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCustomPropertyServiceImpl implements UserCustomPropertyService {
    private UserRepository userRepository;
    private CustomPropertyRepository customPropertyRepository;

    @Autowired
    public UserCustomPropertyServiceImpl(UserRepository userRepository, CustomPropertyRepository customPropertyRepository) {
        super();
        this.userRepository = userRepository;
        this.customPropertyRepository = customPropertyRepository;
    }

    public Collection<CustomPropertyRest> getAllUserCustomProperties(String username) {
        User user = userRepository.findByNameIgnoreCase(username)
                .orElseThrow(() -> new UserDoesNotExistException(username));
        return user.getCustomProperties();
    }

    public void addUserCustomProperties(UserCustomPropertyRequest request) {
        final String key = request.getKey();
        if (key == null || request.getValue() == null) {
            throw new BadRequestException("Malformed custom property information.");
        }

        Collection<User> users = userRepository.findByNameInIgnoreCase(request.getUsernames());
        if (users.isEmpty()) {
            throw new UserDoesNotExistException();
        }

        CustomPropertyRest property = new CustomPropertyRest(key, request.getValue(), request.isOnlyAdmin());
        users.stream().forEach(u -> u.putCustomProperty(property));
        userRepository.saveAll(users);

        Optional<CustomProperty> recordedCustomProperty = customPropertyRepository.findByName(key);
        if (recordedCustomProperty.isEmpty()) {
            recordedCustomProperty = Optional.of(new CustomProperty(key));
        }
        recordedCustomProperty.get().setForUser(true);

        customPropertyRepository.save(recordedCustomProperty.get());
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
