package org.integratedmodelling.mca.services;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubjectiveState;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.mca.api.IAlternative;
import org.integratedmodelling.mca.api.ICriterion;
import org.integratedmodelling.mca.api.IStakeholder;
import org.integratedmodelling.mca.core.MCAAssessment;
import org.integratedmodelling.mca.core.MCAContext;
import org.integratedmodelling.mca.core.MCAContext.Specification;
import org.integratedmodelling.mca.core.Results;

public class ConcordanceResolver implements IResolver<IState>, IExpression {

	int levels = 5;
	MCAContext mcaContext;
	IGeometry geometry;

	@Override
	public IGeometry getGeometry() {
		return geometry;
	}

	@Override
	public Type getType() {
		return Type.NUMBER;
	}

	@Override
	public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {
		ConcordanceResolver ret = new ConcordanceResolver();
		ret.levels = parameters.get("levels", 5);
		return ret;
	}

	@Override
	public IState resolve(IState ret, IComputationContext context) throws KlabException {

		this.geometry = context.getScale();
		MCAContext mcaContext = new MCAContext(ret.getObservable(), (IRuntimeContext) context, ITime.INITIALIZATION, levels);
		
		if (mcaContext.isComputable()) {
			
			MCAAssessment mCAAssessment = new MCAAssessment();
			for (ICriterion criterion : mcaContext.getCriteria()) {
				mCAAssessment.declareCriterion(criterion.getName(), criterion.getDataType(), criterion.getType());
			}
			for (IAlternative alternative : mcaContext.getAlternatives()) {
				mCAAssessment.declareAlternative(alternative.getId());
				for (ICriterion criterion : mcaContext.getCriteria()) {
					mCAAssessment.setCriterionValue(alternative.getId(), criterion.getName(), alternative.getValue(criterion.getName()));
				}
			}
			for (IStakeholder stakeholder : mcaContext.getStakeholders()) {

				/**
				 * Stakeholder switching logics. TODO: this will not affect the criteria
				 * if they are also subjective.
				 */
				if (!(mcaContext.getSpecification() == Specification.InlineSingle)) {
					// if state is not an observed set, wrap it into one
					if (!(ret instanceof ISubjectiveState)) {
						ret = ret.reinterpret(/* TODO */ null);
					}
					// set the observer in the stakeholder in the set so that all further assignments reflect its perspective
					((ISubjectiveState)ret).setObserver(stakeholder.getSubject());
				}
				
				mCAAssessment.resetWeights();
				
				for (String crit : stakeholder.getWeights().keySet()) {
					mCAAssessment.setCriterionWeight(crit, stakeholder.getWeights().get(crit));
				}
				
				// invert to turn weights into priorities
				mCAAssessment.invertWeights();
				
				// run
				Results results = mCAAssessment.run(context.getMonitor());
				
				// TODO set directives for reporting
				System.out.println(results.dump());

				// have the MCAContext build the resulting artifact
				mcaContext.distributeResults(results, ret);
			
			}
			
		} else {
			context.getMonitor().warn(
					"MCA is not computable: must have 1+ stakeholders, 2+ valid criteria and 2+ valid alternatives");
		}

		return ret;
	}

}
