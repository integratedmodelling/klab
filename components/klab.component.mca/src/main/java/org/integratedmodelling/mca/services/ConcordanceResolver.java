package org.integratedmodelling.mca.services;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.mca.api.IAlternative;
import org.integratedmodelling.mca.api.ICriterion;
import org.integratedmodelling.mca.api.IStakeholder;
import org.integratedmodelling.mca.core.MCAAssessment;
import org.integratedmodelling.mca.core.MCAContext;
import org.integratedmodelling.mca.core.MCAContext.Specification;
import org.integratedmodelling.mca.core.Results;
import org.integratedmodelling.mca.model.Alternative;
import org.integratedmodelling.mca.model.Stakeholder;

public class ConcordanceResolver extends AbstractContextualizer implements IResolver<IState>, IExpression {

    int levels = 5;
    MCAContext mcaContext;
    IGeometry geometry;

    @Override
    public Type getType() {
        return Type.NUMBER;
    }

    @Override
    public Object eval(IContextualizationScope context, Object... parameters) throws KlabException {
        ConcordanceResolver ret = new ConcordanceResolver();
        ret.levels = Parameters.create(parameters).get("levels", 5);
        return ret;
    }

    @Override
    public IState resolve(IState ret, IContextualizationScope context) throws KlabException {

        this.geometry = context.getScale();
        MCAContext mcaContext = new MCAContext(ret.getObservable(),
                (IRuntimeScope) context/* , Time.INITIALIZATION */, levels);

        if (mcaContext.isComputable()) {

            MCAAssessment mCAAssessment = new MCAAssessment();
            for (ICriterion criterion : mcaContext.getCriteria()) {
                mCAAssessment.declareCriterion(criterion.getName(), criterion.getDataType(), criterion.getType());
            }

            for (IAlternative alternative : mcaContext.getAlternatives()) {
                if (!alternative.isValid()) {
                    continue;
                }
                mCAAssessment.declareAlternative(alternative.getId());
                for (ICriterion criterion : mcaContext.getCriteria()) {
                    mCAAssessment.setCriterionValue(alternative.getId(), criterion.getName(),
                            ((Alternative) alternative).getValue(criterion.getName()));
                }
            }

            for (IStakeholder stakeholder : mcaContext.getStakeholders()) {

                /**
                 * Stakeholder switching logics. TODO: this will not affect the criteria if they are
                 * also subjective.
                 */
                if (!(mcaContext.getSpecification() == Specification.InlineSingle)) {
                    // if state is not an observed set, wrap it into one
//                    if (!(ret instanceof ISubjectiveState)) {
//                        ret = ret.reinterpret(stakeholder.getSubject());
//                    }
                    // set the observer in the stakeholder in the set so that all further
                    // assignments reflect
                    // its perspective
                    ((Observation) ret).setObserver(stakeholder.getSubject());
                }

                mCAAssessment.resetWeights();

                for (String crit : ((Stakeholder) stakeholder).getWeights().keySet()) {
                    mCAAssessment.setCriterionWeight(crit, ((Stakeholder) stakeholder).getWeights().get(crit));
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
            context.getMonitor()
                    .warn("MCA is not computable: must have 1+ stakeholders, 2+ valid criteria and 2+ valid alternatives");
        }

        return ret;
    }

}
