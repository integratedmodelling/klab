package org.integratedmodelling.klab.data.resources;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.api.knowledge.ICodelist;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.rest.CodelistReference;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Utils;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

public class Codelist implements ICodelist {

	CodelistReference reference;
	IConcept rootConcept;
	Multimap<Object, Object> direct = LinkedListMultimap.create();
	Multimap<Object, Object> reverse = LinkedListMultimap.create();

	public Codelist(CodelistReference reference) {
		this.reference = reference;
		for (Pair<String, String> pair : reference.getDirectMapping().getMappings()) {
			direct.put(
					Utils.asType(pair.getFirst(),
							Utils.getClassForType(reference.getDirectMapping().getKeyType() == null ? Type.TEXT
									: reference.getDirectMapping().getKeyType())),
					Utils.asType(pair.getSecond(),
							Utils.getClassForType(reference.getDirectMapping().getValueType() == null ? Type.TEXT
									: reference.getDirectMapping().getValueType())));
		}
		for (Pair<String, String> pair : reference.getInverseMapping().getMappings()) {
			reverse.put(
					Utils.asType(pair.getFirst(),
							Utils.getClassForType(reference.getInverseMapping().getKeyType() == null ? Type.TEXT
									: reference.getInverseMapping().getKeyType())),
					Utils.asType(pair.getSecond(),
							Utils.getClassForType(reference.getInverseMapping().getValueType() == null ? Type.TEXT
									: reference.getInverseMapping().getValueType())));
		}
	}

	public CodelistReference getReference() {
		return this.reference;
	}

	@Override
	public Object value(Object key) {
		Collection<Object> ret = direct.get(key);
		// an empty mapping is the object mapping on itself
		return isEmpty(ret) ? key : ret.iterator().next();
	}

	private boolean isEmpty(Collection<Object> ret) {
		if (ret.isEmpty()) {
			return true;
		}
		if (ret.size() == 1) {
			Object content = ret.iterator().next();
			return content == null || (content instanceof String && ((String)content).isEmpty());
		}
		return false;
	}

	@Override
	public String key(Object value) {
		// if there are no reverse mappings, the key to the value is the value itself.
		if (reverse.isEmpty()) {
			return value.toString();
		}
		Collection<Object> ret = reverse.get(Utils.asType(value, reference.getInverseMapping().getKeyType() == null
				? String.class
				: Utils.getClassForType(reference.getInverseMapping().getKeyType())));
		return ret.isEmpty() ? null : ret.iterator().next().toString();
	}

	@Override
	public String getWorldview() {
		return reference.getWorldview();
	}

	@Override
	public Type getType() {
		return reference.getType();
	}

	@Override
	public String getName() {
		return reference.getName();
	}

	@Override
	public String getDescription() {
		return reference.getDescription();
	}

	@Override
	public String getAuthorityId() {
		return reference.getAuthorityId();
	}

	@Override
	public boolean isAuthority() {
		return reference.isAuthority();
	}

	@Override
	public Collection<Object> keys(Object value) {
		return new HashSet<>(reverse.get(value));
	}

	@Override
	public String getPattern() {
		return reference.getPattern();
	}

	@Override
	public String getRootConceptId() {
		return reference.getRootConceptId();
	}

	@Override
	public int size() {
		return direct.size();
	}

}
