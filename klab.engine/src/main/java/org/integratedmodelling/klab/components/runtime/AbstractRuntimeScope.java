package org.integratedmodelling.klab.components.runtime;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabContextualizationException;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Parameters;

/**
 * Provides the state needed at runtime that was previously in (stateful) actuators. All other
 * functionalities are in the derived classes.
 * 
 * @author Ferd
 *
 */
public abstract class AbstractRuntimeScope extends Parameters<String> implements IRuntimeScope {

    Scale resolutionScale;
    ResolutionScope resolutionScope;
    boolean autoStartTransitions = false;
    Map<IActuator, IScale> partialScales;

    protected AbstractRuntimeScope(IResolutionScope resolutionScope) {
        this.resolutionScope = (ResolutionScope) resolutionScope;
        partialScales = Collections.synchronizedMap(new HashMap<>());
    }

    protected AbstractRuntimeScope(AbstractRuntimeScope scope) {
        this.putAll(scope);
        this.resolutionScale = scope.resolutionScale;
        this.resolutionScope = scope.resolutionScope;
        this.autoStartTransitions = scope.autoStartTransitions;
        this.partialScales = scope.partialScales;
    }

    @Override
    public IResolutionScope getResolutionScope() {
        return resolutionScope;
    }
    
    @Override
    public void setMergedScale(IActuator actuator, IScale scale) {
        this.partialScales.put(actuator, scale);
    }

    @Override
    public IScale getMergedScale(IActuator actuator) {
        return this.partialScales.get(actuator);
    }

    @Override
    public IScale getResolutionScale() {
        
        if (this.resolutionScale == null && resolutionScope != null) {
            this.resolutionScale = resolutionScope.getScale();
            if (hasOccurrents && this.resolutionScale.getTime() != null) {
                ITime time = this.resolutionScale.getTime();
                if (time.isGeneric() || time.size() == 1) {

                    if (time.getStart() == null || time.getEnd() == null) {
                        throw new KlabContextualizationException(
                                "cannot contextualize occurrents (processes and events) without a specified temporal extent");
                    }

                    /*
                     * Turn time into a 1-step grid (so size = 2). The scheduler will do the rest.
                     */
                    this.resolutionScale = Scale.substituteExtent(this.resolutionScale,
                            ((Time) time).upgradeForOccurrents());
                }

                // set the dataflow to autostart transitions if we only have one
                if (this.resolutionScale.getTime().size() >= 2) {
                    autoStartTransitions = true;
                }
            }
        }
        return this.resolutionScale;
    }

}
