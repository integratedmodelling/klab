package org.integratedmodelling.klab.engine.debugger;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import javax.swing.WindowConstants;

import org.beryx.textio.PropertiesConstants;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.beryx.textio.swing.SwingTextTerminal;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.general.IExpression.CompilerOption;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid.Cell;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.components.localstorage.impl.AbstractAdaptiveStorage;
import org.integratedmodelling.klab.components.localstorage.impl.KeyedStorage;
import org.integratedmodelling.klab.components.runtime.observations.State;
import org.integratedmodelling.klab.dataflow.ObservedConcept;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.StringUtil;

/*
 * Bare-bones debugger for k.LAB contexts. Installs observation listeners to implement watches and breakpoints, 
 * removes them when quitting. Will grow over time. Create as many as needed using
 * the "debug new" command in the CLI. 
 * 
 * FIXME Used a TextIO demo, not sure of the rationale for the BiConsumer inheritance.
 */
public class Debugger implements BiConsumer<TextIO, ISession> {

	private ISession session;
	private TextTerminal<?> terminal;
	private ILocator focus;
	private Cell cellFocus;
	private ILocator prospectiveFocus;
	private String id;
	private Map<String, Debugger> catalog;
	private Map<Integer, ObservedConcept> scopeCatalog = new HashMap<>();
	private Map<String, IObservation> watches = new LinkedHashMap<>();

	public Debugger(String id, Map<String, Debugger> catalog, ISession session) {
		TextIO textIO = TextIoFactory.getTextIO();
		this.catalog = catalog;
		catalog.put(id, this);
		accept(textIO, session);
		this.id = id;
	}

	public static interface Watcher {

		/**
		 * Called when a new observation is created after adding it to the context.
		 */
		void newObservation(IObservation observation);

		/**
		 * Called when a new value is set for a state.
		 * 
		 * @param state
		 * @param locator
		 * @param value
		 */
		void newValue(IState state, ILocator locator, Object value);

		/**
		 * Called at each new temporal transition in the scheduler.
		 * 
		 * @param transition
		 */
		void newTransition(ITime transition);

		/**
		 * Called at each new temporal transition regarding a specific observation in
		 * the scheduler, always after newTransition(ITime).
		 * 
		 * @param transition
		 */
		void newTransition(ITime transition, IObservation target);

		/**
		 * Called whenever a new temporal slice of storage is allocated in a state.
		 * 
		 * @param state
		 * @param slice
		 */
		void newStateSlice(IState state, ITime slice);
	}

	@Override
	public void accept(TextIO textIO, ISession session) {

		this.session = session;
		this.terminal = textIO.getTextTerminal();
		if (this.terminal instanceof SwingTextTerminal) {
			// still kills the engine when closed. What to do?
			((SwingTextTerminal)this.terminal).getFrame().setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		}
		terminal.println("k.LAB debugger " + Version.CURRENT);
		terminal.println();

		boolean debug = true;
		while (debug) {
			String command = textIO.newStringInputReader().withDefaultValue(null).read("?");

			switch (command) {
			case "q":
				debug = false;
				break;
			case "h":
				help(terminal);
				break;
			case "ls":
				listContext();
				break;
			case "l":
				setLocator(this.prospectiveFocus);
				break;
			case "u":
				setLocator(null);
				break;
			default:
				execute(command);
				break;
			}

//			terminal.getProperties().put(PropertiesConstants.PROP_PROMPT_STYLE_CLASS, "textterm-white-space-pre");
//			terminal.executeWithPropertiesPrefix("pre", t -> {
//				t.println("-------------------------------------------------------");
//				t.println("  Temperature      Wind speed      Atmospheric pressure");
//				t.println("-------------------------------------------------------");
//				for (int i = 0; i < 20; i++) {
//					t.moveToLineStart();
//					delay(80);
//					t.print(getData());
//					delay(400);
//				}
//			});
//			terminal.println();
//			terminal.println();
//			terminal.println();

//			if (!textIO.newBooleanInputReader().withPropertiesPrefix("exit").withDefaultValue(true).read("Run again?"))
//				break;
//			terminal.resetToBookmark("MAIN");
		}

		catalog.remove(id);
		textIO.dispose();
	}

	private void listContext() {
		this.scopeCatalog.clear();
		if (getScope() == null) {
			terminal.println("No context.");
		}
		Map<ObservedConcept, IObservation> cat = getScope().getCatalog();
		int n = 1;
		for (ObservedConcept key : cat.keySet()) {
			this.scopeCatalog.put(n, key);
			terminal.println(String.format("%2d. %38s %s", n, cat.get(key).getObservable().getName(),
					describeValue(cat.get(key)), key.toString()));
			n++;
		}
	}

	private Object describeValue(IObservation observation) {
		String ret = "";
		if (this.focus != null && observation instanceof State) {
			Object[] values = ((State) observation).getTimeseries(this.focus);
			if (values.length == 1) {
				return Observations.INSTANCE.describeValue(values[0]);
			} else if (values.length > 1) {
				ret = "[";
				for (Object value : values) {
					ret += (ret.length() == 1 ? "" : ", ") + Observations.INSTANCE.describeValue(value);
				}
				ret += "]";
			}
		}
		return ret;
	}

	private IRuntimeScope getScope() {
		return session.getState().getCurrentContext() == null ? null
				: (IRuntimeScope) session.getState().getCurrentContext().getScope();
	}

	public void reset() {
		// TODO
		catalog.clear();
		terminal.println("Context cleared.");
	}

	private ISubject getContext() {
		return session.getState().getCurrentContext();
	}

	private void setLocator(ILocator locator) {
		if (locator == null) {
			this.focus = this.prospectiveFocus = null;
			terminal.getProperties().put(PropertiesConstants.PROP_PANE_TITLE, "k.LAB debugger");
			terminal.println("Watch location removed");
		} else if (this.prospectiveFocus != null) {
			this.focus = this.prospectiveFocus;
			if (locator instanceof IScale) {
				ISpace space = ((IScale) locator).getSpace();
				if (space instanceof Cell) {
					this.cellFocus = (Cell) space;
				}
			}
			terminal.getProperties().put(PropertiesConstants.PROP_PANE_TITLE, "k.LAB debugger @ " + focusToString());
			terminal.println("Enter 'u' to reset the watch location");
		}
	}

	private String focusToString() {
		return cellFocus == null ? (focus == null ? "" : focus.toString())
				: (cellFocus.getX() + "," + cellFocus.getY());
	}

	private String locatorToString(ILocator locator) {
		if (locator instanceof IScale) {
			ISpace space = ((IScale) locator).getSpace();
			if (space instanceof Cell) {
				return ((Cell) space).getX() + "," + ((Cell) space).getY();
			}
		}
		return locator.toString();
	}

	public void focus(ILocator locator, IObservation observation) {
		if (this.focus == null) {
			this.prospectiveFocus = locator;
			terminal.println("Enter 'l' to watch " + locatorToString(locator));
		}
	}

	private void execute(String string) {
		String[] cmds = string.split("\\s+");
		if (cmds.length > 0) {
			switch (cmds[0]) {
			case "w":
				watch(StringUtil.join(cmds, 1, " "));
				break;
			case "i":
				info(StringUtil.join(cmds, 1, " "));
				break;
			case "l":
				locate(StringUtil.join(cmds, 1, " "));
				break;
			case "b":
				breakpoint(cmds);
				break;
			}
		}
	}

	private void locate(String where) {

		// TODO if a @ is present, split on that and take init/end/<n> to mean the n-th
		// time in the temporal context, use that for
		// localization. Default is latest or init.

		IScale scale = getContext().getScale();
		IRuntimeScope transitionContext = getScope();

		if (getContext().getScale().isTemporallyDistributed()) {
			scale = (IScale) getContext().getScale().at(getContext().getScale().getTime().latest());
			transitionContext = getScope().locate(scale, getScope().getMonitor());
		}

		boolean found = false;
		IExpression code = Extensions.INSTANCE.compileExpression(where, transitionContext.getExpressionContext(),
				Extensions.DEFAULT_EXPRESSION_LANGUAGE, CompilerOption.ForcedScalar);
		for (ILocator locator : scale) {
			Object o = code.eval(transitionContext.localize(locator), transitionContext);
			if (o instanceof Boolean && ((Boolean) o)) {
				this.prospectiveFocus = locator;
				setLocator(locator);
				found = true;
				break;
			}
		}

		if (!found) {
			terminal.println("No match for find expression");
		}
	}

	private void breakpoint(String[] cmds) {
		// TODO Auto-generated method stub

	}

	private Collection<IObservation> getObservations(String target) {
		Set<IObservation> ret = new HashSet<>();
		if (NumberUtils.encodesInteger(target)) {
			ObservedConcept art = this.scopeCatalog.get(Integer.parseInt(target));
			if (art != null) {
				IObservation obs = getScope().getCatalog().get(art);
				if (obs != null) {
					ret.add(obs);
				}
			}
		} else if (target.contains(":")) {
			IObservable obs = Observables.INSTANCE.declare(target);
			for (IArtifact artifact : getScope().getArtifact(obs.getType())) {
				if (artifact instanceof IObservation) {
					ret.add((IObservation) artifact);
				}
			}
		} else {
			IArtifact artifact = getScope().getArtifact(target);
			if (artifact instanceof IObservation) {
				ret.add((IObservation) artifact);
			}
		}
		return ret;
	}

	private void watch(String target) {
		for (IObservation o : getObservations(target)) {
			watches.put(o.getObservable().getName(), o);
			refreshWatched();
		}
	}

	private void info(String target) {
		for (IObservation obs : getObservations(target)) {
			terminal.print(describeObservation(obs, false));
		}
	}

	private void refreshWatched() {
		for (String s : this.watches.keySet()) {
			terminal.printf("%20s %s%n", s, describeObservation(this.watches.get(s), true));
		}
	}

	private String describeObservation(IObservation observation, boolean brief) {
		String ret = Observables.INSTANCE.getDisplayName(observation.getObservable());
		if (brief) {
		} else {
			if (observation instanceof State) {
				ret += "\n";
				State state = (State) observation;
				if (state.getStorage() instanceof AbstractAdaptiveStorage) {
					AbstractAdaptiveStorage<?> storage = (AbstractAdaptiveStorage<?>) state.getStorage();
					ret += storage.getInfo(3);
				} else if (state.getStorage() instanceof KeyedStorage) {
					KeyedStorage<?> storage = (KeyedStorage<?>) state.getStorage();
					ret += storage.getInfo(3);
				}
			}

		}
		return ret;
	}

	private void help(TextTerminal<?> terminal) {
		// TODO Auto-generated method stub

	}

	/**
	 * Mocks a data retrieval operation.
	 * 
	 * @return the info text to be displayed
	 */
//	public String getData() {
//		double temperature = 15 + 10 * Math.random();
//		if (!useCelsius) {
//			temperature = temperature * 9 / 5 + 32;
//		}
//		String sTemperature = String.format(Locale.US, (useCelsius ? "%4.1f °C" : "%4.1f °F"), temperature);
//
//		double speed = 10 + 30 * Math.random();
//		if (!useKmh) {
//			speed /= 1.609344;
//		}
//		String sSpeed = String.format(Locale.US, (useKmh ? "%4.1f km/h" : "%4.1f mph"), speed);
//
//		double pressure = 990 + 30 * Math.random();
//		if (!useMbar) {
//			pressure /= 10;
//		}
//		String sPressure = String.format(Locale.US, (useMbar ? "%6.1f mbar" : "%6.2f kPa"), pressure);
//
//		return String.format(Locale.US, "%13s %15s %25s", sTemperature, sSpeed, sPressure);
//	}

//	public static void delay(long millis) {
//		try {
//			Thread.sleep(millis);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}

	public static void create(final ISession session, final Map<String, Debugger> debuggers) {
		new Thread() {
			@Override
			public void run() {
				new Debugger(NameGenerator.shortUUID(), debuggers, session);
			}
		}.start();

	}
}
