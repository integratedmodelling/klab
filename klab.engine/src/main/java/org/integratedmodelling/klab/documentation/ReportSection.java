package org.integratedmodelling.klab.documentation;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.documentation.IReport;
import org.integratedmodelling.klab.api.documentation.IReport.Section;
import org.integratedmodelling.klab.api.documentation.IReport.SectionRole;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.StringUtils;

public class ReportSection extends Parameters<String> implements Section {

	IReport.SectionRole role;
	String id = "rsec" + NameGenerator.shortUUID();
	String name = null;
	List<ReportSection> children = new ArrayList<>();
	String body = "";
	
	ReportSection(SectionRole role) {
		this.role = role;
	}
	
	ReportSection(ReportSection parent) {
		parent.children.add(this);
	}
	
	public String getName() {
		return name == null ? StringUtils.capitalize(role.name()) : name;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public SectionRole getRole() {
		return role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportSection other = (ReportSection) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public ReportSection getChild(ReportSection parent, String titlePath) {
		ReportSection ret = parent;
		while (titlePath.startsWith("/")) {
			titlePath = titlePath.substring(1);
		}
		String[] path = titlePath.split("\\/");
		for (int i = 0; i < path.length; i++) {
			ret = ret.getOrCreateChildNamed(path[i]);
		}
		return ret;
	}

	private ReportSection getOrCreateChildNamed(String string) {
		for (ReportSection child : children) {
			if (child.name.equals(string)) {
				return child;
			}
		}
		ReportSection ret = new ReportSection(this);
		ret.name = string;
		return ret;
	}

	
	
}
