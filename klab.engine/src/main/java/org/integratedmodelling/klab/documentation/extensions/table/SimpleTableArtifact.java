package org.integratedmodelling.klab.documentation.extensions.table;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.documentation.views.IDocumentationView;
import org.integratedmodelling.klab.api.observations.IKnowledgeView;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.documentation.extensions.table.TableCompiler.Style;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.provenance.Artifact;
import org.integratedmodelling.klab.rest.ObservationReference.ExportFormat;
import org.integratedmodelling.klab.utils.NameGenerator;

public class SimpleTableArtifact extends Artifact implements IKnowledgeView {

	static class Dimension {
		public Dimension(String string, Object[] options) {
		    this.id = string;
		    // TODO handle options
		}

		boolean header;
		String id;
		String label;
		Set<Style> style = EnumSet.noneOf(Style.class);
	}

	public static class TableBuilder implements IKnowledgeView.Builder {

		IRuntimeScope scope;
		Map<String, Dimension> rows = new LinkedHashMap<>();
		Map<String, Dimension> cols = new LinkedHashMap<>();
		Map<Object, Dimension> crows = new HashMap<>();
		Map<Object, Dimension> ccols = new HashMap<>();

		TableBuilder(TableCompiler compiler, IRuntimeScope scope) {
			this.scope = scope;
		}

		@Override
		public String getColumn(Object classifier, Object... options) {
			Dimension dim = ccols.get(classifier);
			if (dim == null) {
				dim = new Dimension("c" + ccols.size() + 1, options);
				ccols.put(classifier, dim);
			}
			return dim.id;
		}

		@Override
		public String getRow(Object classifier, Object... options) {
			Dimension dim = crows.get(classifier);
			if (dim == null) {
				dim = new Dimension("r" + crows.size() + 1, options);
				crows.put(classifier, dim);
			}
			return dim.id;
		}

		@Override
		public String getCell(String rowId, String colId, Object value, Object... options) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IKnowledgeView build() {
			TableArtifact ret = new TableArtifact(null, null, null, null);
			return ret;
		}
	}

	TableCompiler tableCompiler;
	IRuntimeScope scope;
	String id = "v" + NameGenerator.shortUUID();

	@Override
	public String getViewClass() {
		return "table";
	}

	@Override
	public String getName() {
		return this.tableCompiler.getName();
	}

	@Override
	public String getTitle() {
		return this.tableCompiler.getTitle();
	}

	@Override
	public IGeometry getGeometry() {
		return Geometry.scalar();
	}

	@Override
	public Type getType() {
		return Type.VOID;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String getIdentifier() {
		return this.tableCompiler.getIdentifier();
	}

	@Override
	public IArtifact getGroupMember(int n) {
		return n == 0 ? this : null;
	}

	@Override
	public String getLabel() {
		return this.tableCompiler.getLabel();
	}

	@Override
	public Collection<ExportFormat> getExportFormats() {
		List<ExportFormat> ret = new ArrayList<>();
		ret.add(new ExportFormat("Excel worksheet", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
				"xlsx", "xlsx"));
		return ret;
	}

	@Override
	public long getLastUpdate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IDocumentationView getCompiledView(String mediaType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean export(File file, String mediaType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T getBean(Class<T> cls) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Get a builder for the custom plugins
	 */
	public static Builder builder(TableCompiler compiler, IRuntimeScope scope) {
		return new TableBuilder(compiler, scope);
	}

}
