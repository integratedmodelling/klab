package org.integratedmodelling.klab.engine.debugger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IInspector;
import org.integratedmodelling.klab.utils.Pair;

public class Inspector implements IInspector {

    class TriggerImpl implements Trigger {

        Asset asset;
        Metric metric;
        Event event;
        Action action = Action.CONTINUE;
        // the specific object, if any, that will trigger this - could be a model
        // object, a URN, a state value
        Object self;
        // condition to match for trigger: if not empty, the fields of the trigger will be
        // accessible with their names
        Object condition;
        List<Pair<ValueOperator, Object>> operators = new ArrayList<>();

        // data for matching, used only in triggered events for ease of comparison
        List<Object> data = new ArrayList<>();
        BiConsumer<Trigger, IContextualizationScope> handler;

        @Override
        public Asset getAsset() {
            return asset;
        }
        @Override
        public Metric getMetric() {
            return metric;
        }
        @Override
        public Event getEvent() {
            return event;
        }
        @Override
        public Object getSubject() {
            return self;
        }
        @Override
        public Object getTriggerValue() {
            return condition;
        }

        @Override
        public List<Object> getData() {
            return data;
        }

        TriggerImpl() {
        }

        TriggerImpl(TriggerImpl model, Object self, Object condition) {
            this.asset = model.asset;
            this.metric = model.metric;
            this.event = model.event;
            this.action = model.action;
            this.self = self;
            this.condition = condition;
        }
        public boolean matches(TriggerImpl trigger) {
            if (trigger.asset != null && (this.asset == null || this.asset != trigger.asset)) {
                return false;
            }
            if (trigger.event != null && (this.event == null || this.event != trigger.event)) {
                return false;
            }
            return true;
        }
    }

    List<TriggerImpl> triggers = new ArrayList<>();

    @Override
    public void setTrigger(BiConsumer<Trigger, IContextualizationScope> handler, Object... triggerArguments) {
        TriggerImpl trigger = new TriggerImpl();
        Pair<ValueOperator, Object> op = null;
        for (Object o : triggerArguments) {
            if (o instanceof Action) {
                trigger.action = (Action) o;
            } else if (o instanceof Event) {
                trigger.event = (Event) o;
            } else if (o instanceof Metric) {
                trigger.metric = (Metric) o;
            } else if (o instanceof Asset) {
                trigger.asset = (Asset) o;
            } else if (o instanceof ValueOperator) {
                op = new Pair<>((ValueOperator) o, null);
                if (((ValueOperator) o).nArguments == 0) {
                    trigger.operators.add(op);
                    op = null;
                }
            } else if (op == null) {
                trigger.self = op;
            } else {
                op.setSecond(o);
                trigger.operators.add(op);
            }
        }
        trigger.handler = handler;
        triggers.add(trigger);
    }

    @Override
    public void trigger(IContextualizationScope scope, Object... triggerArguments) {

        if (triggerArguments == null || triggers.isEmpty()) {
            return;
        }

        TriggerImpl trigger = new TriggerImpl();
        Pair<ValueOperator, Object> op = null;
        for (Object o : triggerArguments) {
            if (o instanceof Action) {
                trigger.action = (Action) o;
            } else if (o instanceof Event) {
                trigger.event = (Event) o;
            } else if (o instanceof Metric) {
                trigger.metric = (Metric) o;
            } else if (o instanceof Asset) {
                trigger.asset = (Asset) o;
            } else {
                if (trigger.self == null) {
                    trigger.self = o;
                }
                trigger.data.add(o);
            }
        }

        for (TriggerImpl t : triggers) {
            if (t.matches(trigger)) {
                t.handler.accept(trigger, scope);
            }
        }

    }

}
