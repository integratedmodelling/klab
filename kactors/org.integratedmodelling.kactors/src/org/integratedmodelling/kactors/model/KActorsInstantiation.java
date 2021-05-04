package org.integratedmodelling.kactors.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Visitor;
import org.integratedmodelling.kactors.api.IKActorsStatement.Instantiation;
import org.integratedmodelling.kactors.kactors.ActorInstantiation;
import org.integratedmodelling.kactors.kactors.Match;
import org.integratedmodelling.kactors.model.KActorsActionCall.ActionDescriptor;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Pair;

public class KActorsInstantiation extends KActorsStatement implements Instantiation {

    String behavior;
    KActorsArguments arguments = null;
    private List<ActionDescriptor> actions = new ArrayList<>();

    private String actorBaseName = null;

    public KActorsInstantiation(ActorInstantiation statement, String tag, KActorCodeStatement parent) {
        super(statement, parent, Type.INSTANTIATION);
        this.behavior = statement.getBehavior();
        if (tag != null) {
            this.actorBaseName = tag.substring(1);
        }
        if (statement.getParameters() != null) {
            this.arguments = new KActorsArguments(statement.getParameters());
            if (this.actorBaseName == null && this.arguments.getData().containsKey("tag")) {
                this.actorBaseName = this.arguments.getData().get("tag").toString();
            }
        }

        if (this.actorBaseName == null) {
            this.actorBaseName = "a" + NameGenerator.shortUUID();
        }

        if (statement.getActions() != null) {
            if (statement.getActions().getStatement() != null) {
                ActionDescriptor action = new ActionDescriptor();
                action.match = KActorsValue.anytrue();
                action.action = KActorsStatement.create(statement.getActions().getStatement(), this);
                actions.add(action);
            } else if (statement.getActions().getStatements() != null) {
                ActionDescriptor action = new ActionDescriptor();
                action.match = KActorsValue.anytrue();
                action.action = new KActorsConcurrentGroup(Collections.singletonList(statement.getActions().getStatements()),
                        this);
                actions.add(action);
            } else if (statement.getActions().getMatch() != null) {
                ActionDescriptor action = new ActionDescriptor();
                action.match = new KActorsValue(statement.getActions().getMatch(), this);
                action.action = new KActorsConcurrentGroup(Collections.singletonList(statement.getActions().getMatch().getBody()),
                        this);
                actions.add(action);
            } else if (statement.getActions().getMatches() != null) {
                for (Match match : statement.getActions().getMatches()) {
                    ActionDescriptor action = new ActionDescriptor();
                    action.match = new KActorsValue(match, this);
                    action.action = new KActorsConcurrentGroup(Collections.singletonList(match.getBody()), this);
                    actions.add(action);
                }
            }
        }
    }

    @Override
    public String getBehavior() {
        return behavior;
    }

    @Override
    public IParameters<String> getArguments() {
        return arguments;
    }

    @Override
    public String getActorBaseName() {
        return actorBaseName;
    }

    @Override
    public List<Pair<IKActorsValue, IKActorsStatement>> getActions() {
        List<Pair<IKActorsValue, IKActorsStatement>> ret = new ArrayList<>();
        for (ActionDescriptor ad : actions) {
            ret.add(new Pair<>(ad.match, ad.action));
        }
        return ret;
    }

    public void setActorBaseName(String actorBaseName) {
        this.actorBaseName = actorBaseName;
    }
    

    @Override
    protected void visit(IKActorsAction action, Visitor visitor) {
        for (ActionDescriptor ad : actions) {
            ad.visit(action, this, visitor);
        }
        super.visit(action, visitor);
    }
    

}
