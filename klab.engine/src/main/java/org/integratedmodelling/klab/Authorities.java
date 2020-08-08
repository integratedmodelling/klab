package org.integratedmodelling.klab;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.extensions.Authority;
import org.integratedmodelling.klab.api.knowledge.IAuthority;
import org.integratedmodelling.klab.api.services.IAuthorityService;
import org.integratedmodelling.klab.rest.AuthorityIdentity;
import org.integratedmodelling.klab.utils.Path;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public enum Authorities implements IAuthorityService {

	INSTANCE;

	Map<String, IAuthority> authorities = Collections.synchronizedMap(new HashMap<>());
	Map<String, IAuthority> inactive = Collections.synchronizedMap(new HashMap<>());

	private Authorities() {

		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AnnotationTypeFilter(Authority.class));

		Set<BeanDefinition> beans = provider.findCandidateComponents("org.integratedmodelling");
		for (BeanDefinition bd : beans) {
			try {
				Class<?> cls = Class.forName(bd.getBeanClassName());
				Authority annotation = cls.getAnnotation(Authority.class);
				if (annotation != null) {
					createAuthority(annotation, cls);
				}
			} catch (ClassNotFoundException e) {
				Logging.INSTANCE.error(e);
				continue;
			}
		}
		Services.INSTANCE.registerService(this, IAuthorityService.class);
	}

	private void createAuthority(Authority annotation, Class<?> cls) {
		if (IAuthority.class.isAssignableFrom(cls)) {
			try {
				// TODO put away descriptors with label, description and catalogs
				IAuthority authority = (IAuthority) cls.newInstance();
				authorities.put(annotation.id(), authority);
			} catch (Throwable e) {
				Logging.INSTANCE.error("Error creating authority " + annotation.id() + ": " + e.getMessage());
			}
		} else {
			Logging.INSTANCE
					.error("Authority annotation " + annotation.id() + " used with non-authority class: ignoring");
		}
	}

	@Override
	public Collection<IAuthority> getAuthorities() {
		return authorities.values();
	}

	@Override
	public IAuthority getAuthority(String authorityId) {
		return authorities.get(authorityId);
	}

	@Override
	public IAuthority.Identity getIdentity(String authorityId, String identityId) {

		String auth = authorityId;
		String catalog = null;

		if (auth.contains(".")) {
			auth = Path.getFirst(authorityId, ".");
			catalog = Path.getRemainder(authorityId, ".");
		}

		/*
		 * if we have the authority locally, use that
		 */
		IAuthority.Identity ret = null;
		if (authorities.containsKey(auth)) {
			ret = authorities.get(auth).getIdentity(identityId, catalog);
		}

		/*
		 * Lookup a service on the network and use the first that responds.
		 */
		if (ret == null) {
			for (INodeIdentity node : Network.INSTANCE.getNodesForAuthority(auth)) {
				ret = node.getClient().get(API.AUTHORITY.RESOLVE, AuthorityIdentity.class, API.AUTHORITY.P_AUTHORITY,
						auth, API.AUTHORITY.P_IDENTIFIER, identityId);
				if (ret != null) {
					break;
				}
			}
		}

		return ret;
	}

	@Override
	public void deactivateAuthority(String authority) {
		IAuthority auth = authorities.remove(authority);
		if (auth != null) {
			inactive.put(authority, auth);
		}
	}

}
