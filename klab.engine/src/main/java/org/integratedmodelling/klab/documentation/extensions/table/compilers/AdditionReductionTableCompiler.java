package org.integratedmodelling.klab.documentation.extensions.table.compilers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.extensions.ITableCompiler;
import org.integratedmodelling.klab.api.knowledge.IObservedConcept;
import org.integratedmodelling.klab.api.knowledge.ISemantic;
import org.integratedmodelling.klab.api.observations.IKnowledgeView.Attribute;
import org.integratedmodelling.klab.api.observations.IKnowledgeView.Builder;
import org.integratedmodelling.klab.api.observations.IKnowledgeView.Style;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.data.storage.BasicFileMappedStorage;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabStorageException;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.TemplateUtils;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * Contabilize all changes that add or remove from each category, plus the
 * initial and final values. Optionally also contabilize changes from and to a
 * nodata category.
 * 
 * @author Ferd
 *
 */
public class AdditionReductionTableCompiler implements ITableCompiler {

	IState sourceState;
	boolean contabilizeNulls = false;
	List<?> comparedStates;
	IObservedConcept target;
	String reportedValue;
	boolean rowTotals = false;
	boolean colTotals = false;
	IUnit areaUnit = Units.INSTANCE.SQUARE_KILOMETERS;
	String emptyValue = "0.0";
	String noDataValue = "0.0";
	boolean hasNulls = false;
	Map<String, Object> templateVars;

	/*
	 * hashes to keep the correspondence between the original values and their
	 * numeric value and the combined values and the value for the final histogram.
	 */
	int nextId = 2; // use 1 for nodata and start at 2 for actual data
	BiMap<Object, Integer> codes = HashBiMap.create();
	Map<Pair<Object, Object>, Double> bins = new HashMap<>();

	@Override
	public void initialize(IParameters<String> parameters, Map<?, ?> tableDefinition, IContextualizationScope scope) {

		if (scope == null) {
			// not viable, but this instance will not be used.
			return;
		}

		if (tableDefinition.containsKey("target")) {
			this.target = Observables.INSTANCE.asObservedConcept(tableDefinition.get("target"));
			if (this.target != null) {
				IObservation state = ((IRuntimeScope) scope).getCatalog().get(this.target);
				if (state instanceof IState) {
					this.sourceState = (IState) state;
				}
			}
		}

		if (parameters.contains("unit")) {
			areaUnit = Unit.create(parameters.get("unit", String.class));
		}

		this.comparedStates = parameters.get("compare", List.class);
		this.contabilizeNulls = parameters.get("contabilize-nulls", Boolean.FALSE);
		this.rowTotals = parameters.get("row-totals", Boolean.FALSE);
		this.colTotals = parameters.get("column-totals", Boolean.FALSE);
		this.reportedValue = parameters.get("report", "count");
		this.emptyValue = parameters.get("empty", "0.0");
		this.noDataValue = parameters.get("no-data", "0.0");
		this.templateVars = getTemplateVars(scope);
		if (this.sourceState == null || this.comparedStates == null || this.comparedStates.size() != 2) {
			throw new KlabIllegalArgumentException(
					"Pairwise table compiler called with insufficient or wrong arguments");
		}

	}

	private Map<String, Object> getTemplateVars(IContextualizationScope scope) {
		Map<String, Object> ret = new HashMap<>();
		ret.put("init", "at start of " + Time.getDisplayLabel(scope.getRootSubject().getScale().getTime().getStart(),
				scope.getRootSubject().getScale().getTime().getResolution()));
		ret.put("start", Time.getDisplayLabel(scope.getRootSubject().getScale().getTime().getStart(),
				scope.getRootSubject().getScale().getTime().getResolution()));
		ret.put("end", "at start of " + Time.getDisplayLabel(scope.getRootSubject().getScale().getTime().getEnd(),
				scope.getRootSubject().getScale().getTime().getResolution()));
		return ret;
	}

	class SData {
		double opening = 0;
		double closing = 0;
		double additions = 0;
		double reductions = 0;
	};

	@Override
	public void compile(Builder builder) {

		bins.clear();
		codes.clear();
		nextId = 2;

		builder.setEmptyCells(emptyValue, noDataValue);

		/*
		 * Get the two slices to compare; use the storage directly if possible. If same
		 * slice or non-existing, give up.
		 */
		ITime first = getTime(sourceState.getScale().getTime(), this.comparedStates.get(0));
		ITime last = getTime(sourceState.getScale().getTime(), this.comparedStates.get(1));

		Map<Object, SData> data = new HashMap<>();

		/*
		 * Create temporary storage during the first pass
		 */
		try (BasicFileMappedStorage<Double> storage = new BasicFileMappedStorage<Double>(Double.class,
				sourceState.getSpace().size(), 1)) {

			/*
			 * first pass
			 */
			long ofs = 0;
			for (ILocator locator : sourceState.getScale().at(first)) {

				double value = 1;
				if ("area".equals(reportedValue)) {
					value = areaUnit
							.convert(((IScale) locator).getSpace().getStandardizedArea(), Units.INSTANCE.SQUARE_METERS)
							.doubleValue();
				}

				Object val = sourceState.get(locator);
				int code = getCode(val);
				storage.set((double) code, ofs++);

				if (val == null) {
					val = OWL.INSTANCE.getNothing();
				}

				SData dat = data.get(val);
				if (dat == null) {
					dat = new SData();
					data.put(val, dat);
				}

				dat.opening += value;
			}

			/*
			 * second pass: build histogram for each pair of states
			 */
			ofs = 0;
			for (ILocator locator : sourceState.getScale().at(last)) {

				double value = 1;
				if ("area".equals(reportedValue)) {
					value = areaUnit
							.convert(((IScale) locator).getSpace().getStandardizedArea(), Units.INSTANCE.SQUARE_METERS)
							.doubleValue();
				}

				Object state2 = sourceState.get(locator);
				Object state1 = codes.inverse().get(storage.get(ofs++).intValue());

				if (state1 == null) {
					hasNulls = true;
					state1 = OWL.INSTANCE.getNothing();
				}
				if (state2 == null) {
					hasNulls = true;
					state2 = OWL.INSTANCE.getNothing();
				}

				SData dat2 = data.get(state2);
				if (dat2 == null) {
					dat2 = new SData();
					data.put(state2, dat2);
				}

				dat2.closing += value;
				if (!state2.equals(state1)) {
					SData dat1 = data.get(state1);
					dat1.reductions += value;
					dat2.additions += value;
				}
			}

		} catch (Throwable e) {
			throw new KlabStorageException(e);
		}

		/*
		 * build the fucka
		 */
		SData unassigned = null;
		Map<String, Object> labels = new HashMap<>();
		for (Object key : data.keySet()) {
			if (OWL.INSTANCE.getNothing().equals(key)) {
				unassigned = data.get(key);
			} else {
				labels.put(getLabel(key), key);
			}
		}

		List<String> labs = new ArrayList<>(labels.keySet());
		Collections.sort(labs);
		if (unassigned != null) {
			labs.add("Unaccounted");
			labels.put("Unaccounted", OWL.INSTANCE.getNothing());
		}

		String rowIncoming = builder.getRow(Attribute.HEADER_0,
				TemplateUtils.expandMatches("Opening area {start}", templateVars).get(0), Style.BOLD);
		String rowAdditions = builder.getRow(Attribute.HEADER_1, "Expansions");
		String rowReductions = builder.getRow(Attribute.HEADER_1, "Regressions");
		String rowNetChange = builder.getRow(Attribute.HEADER_1, "Net change");
		String rowOutgoing = builder.getRow(Attribute.HEADER_0,
				TemplateUtils.expandMatches("Closing area {end}", templateVars).get(0), Style.BOLD);

		SData totals = new SData();

		for (Object label : labs) {
			SData dat = data.get(labels.get(label));
			String column = builder.getColumn("Unaccounted".equals(label) ? label : labels.get(label));
			builder.setCell(rowIncoming, column, dat.opening);
			builder.setCell(rowOutgoing, column, dat.closing);
			builder.setCell(rowNetChange, column, dat.closing - dat.opening);
			builder.setCell(rowAdditions, column, dat.additions);
			builder.setCell(rowReductions, column, dat.reductions);

			totals.opening += dat.opening;
			totals.closing += dat.closing;
			totals.additions += dat.additions;
			totals.reductions += dat.reductions;
		}

		String colTotals = builder.getColumn("Totals", Style.BOLD);
		builder.setCell(rowIncoming, colTotals, totals.opening);
		builder.setCell(rowOutgoing, colTotals, totals.closing);
		builder.setCell(rowNetChange, colTotals, totals.closing - totals.opening);
		builder.setCell(rowAdditions, colTotals, totals.additions);
		builder.setCell(rowReductions, colTotals, totals.reductions);

	}

	private String getLabel(Object object) {
		if (OWL.INSTANCE.getNothing().equals(object)) {
			return "Unaccounted";
		}
		if (object instanceof ISemantic) {
			return Concepts.INSTANCE.getDisplayLabel(((ISemantic) object).getType());
		}
		return "";
	}

	private int getCode(Object object) {
		if (Observations.INSTANCE.isData(object)) {
			Integer ret = codes.get(object);
			if (ret == null) {
				ret = nextId;
				codes.put(object, ret);
				nextId++;
			}
			return ret;
		}
		return 1;
	}

	private ITime getTime(ITime overall, Object object) {
		if (object instanceof Number) {
			return (ITime) overall.getExtent(((Number) object).longValue());
		} else if (object instanceof String) {
			switch ((String) object) {
			case "init":
				return Time.initialization(overall);
			case "start":
				return overall.earliest();
			case "end":
				return overall.latest();
			}
		} else if (object instanceof ITime) {
			return (ITime) object;
		}
		throw new KlabIllegalArgumentException("pairwise table compiler: cannot infer temporal state from " + object);
	}

}
