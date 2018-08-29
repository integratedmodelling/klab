package org.integratedmodelling.klab;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.services.IRoleService;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.OWL;

public enum Roles implements IRoleService {

	INSTANCE;

	@Override
	public Collection<IConcept> getRoles(IConcept concept) {
		return OWL.INSTANCE.getRestrictedClasses(concept, Concepts.p(NS.HAS_ROLE_PROPERTY));
	}

	@Override
	public Collection<IConcept> getDirectRoles(IConcept concept) {
		return OWL.INSTANCE.getDirectRestrictedClasses(concept, Concepts.p(NS.HAS_ROLE_PROPERTY));
	}
	
	@Override
	public boolean hasRole(IConcept type, IConcept role) {
		for (IConcept c : getRoles(type)) {
			if (c.is(role)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasParentRole(IConcept type, IConcept role) {
		for (IConcept c : getRoles(type)) {
			if (role.is(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return the role that is baseRole and is implied by the context observable,
	 * either directly or through its implication closure.
	 * 
	 * @param baseRole
	 * @param contextObservable
	 * @return
	 */
	public IConcept getImpliedRole(IConcept baseRole, IConcept contextObservable) {
		Collection<IConcept> roles = contextObservable.is(Type.ROLE) ? Collections.singleton(contextObservable)
				: getRoles(contextObservable);
		for (IConcept role : roles) {
			if (!role.isAbstract() && role.is(baseRole)) {
				return role;
			}
			for (IConcept irole : getImpliedObservableRoles(role, true)) {
				IConcept ret = getImpliedRole(baseRole, irole);
				if (ret != null) {
					return ret;
				}
			}
		}
		return null;
	}

	/**
	 * Get all other roles implied by this one. These must be concrete when the role
	 * is used in the main observable for a model, which must produce or use them.
	 * 
	 * @param role
	 * @return
	 */
	public Collection<IConcept> getImpliedObservableRoles(IConcept role) {
		return getImpliedObservableRoles(role, false);
	}

	/**
	 * Get all other roles implied by this one. These must be concrete when the role
	 * is used in the main observable for a model, which must produce or use them.
	 * Optionally include the source and destination endpoints for all roles that
	 * apply to a relationship.
	 * 
	 * @param role
	 * @param includeRelationshipEndpoints
	 *            if true, roles that apply to relationships will add the
	 *            specialized source and destination types.
	 * @return
	 */
	public Collection<IConcept> getImpliedObservableRoles(IConcept role, boolean includeRelationshipEndpoints) {

		Set<IConcept> ret = new HashSet<>();
		if (includeRelationshipEndpoints
				&& Concepts.INSTANCE.is(Observables.INSTANCE.getApplicableObservables(role), Type.RELATIONSHIP)) {

			IConcept source = Observables.INSTANCE.getRelationshipSource(role);
			IConcept destination = Observables.INSTANCE.getRelationshipTarget(role);

			if (source != null) {
				ret.add(source);
			}
			if (destination != null) {
				ret.add(destination);
			}
		}

		ret.addAll(OWL.INSTANCE.getRestrictedClasses(role, Concepts.p(NS.IMPLIES_ROLE_PROPERTY), true));

		return ret;
	}

	/**
	 * Pass a role and a context; return the generic observable that is expected to
	 * have that role in that context so that we can query the semantic web for
	 * models.
	 * 
	 * @param concept
	 * @param contextType
	 * @return
	 * @throws KlabException
	 */
	public IConcept getObservableWithRole(IConcept concept, IConcept contextType) throws KlabException {

		IConcept ret = null;

		Collection<IConcept> applicable = Observables.INSTANCE.getApplicableObservables(concept);

		if (!applicable.isEmpty()) {

			IConcept appl = Concepts.INSTANCE.getLeastGeneralConcept(applicable);
			if (appl == null) {
				Logging.INSTANCE.warn("using only the first applicable observable for observation of role " + concept);
			}
			// TODO!
//			ret = Observables.INSTANCE.declareObservable(appl, null, contextType, null, Collections.singleton(concept),
//					null, null, Reasoner.INSTANCE.getOntology());
		} else {
			throw new KlabValidationException(
					"cannot observe a generic role: the role must apply to a specific observable type");
		}

		throw new KlabUnsupportedFeatureException();
//		return ret;
	}

	/**
	 * Return the role
	 * 
	 * @param observable
	 * @param context
	 * @return
	 */
	public Collection<IConcept> getRoleFor(IConcept observable, IConcept context) {
		Set<IConcept> ret = new HashSet<>();
		for (IConcept c : getRoles(context)) {
			for (IConcept r : getImpliedObservableRoles(c)) {
				for (IConcept applicable : Observables.INSTANCE.getApplicableObservables(r)) {
					if (applicable != null && !applicable.isAbstract() && observable.is(applicable)) {
						ret.add(r);
					}
				}
			}
		}
		return ret;
	}

}
