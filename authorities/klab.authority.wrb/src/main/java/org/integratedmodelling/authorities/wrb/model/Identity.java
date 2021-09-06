package org.integratedmodelling.authorities.wrb.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.integratedmodelling.authorities.wrb.WRBAuthority;
import org.integratedmodelling.klab.rest.AuthorityIdentity;
import org.integratedmodelling.klab.rest.Notification;
import org.integratedmodelling.klab.utils.StringUtils;

/**
 * The parsed RSG with all its components. This one does not need bean methods
 * for reflection and serialization.
 * 
 * @author Ferd
 *
 */
public class Identity {

	/**
	 * A qualifier with an optional specifier and methods to normalize the string
	 * form and extract a stable short ID.
	 * 
	 * @author Ferd
	 *
	 */
	public static class SpecifiedQualifier {

		private Qualifier qualifier;
		private Specifier specifier = null;
		private String stringForm;

		public Qualifier getQualifier() {
			return qualifier;
		}

		public void setQualifier(Qualifier qualifier) {
			this.qualifier = qualifier;
		}

		public Specifier getSpecifier() {
			return specifier;
		}

		public void setSpecifier(Specifier specifier) {
			this.specifier = specifier;
		}

		public String getStringForm() {
			return stringForm;
		}

		public void setStringForm(String stringForm) {
			this.stringForm = stringForm;
		}

		public String getCode() {
			return (this.specifier == null ? "" : this.specifier.getCode()) + this.qualifier.getCode();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((qualifier == null) ? 0 : qualifier.hashCode());
			result = prime * result + ((specifier == null) ? 0 : specifier.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SpecifiedQualifier other = (SpecifiedQualifier) obj;
			if (qualifier == null) {
				if (other.qualifier != null)
					return false;
			} else if (!qualifier.equals(other.qualifier))
				return false;
			if (specifier == null) {
				if (other.specifier != null)
					return false;
			} else if (!specifier.equals(other.specifier))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "{" + (specifier == null ? "x" : specifier.getName()) + "," + qualifier.getName() + "}";
		}
	}

	private ReferenceSoilGroup soilGroup;
	private List<SpecifiedQualifier> principalQualifiers = new ArrayList<>();
	private List<SpecifiedQualifier> supplementaryQualifiers = new ArrayList<>();
	private String documentation;
	private String normalizedId;
	private Set<String> errors = new HashSet<>();

	public ReferenceSoilGroup getSoilGroup() {
		return soilGroup;
	}

	public void setSoilGroup(ReferenceSoilGroup soilGroup) {
		this.soilGroup = soilGroup;
	}

	public List<SpecifiedQualifier> getPrincipalQualifiers() {
		return principalQualifiers;
	}

	public Set<String> getErrors() {
		return errors;
	}

	/**
	 * Order is significant in principal qualifiers: same qualifiers with different
	 * order = different identity.
	 * 
	 * @param principalQualifiers
	 */
	public void setPrincipalQualifiers(List<SpecifiedQualifier> principalQualifiers) {
		this.principalQualifiers = principalQualifiers;
	}

	/**
	 * Supplemental qualifiers will be given in normalized order and their order
	 * does not count.
	 * 
	 * @return
	 */
	public List<SpecifiedQualifier> getSupplementaryQualifiers() {
		return supplementaryQualifiers;
	}

	public void setSupplementaryQualifiers(List<SpecifiedQualifier> supplementaryQualifiers) {
		this.supplementaryQualifiers = supplementaryQualifiers;
	}

	public String getDocumentation() {
		
		if (documentation == null) {
			StringBuffer doc = new StringBuffer(512);
			doc.append(toString());
			doc.append("\n\n");

			if (errors.size() > 0) {
				doc.append("This WRB soil specification is invalid due to the following errors:\n\n");
				for (String error : errors) {
					doc.append(". " + error + "\n");
				}
			} else {

				doc.append(soilGroup.getDescription());

				if (principalQualifiers.size() > 0) {
					doc.append("\n\nPrincipal qualifiers:");
					for (SpecifiedQualifier sq : principalQualifiers) {
						doc.append(sq.getStringForm() + "\n");
						doc.append(": ");
						doc.append(sq.getQualifier().getDescription());
						if (sq.getSpecifier() != null) {
							doc.append(" *" + sq.getQualifier().getDescription() + "*: "
									+ sq.getSpecifier().getDescription());
						}
						doc.append("\n");
					}
				}
				if (supplementaryQualifiers.size() > 0) {
					doc.append("\n\nSupplementary qualifiers:");
					for (SpecifiedQualifier sq : supplementaryQualifiers) {
						doc.append(sq.getStringForm() + "\n");
						doc.append(": ");
						doc.append(sq.getQualifier().getDescription());
						if (sq.getSpecifier() != null) {
							doc.append(" *" + sq.getQualifier().getDescription() + "*: "
									+ sq.getSpecifier().getDescription());
						}
						doc.append("\n");
					}
				}
			}
			this.documentation = doc.toString();
		}
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

	/**
	 * Normalized IDs separates the code into one to three groups, each separated by
	 * double underscores. Each group contains the component codes separated by
	 * single underscores. Classifier codes have the specifier code prepended if
	 * any, the last two characters being always the classifier code.
	 * 
	 * @return
	 */
	public String getNormalizedId() {
		
		if (soilGroup == null) {
			return null;
		}
		
		if (normalizedId == null) {
			String pclass = "";
			String sclass = "";
			for (SpecifiedQualifier sq : principalQualifiers) {
				pclass += (pclass.isEmpty() ? "" : "_") + sq.getCode();
			}
			for (SpecifiedQualifier sq : supplementaryQualifiers) {
				sclass += (sclass.isEmpty() ? "" : "_") + sq.getCode();
			}

			normalizedId = pclass + (pclass.isEmpty() ? "" : "__") + soilGroup.getCode()
					+ (sclass.isEmpty() ? "" : "__") + sclass;
		}
		return normalizedId;
	}

	public void setNormalizedId(String normalizedId) {
		this.normalizedId = normalizedId;
	}

	public AuthorityIdentity getAuthorityIdentity() {

		AuthorityIdentity ret = new AuthorityIdentity();

		for (String error : errors) {
			ret.getNotifications().add(new Notification(error, Level.SEVERE.getName()));
		}
		
		ret.setAuthorityName(WRBAuthority.ID);
		ret.setId(getNormalizedId());
		ret.setLabel(toString());
		ret.setConceptName(getNormalizedId());
		ret.setDescription(getDocumentation());
		ret.setLocator(WRBAuthority.ID + ":\"" + getNormalizedId() + "\"");
		
		boolean ws = StringUtils.containsWhitespace(ret.getId());
		ret.setLocator(WRBAuthority.ID + (ws ? ":'" : ":") + ret.getId() + (ws ? "':" : ":"));

		return ret;
	}

	public String toString() {
		StringBuffer ret = new StringBuffer(256);
		for (SpecifiedQualifier primary : principalQualifiers) {
			ret.append(primary.getStringForm());
			ret.append(" ");
		}
		ret.append(soilGroup == null ? "" : soilGroup.getName());
		if (supplementaryQualifiers.size() > 0) {
			ret.append(" (");
			boolean first = true;
			for (SpecifiedQualifier primary : supplementaryQualifiers) {
				if (!first) {
					ret.append(" ");
				}
				ret.append(primary.getStringForm());
				first = false;
			}
			ret.append(")");
		}
		return ret.toString();
	}

}
