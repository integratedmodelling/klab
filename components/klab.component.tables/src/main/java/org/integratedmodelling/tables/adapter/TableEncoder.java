package org.integratedmodelling.tables.adapter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.Authorities;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Attribute;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.data.general.ITable.Filter;
import org.integratedmodelling.klab.api.data.general.ITable.Filter.Type;
import org.integratedmodelling.klab.api.knowledge.ICodelist;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.Aggregator;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.SemanticType;
import org.integratedmodelling.klab.utils.Triple;
import org.integratedmodelling.tables.AbstractTable;
import org.integratedmodelling.tables.CodeList;
import org.integratedmodelling.tables.DimensionScanner;
import org.integratedmodelling.tables.ITableInterpreter;
import org.integratedmodelling.tables.TablesComponent;

public class TableEncoder implements IResourceEncoder {

	public TableEncoder() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isOnline(IResource resource, IMonitor monitor) {
		// TODO Auto-generated method stub
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void getEncodedData(IResource resource, Map<String, String> urnParameters, IGeometry geometry,
			Builder builder, IContextualizationScope scope) {

		boolean ignoreTime = false;
		boolean ignoreSpace = false;
		if (urnParameters.containsKey("ignore")) {
			String[] ss = urnParameters.get("ignore").split(",");
			for (String s : ss) {
				if ("time".equals(s.toLowerCase())) {
					ignoreTime = true;
				} else if ("space".equals(s.toLowerCase())) {
					ignoreSpace = true;
				}
			}
		}

		if (ignoreTime && scope.getScale().getTime() != null
				&& scope.getScale().getTime().getTimeType() != ITime.Type.INITIALIZATION) {
			// just don't move.
			return;
		}

		ITable<?> table = getTable(resource, scope.getMonitor());
		if (table != null) {

			/*
			 * collect attributes to scan: either an attribute, which we map to the values
			 * of a column, or the keyword "header" which makes us scan all attributes,
			 * collect that map to concepts, and check by applying any condition in the
			 * contextualized values.
			 */
			List<Attribute> scannedAttributes = new ArrayList<>();

			String collected = urnParameters.get("collect");
			String collectCondition = null;
			List<CodeList> collectedMappings = new ArrayList<>();
			Map<String, IConcept> collectedAttributeToConcept = new HashMap<>();

			if (collected != null) {

				if (collected.contains(":")) {
					String[] cc = collected.split(Pattern.quote(":"));
					collected = cc[0];
					if (cc.length > 1 && !cc[1].isEmpty()) {
						collectCondition = cc[1];
					}
				}
				if (collected.contains("->")) {
					String[] cc = collected.split(Pattern.quote("->"));
					collected = cc[0];
					for (int i = 1; i < cc.length; i++) {
						// succeeds
						CodeList cm = ((AbstractTable<?>) table).getMapping(cc[i]);
						if (cm == null) {
							throw new KlabValidationException("table resource refers to non-existent mapping " + cc[i]);
						}
						collectedMappings.add(cm);
					}
				}

				if ("header".equals(collected)) {

					// adds stuff
					for (Attribute attr : resource.getAttributes()) {
						Object attrName = attr.getName();
						for (CodeList m : collectedMappings) {
							attrName = m.value(attrName.toString());
						}
						if (attrName instanceof IConcept) {
							scannedAttributes.add(attr);
							collectedAttributeToConcept.put(attr.getName(), (IConcept) attrName);
						}
					}

				} else {
					Object str = urnParameters.get("collect");
					Attribute attr = str == null ? null : table.getColumnDescriptor(str.toString());
					if (attr == null) {
						throw new KlabValidationException(
								"table resource refers to non-existent attribute " + collected);
					}
					scannedAttributes.add(attr);
				}

			}

			DimensionScanner<ITime> time = (DimensionScanner<ITime>) ((Resource) resource).getRuntimeData().get("time");
			DimensionScanner<ISpace> space = (DimensionScanner<ISpace>) ((Resource) resource).getRuntimeData()
					.get("space");

			/*
			 * if collect=something, we collect the values of the specified attribute in the
			 * filtered space and time corresponding to the context. They must specify one
			 * or more compatible concepts, which we OR if >1, and set into the semantics
			 * field of the builder.
			 */
			if (scannedAttributes.size() > 0) {

				Set<IConcept> collection = new HashSet<>();
				if (time != null && !ignoreTime) {
					table = time.subset(table, scope.getScale());
				}
				if (space != null && !ignoreSpace) {
					table = space.subset(table, scope.getScale());
				}

				/*
				 * scan each row and ask t/s if it applies; if so, collect the value in the
				 * passed collection
				 */
				if (scannedAttributes.size() == 1) {
					for (Object value : table.get(List.class, scope, scannedAttributes.get(0))) {
						if (value instanceof IConcept) {
							collection.add((IConcept) value);
						}
					}
				} else {
					for (Attribute attribute : scannedAttributes) {

						/*
						 * scan all the attributes and collect the mapped concept value
						 */
						for (Object value : table.get(List.class, scope, attribute)) {
							if (Observations.INSTANCE.isData(value)) {
								boolean ok = true;
								/*
								 * poor man's expression logics for now. Later: turn into expressions
								 */
								if (collectCondition != null) {
									if (collectCondition.startsWith(">")) {
										Double min = Double.parseDouble(collectCondition.substring(1));
										if (value instanceof String && NumberUtils.encodesDouble((String) value)) {
											value = Double.parseDouble((String) value);
										}
										ok = value instanceof Number && ((Number) value).doubleValue() > min;
									}
								}
								if (ok) {
									collection.add(collectedAttributeToConcept.get(attribute.getName()));
									break;
								}
							}
						}
					}
				}

				if (collection.size() == 1) {
					builder = builder.withSemantics(collection.iterator().next());
				} else if (collection.size() > 1) {
					builder = builder.withSemantics(Concepts.INSTANCE.or(collection));
				}

				return;

			}

			/**
			 * First pass: contextualize for the scope, which may redefine some filters. We
			 * leave space/time filters out of this for now, although they could be more
			 * elegantly included in this step.
			 */
			table = table.contextualize(scope);

			if (time != null && !ignoreTime) {
				Filter timeFilter = time.locate(table, geometry);
				if (timeFilter != null) {
					table = table.filter(timeFilter);
				}
			}

			/*
			 * if we asked for a specific column in the value attribute, map it first if
			 * needed, then filter.
			 * 
			 * If we have >1 unresolved keys and the value attribute wasn't specified, the
			 * object returned can be a TABLE instead of a VALUE.
			 * 
			 * TODO this should be <-, i.e. the mapping should always follow the arrows -
			 * worried that it makes it impossible to understand (it probably already is
			 * though). Correctly it should probably say header->headerToCrop:<concept> to
			 * define what to match.
			 */
			if (urnParameters.containsKey(Urn.SINGLE_PARAMETER_KEY)) {

				String[] columnId = urnParameters.get(Urn.SINGLE_PARAMETER_KEY).split(Pattern.quote("->"));
				Object column = columnId[0];

				if (SemanticType.validate(column.toString()) && scope.getTargetSemantics() != null) {
					/*
					 * check if this is an abstract identity or role and if so, translate to
					 * whatever the observable incarnates it to.
					 */
					IConcept predicate = Concepts.c(column.toString());
					if (predicate != null && predicate.isAbstract()
							&& (predicate.is(IKimConcept.Type.ROLE) || predicate.is(IKimConcept.Type.IDENTITY))) {
						IConcept c = ((Observable) scope.getTargetSemantics()).getPredicate(predicate);
						if (c != null) {
							column = c.getDefinition();
						}
					}
				}

				for (int i = 1; i < columnId.length; i++) {
					CodeList map = ((AbstractTable<?>) table).getMapping(columnId[i]);
					if (map == null) {
						throw new KlabIllegalArgumentException(
								"table resource does not include a codelist named " + columnId[i]);
					}
					Object mapped = map.key(column);
					if (mapped == null) {
						scope.getMonitor()
								.warn("Cannot map value " + column + " using mapping " + map.getDescription());
						return;
					}
					column = mapped;
				}

				/**
				 * FIXME this duplicates what set at beginning by setFilters - the filter checks
				 * for duplicates so nothing bad happens, but there should be no duplication in
				 * the first place.
				 */
				if (table.getColumnDescriptor(column.toString()) == null) {
					throw new KlabIllegalArgumentException("table resource does not include a column named " + column);
				}

				table = table.filter(Type.INCLUDE_COLUMNS,
						Collections.singleton(table.getColumnDescriptor(column.toString()).getIndex()));
			}

			Map<Filter, Object> valueCache = new HashMap<>();
			Aggregator aggregator = new Aggregator(scope.getTargetSemantics(), scope.getMonitor(), true);

			for (Filter filter : table.getFilters()) {
				if (filter.getType() == Type.NO_RESULTS) {
					return;
				}
			}

			/*
			 * find out which observed states are mapped to attribute codelists, if any
			 */
			List<Triple<IState, ICodelist, String>> mappedStates = new ArrayList<>();
			for (Pair<String, IState> state : scope.getArtifacts(IState.class)) {
				if (state.getSecond().getDataKey() != null && state.getSecond().getDataKey().getAuthority() != null) {
					ICodelist codelist = state.getSecond().getDataKey().getAuthority().getCodelist();
					if (codelist != null) {
						String columnId = ((AbstractTable<?>) table).findClassifiedColumn(codelist);
						if (columnId != null) {
							mappedStates.add(new Triple<>(state.getSecond(), codelist, columnId));
						}
					}
				}
			}

			/**
			 * Otherwise, we just scan the space (time has been filtered upstream) and
			 * collect the values corresponding to the remaining filtering in the table. If
			 * there is no space we only do one evaluation.
			 */
			boolean cached = false;
			Object value = null;

			for (ILocator locator : scope.getScale()) {

				if (scope.getMonitor().isInterrupted()) {
					return;
				}

				Set<Filter> filters = new HashSet<>();

				if (!table.isEmpty()) {

					ITable<?> t = table;

					/*
					 * Filter for any keys observed through states. TODO the value cache should look
					 * at ALL these filters, space and the others.
					 */
					for (Triple<IState, ICodelist, String> mst : mappedStates) {
						Object aid = mst.getFirst().get(locator);
						if (aid instanceof IConcept) {
							String code = Authorities.INSTANCE.getAuthorityCode((IConcept) aid);
							if (code != null) {
								t = t.filter(Filter.Type.COLUMN_MATCH,
										new Object[] { mst.getThird(), mst.getSecond().value(code) });
							} else {
								builder.set(null, locator);
								return;
							}
						}
					}

					if (space != null && !ignoreSpace) {

						Filter filter = space.locate(table, locator);

						/*
						 * cache the spatial filter only as the others don't change
						 */
						if (valueCache.containsKey(filter)) {
							value = valueCache.get(filter);
						} else {
							if (filter != null) {
								System.out.println("   NEW SPATIAL FILTER " + filter);
								t = t.filter(filter);
							}
							/*
							 * this takes all matching values, aggregating if needed using the aggregator
							 * that fits the semantics.
							 */
							value = t.get(Object.class, scope, aggregator);
							System.out.println("       aggregated value = " + value);
							valueCache.put(filter, value);
						}
					} else if (!cached || ignoreSpace) {
						value = t.get(Object.class, scope, aggregator);
						cached = true;
						System.out.println("       aggregated value = " + value);
					}
				}

				builder.set(value, locator);
			}

		}

	}

	@SuppressWarnings("unchecked")
	private ITable<?> getTable(IResource resource, IMonitor monitor) {

		ITable<?> ret = (ITable<?>) ((Resource) resource).getRuntimeData().get("table");
		if (ret == null) {
			ret = setFilters(resource, TableAdapter.getOriginalTable(resource, true, monitor), null);
			DimensionScanner<IExtent> space = TableAdapter.runtimeData.get(resource.getUrn() + "_space",
					DimensionScanner.class);
			DimensionScanner<IExtent> time = TableAdapter.runtimeData.get(resource.getUrn() + "_time",
					DimensionScanner.class);
			((Resource) resource).getRuntimeData().put("table", ret);
			((Resource) resource).getRuntimeData().put("space", space);
			((Resource) resource).getRuntimeData().put("time", time);
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IResource contextualize(IResource resource, IScale scale, IArtifact targetObservation,
			Map<String, String> urnParameters, IContextualizationScope scope) {

		boolean ignoreTime = false;
		boolean ignoreSpace = false;
		if (urnParameters.containsKey("ignore")) {
			String[] ss = urnParameters.get("ignore").split(",");
			for (String s : ss) {
				if ("time".equals(s.toLowerCase())) {
					ignoreTime = true;
				} else if ("space".equals(s.toLowerCase())) {
					ignoreSpace = true;
				}
			}
		}

		ITable<?> table = setFilters(resource, TableAdapter.getOriginalTable(resource, true, scope.getMonitor()),
				urnParameters);
		DimensionScanner<IExtent> space = TableAdapter.runtimeData.get(resource.getUrn() + "_space",
				DimensionScanner.class);
		DimensionScanner<IExtent> time = TableAdapter.runtimeData.get(resource.getUrn() + "_time",
				DimensionScanner.class);

		if (time != null && !ignoreTime) {
			time = time.contextualize(table, scale.getTime(), scope);
		}
		if (space != null && !ignoreSpace) {
			space = space.contextualize(table, scale.getSpace(), scope);
		}

		IResource ret = ((Resource) resource).copy();

		((Resource) ret).getRuntimeData().put("table", table);
		((Resource) ret).getRuntimeData().put("space", space);
		((Resource) ret).getRuntimeData().put("time", time);

		return ret;
	}

	private ITable<?> setFilters(IResource resource, ITable<?> originalTable, Map<String, String> urnParameters) {

		ITable<?> ret = originalTable;
		/*
		 * The filter is an expression
		 */
		if (resource.getParameters().containsKey("filter")) {
			ret = ret.filter(Filter.Type.COLUMN_EXPRESSION, resource.getParameters().get("filter").toString());
		}
		if (urnParameters != null) {
			for (String parm : urnParameters.keySet()) {
				if (Urn.SINGLE_PARAMETER_KEY.equals(parm)) {
					Attribute attribute = originalTable.getColumnDescriptor(urnParameters.get(parm));
					if (attribute != null) {
						ret = ret.filter(Filter.Type.INCLUDE_COLUMNS, Collections.singleton(attribute.getIndex()));
						// if not there, continue on to filtering
						continue;
					}
				}
				if (originalTable.getColumnDescriptor(parm) != null) {
					ret = ret.filter(Filter.Type.COLUMN_MATCH, parm,
							processFilter(originalTable.getColumnDescriptor(parm), urnParameters.get(parm)));
				}
			}
		}

		return ret;
	}

	private Object processFilter(Attribute columnDescriptor, String filterSpecs) {

		return filterSpecs;
	}

	@Override
	public ICodelist categorize(IResource resource, String attribute, IMonitor monitor) {
		ITableInterpreter interpreter = TablesComponent.getTableInterpreter(resource.getAdapterType());
		for (String codelist : resource.getCodelists()) {
			if (attribute.equals(codelist)) {
				return Resources.INSTANCE.getCodelist(resource, attribute, monitor);
			}
		}
		interpreter.categorize(resource, Parameters.create("dimension", attribute), monitor);
		resource.getCodelists().add(attribute);
		Resources.INSTANCE.getCatalog(resource).update(resource, "Codelist " + attribute + " created");
		return Resources.INSTANCE.getCodelist(resource, attribute, monitor);
	}

	@Override
	public void listDetail(IResource resource, OutputStream stream, boolean verbose, IMonitor monitor) {

		OutputStreamWriter writer = new OutputStreamWriter(stream);
		ITable<?> table = TableAdapter.getOriginalTable(resource, false, monitor);

		try {

			if (table instanceof AbstractTable && ((AbstractTable<?>) table).getCache(resource) != null) {
				((AbstractTable<?>) table).getCache(resource).dumpData(verbose ? 50 : 0, writer);
				writer.flush();
				return;
			}

			if (verbose) {

				/*
				 * list the first 50 data rows
				 */
				int[] dims = table.size();
				for (int i = 0; i < (dims[0] < 50 ? dims[0] : 50); i++) {
					List<?> row = table.getRowItems(i);
					boolean first = true;
					for (Object o : row) {
						writer.append((first ? "" : "\t") + o);
						first = false;
					}
					writer.append("\n");
				}

			} else {

				/*
				 * list the structure + the first 10 values per each field
				 */
				int[] dims = table.size();
				for (int i = 0; i < dims[1]; i++) {
					Attribute column = table.getColumnDescriptor(i);
					writer.append(column.getName() + ": " + column.getType() + "\n");
				}
			}

			writer.flush();

		} catch (IOException e) {
			throw new KlabIOException(e);
		}

	}

}
