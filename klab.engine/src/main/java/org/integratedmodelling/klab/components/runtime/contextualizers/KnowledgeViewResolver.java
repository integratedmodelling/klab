package org.integratedmodelling.klab.components.runtime.contextualizers;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IViewModel;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IKnowledgeView;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Pair;

public class KnowledgeViewResolver implements IResolver<IArtifact>, IExpression {

	static final public String FUNCTION_ID = "klab.runtime.compileview";

	private IViewModel view;

	// don't remove - only used as expression
	public KnowledgeViewResolver() {
	}

	public KnowledgeViewResolver(String viewName) {
		this.view = (IViewModel) Resources.INSTANCE.getModelObject(viewName);
	}

	public static IServiceCall getServiceCall(IViewModel view) {
		return KimServiceCall.create(FUNCTION_ID, "view", view.getName());
	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
		return new KnowledgeViewResolver(parameters.get("view", String.class));
	}

	@Override
	public IArtifact.Type getType() {
		return Type.VOID;
	}

	@Override
	public IArtifact resolve(IArtifact ret, IContextualizationScope scope) throws KlabException {

		boolean isInitialization = scope.getScale().getTime() == null
				|| scope.getScale().getTime().is(ITime.Type.INITIALIZATION);

		if (isInitialization) {

			/*
			 * run only when we have no schedule or we are scheduled to run at
			 * initialization (i.e. init is set but we are not comparing the init state with
			 * others). All other cases are dealt with by the scheduler, and if we get here
			 * at any time besides initialization it was the scheduler who sent us, so just
			 * run.
			 */
			IViewModel.Schedule schedule = this.view.getSchedule();
			boolean run = schedule == null || (schedule.isInit() && !(schedule.isEnd() || schedule.isStart()));
			if (!run) {
				return ret;
			}
		}

		/*
		 * the passed artifact is null here, so find the target of the view and pass that
		 */
		IObservable targetObservable = this.view.getTargetObservable();
		if (targetObservable == null) {
			throw new KlabValidationException("view " + this.view.getName() + " does not declare a target");
		}
		Pair<String, IArtifact> artifact = ((IRuntimeScope)scope).findArtifact(targetObservable);
		if (artifact == null) {
			throw new KlabValidationException("view " + this.view.getName() + " declares a target that cannot be found in the context");
		}
		
		IKnowledgeView result = this.view.compileView((IObservation)artifact.getSecond(), scope);

		((IRuntimeScope) scope).addView(result);
		return result;
	}
}
