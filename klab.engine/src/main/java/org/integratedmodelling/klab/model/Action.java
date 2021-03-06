package org.integratedmodelling.klab.model;

import java.util.List;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimAction;
import org.integratedmodelling.kim.api.IKimAction.Trigger;
import org.integratedmodelling.kim.api.IKimAction.Type;
import org.integratedmodelling.klab.api.model.IAction;

/*
 * FIXME at this point there is no need for a model-side Action - could simply use the k.IM peer
 */
class Action implements IAction {

    IKimAction delegate;

    public Action(IKimAction action) {
        this.delegate = action;
    }

    @Override
    public Trigger getTrigger() {
        return this.delegate.getTrigger();
    }

    @Override
    public Type getType() {
        return this.delegate.getType();
    }

    @Override
    public List<IContextualizable> getComputation() {

      return this.delegate.getComputation();
        
        // TODO change this to have the runtime provider compile the entire thing
//        if (this.delegate.getActionExpression() != null) {
//
//            KimServiceCall call = new KimServiceCall(IRuntimeProvider.EXECUTE_FUNCTION_ID);
//
//            call.getParameters().put(IRuntimeProvider.EXECUTE_FUNCTION_PARAMETER_CODE, this.delegate
//                    .getActionExpression());
//
//            if (this.delegate.getConditionExpression() != null) {
//                call.getParameters()
//                        .put(this.delegate.isConditionNegative()
//                                ? IRuntimeProvider.EXECUTE_FUNCTION_PARAMETER_NEGATIVE_CONDITION
//                                : IRuntimeProvider.EXECUTE_FUNCTION_PARAMETER_CONDITION, this.delegate
//                                        .getConditionLiteral());
//            }
//
//            if (this.delegate.getLanguage() != null) {
//                call.getParameters().put(IRuntimeProvider.EXECUTE_FUNCTION_PARAMETER_LANGUAGE, this.delegate
//                        .getLanguage());
//            }
//
//            ret.add(call);
//
//        }
//        return ret;
    }

}
