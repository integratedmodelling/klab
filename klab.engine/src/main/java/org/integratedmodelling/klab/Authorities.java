package org.integratedmodelling.klab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.extensions.Authority;
import org.integratedmodelling.klab.api.knowledge.IAuthority;
import org.integratedmodelling.klab.api.knowledge.ICodelist;
import org.integratedmodelling.klab.api.services.IAuthorityService;
import org.integratedmodelling.klab.data.resources.AgencyAuthority;
import org.integratedmodelling.klab.data.resources.ResourceAuthority;
import org.integratedmodelling.klab.rest.AuthorityIdentity;
import org.integratedmodelling.klab.rest.AuthorityReference;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Path;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public enum Authorities implements IAuthorityService {

	INSTANCE;

	private class AuthorityDescriptor {
		public IAuthority authority;
		public String name;
		public String description;
		public String[] catalogs;
	}

	Map<String, AuthorityDescriptor> authorities = Collections.synchronizedMap(new HashMap<>());
	Map<String, AuthorityDescriptor> inactive = Collections.synchronizedMap(new HashMap<>());

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
				AuthorityDescriptor descriptor = new AuthorityDescriptor();
				descriptor.authority = (IAuthority) cls.getDeclaredConstructor().newInstance();
				descriptor.catalogs = annotation.catalogs();
				descriptor.description = annotation.description();
				descriptor.name = annotation.id();
				authorities.put(annotation.id(), descriptor);
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
		return authorities.values().stream().map((a) -> a.authority).collect(Collectors.toList());
	}

	@Override
	public IAuthority getAuthority(String authorityId) {
		AuthorityDescriptor descriptor = authorities.get(authorityId);
		return descriptor == null ? null : descriptor.authority;
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
			ret = authorities.get(auth).authority.getIdentity(identityId, catalog);
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
		AuthorityDescriptor auth = authorities.remove(authority);
		if (auth != null) {
			inactive.put(authority, auth);
		}
	}

	public List<AuthorityReference> getAuthorityDescriptors() {
		List<AuthorityReference> ret = new ArrayList<>();
		for (String authorityId : this.authorities.keySet()) {
			AuthorityDescriptor authority = this.authorities.get(authorityId);
			IAuthority.Capabilities capabilities = authority.authority.getCapabilities();
			if (capabilities instanceof AuthorityReference) {
				ret.add((AuthorityReference) capabilities);
				if (((AuthorityReference) capabilities).getDescription() == null) {
					((AuthorityReference) capabilities).setDescription(authority.description);
				}
			}
			if (authority.catalogs != null && capabilities.getSubAuthorities().isEmpty()) {
				for (String c : authority.catalogs) {
					capabilities.getSubAuthorities().add(new Pair<>(c, c));
				}
			}
		}
		return ret;
	}

	void registerResourceAuthority(ICodelist codelist, IResource resource) {

		if (codelist.isAuthority() && codelist.getAuthorityId() != null) {

			String[] auths = codelist.getAuthorityId().split("\\.");

			if (auths.length == 1) {
			
				AuthorityDescriptor descriptor = new AuthorityDescriptor();
				descriptor.authority = new ResourceAuthority(codelist, resource);
				descriptor.description = codelist.getDescription();
				descriptor.name = codelist.getAuthorityId();
				authorities.put(codelist.getAuthorityId(), descriptor);
			
			} else if (auths.length == 2) {
				
				AgencyAuthority authority = null;
				AuthorityDescriptor descriptor = authorities.get(auths[0]);

				if (descriptor == null) {
				
					descriptor = new AuthorityDescriptor();
					descriptor.authority = authority = new AgencyAuthority(auths[0]);
					descriptor.description = authority.getDescription();
					descriptor.name = auths[0];
					authorities.put(auths[0], descriptor);
					
				} else if (descriptor.authority instanceof AgencyAuthority) {
					authority = (AgencyAuthority) descriptor.authority;
				} else {
					Logging.INSTANCE.error("Cannot create resource authority " + codelist.getAuthorityId()
							+ ": agency name conflicts with existing authority");
				}
				
				authority.addAuthority(auths[1], new ResourceAuthority(codelist, resource));
				
			} else {
				Logging.INSTANCE.error("Cannot create resource authority " + codelist.getAuthorityId()
						+ ": unsupported number of levels");
			}
		}
	}

}
