package org.integratedmodelling.klab.common;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.rest.CompileNotificationReference;

/**
 * All compilation info pertaining to a namespace.
 * 
 * @author ferdinando.villa
 *
 */
public class CompileInfo {

	private List<CompileNotificationReference> errors = new ArrayList<>();
	private List<CompileNotificationReference> warnings = new ArrayList<>();
	private List<CompileNotificationReference> info = new ArrayList<>();

	public List<CompileNotificationReference> getErrors() {
		return errors;
	}

	public List<CompileNotificationReference> getWarnings() {
		return warnings;
	}

	public List<CompileNotificationReference> getInfo() {
		return info;
	}

	public List<CompileNotificationReference> getErrors(String id) {
		List<CompileNotificationReference> ret = new ArrayList<>();
		for (CompileNotificationReference reference : errors) {
			if ((reference.getScopeName() != null && reference.getScopeName().equals(id))
					|| (reference.getStatementUrn() != null && reference.getStatementUrn().equals(id))) {
				ret.add(reference);
			}
		}
		return ret;
	}

	public List<CompileNotificationReference> getWarnings(String id) {
		List<CompileNotificationReference> ret = new ArrayList<>();
		for (CompileNotificationReference reference : warnings) {
			if ((reference.getScopeName() != null && reference.getScopeName().equals(id))
					|| (reference.getStatementUrn() != null && reference.getStatementUrn().equals(id))) {
				ret.add(reference);
			}
		}
		return ret;
	}

	public List<CompileNotificationReference> getInfo(String id) {
		List<CompileNotificationReference> ret = new ArrayList<>();
		for (CompileNotificationReference reference : info) {
			if ((reference.getScopeName() != null && reference.getScopeName().equals(id))
					|| (reference.getStatementUrn() != null && reference.getStatementUrn().equals(id))) {
				ret.add(reference);
			}
		}
		return ret;
	}

	public void setErrors(List<CompileNotificationReference> errors) {
		this.errors = errors;
	}

	public void setWarnings(List<CompileNotificationReference> warnings) {
		this.warnings = warnings;
	}

	public void setInfo(List<CompileNotificationReference> info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "CompileInfo [errors=" + errors + ", warnings=" + warnings + ", info=" + info + "]";
	}

}