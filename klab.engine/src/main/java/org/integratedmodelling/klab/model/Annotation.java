package org.integratedmodelling.klab.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.kim.KimValidator;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Utils;

/**
 * An annotation is a simple parameter object with a unique ID so it can be
 * recognized. This is used when interactive mode is used and parameters are
 * changed by users.
 * 
 * @author Ferd
 *
 */
public class Annotation extends Parameters<String> implements IAnnotation {

	String name;
	IKimAnnotation statement;
	String id = "ann" + NameGenerator.shortUUID();
	Set<String> interactiveParameters = new HashSet<>();

	public Annotation(IKimAnnotation statement) {
		this.name = statement.getName();
		this.statement = statement;
		this.interactiveParameters.addAll(statement.getInteractiveParameters());
		Map<Object, Object> map = KimValidator.compileMapLiteral(statement.getParameters());
		for (Object key : map.keySet()) {
			this.put(key.toString(), map.get(key));
		}
	}

	public Annotation copy() {
		return new Annotation(this.statement);
	}

	/**
	 * Specialized get() that converts IKimConcepts left over as forward references
	 * into concepts at the time of use.
	 */
	@Override
	public Object getDeclared(Object key) {
		Object ret = super.get(key);
		if (ret instanceof Map) {
			Map<Object, Object> map = new LinkedHashMap<Object, Object>();
			for (Entry<?, ?> entry : ((Map<?,?>)ret).entrySet()) {
				Object k = entry.getKey();
				Object o = entry.getValue();
				if (o instanceof IKimConcept) {
					o = Concepts.INSTANCE.declare((IKimConcept)o);
				}
				if (k instanceof IKimConcept) {
					k = Concepts.INSTANCE.declare((IKimConcept)k);
				}
				map.put(k, o);
			}
			ret = map;
		} else if (ret instanceof IKimConcept) {
			ret = Concepts.INSTANCE.declare((IKimConcept)ret);
		}
		return ret;
	}

	@Override
    public <K> K getDeclared(String name, Class<? extends K> cls) {
        Object ret = getDeclared(name);
        if (ret == null) {
            return null;
        }
        return Utils.asType(ret, cls);
    }
	
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Return the unique ID of this annotation.
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * Any parameters that use the ?= syntax
	 * 
	 * @return
	 */
	public Collection<String> getInteractiveParameters() {
		return interactiveParameters;
	}

}
