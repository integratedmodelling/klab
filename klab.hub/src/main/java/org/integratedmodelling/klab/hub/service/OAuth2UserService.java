//package org.integratedmodelling.klab.hub.service;
//
//import org.integratedmodelling.klab.hub.security.oauth2.OAuth2UserInfo;
//import org.integratedmodelling.klab.hub.security.oauth2.OAuth2UserInfoFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.InternalAuthenticationServiceException;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.util.HashSet;
//import java.util.Optional;
//import java.util.Set;
//
//import org.apache.commons.lang3.StringUtils;
//import org.integratedmodelling.klab.hub.api.AuthProvider;
//import org.integratedmodelling.klab.hub.api.ProfileResource;
//import org.integratedmodelling.klab.hub.api.Role;
//import org.integratedmodelling.klab.hub.api.User;
//import org.integratedmodelling.klab.hub.api.User.AccountStatus;
//import org.integratedmodelling.klab.hub.commands.CreateUserWithRolesAndStatus;
//import org.integratedmodelling.klab.hub.exception.OAuth2AuthenticationProcessingException;
//import org.integratedmodelling.klab.hub.repository.UserRepository;
//
//@Service
//public class OAuth2UserService extends DefaultOAuth2UserService {
//	
//	@Autowired
//	UserRepository userRepository;
//	
//    @Autowired
//    protected ObjectMapper objectMapper;
//	
//	@Override
//	public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
//		OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
//		try {
//			return processOAuth2User(oAuth2UserRequest, oAuth2User);
//		} catch (AuthenticationException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
//		}
//	}
//
//	private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
//		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
//        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
//            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
//        }
//        //lets check to see if the user exists in our database, we may have a problem with duplicated email addresses
//        //how can we respond to an email check for example when an engine and a user are both using the same email address? who do I match?
//        //We need to indicate where this verification comes from, ldap, google, or whatever.  
//        Optional<User> userOptional = userRepository
//        				.findByNameIgnoreCaseOrEmailIgnoreCase(oAuth2UserInfo.getName(), oAuth2UserInfo.getEmail());
//        User user;
//        if(userOptional.isPresent()) {
//        	user = userOptional.get();
//        	if(!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
//        		throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
//                    user.getProvider() + " account. Please use your " + user.getProvider() +
//                    " account to login.");
//        	}
//        	user = updateExistingUser(user, oAuth2UserInfo);
//        } else {
//        	user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
//        }
//        
//        ProfileResource profielResource = objectMapper.convertValue(user, ProfileResource.class);
//        profielResource.setAttributes(oAuth2User.getAttributes());
//        return profielResource;
//	}
//	
//    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
//    	Set<Role> roles = new HashSet<>();
//    	roles.add(Role.ROLE_USER);
//        User user = new User();
//        user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
//        user.setProviderId(oAuth2UserInfo.getId());
//        user.setFirstName(oAuth2UserInfo.getName());
//        user.setEmail(oAuth2UserInfo.getEmail());
//        user.setUsername(oAuth2UserInfo.getEmail());
//        user.setRoles(roles);
//        user.setAccountStatus(AccountStatus.active);
//        user = new CreateUserWithRolesAndStatus(user, userRepository, null).execute();
//        return user;
//    }
//    
//    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
//    	//TODO need some user update system for when if oauth info changes
//    	return null;
//    }
//}
