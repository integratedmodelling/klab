package org.integratedmodelling.kactors.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Visitor;
import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.kactors.KeyValuePair;
import org.integratedmodelling.kactors.kactors.ParameterList;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;

/**
 * Arguments with possible unnamed parameters, actually named _p1, _p2.... Also keeps a set of any
 * arguments passed through keyed metadata (which enter the hash anyway) for later checking.
 * 
 * @author Ferd
 *
 */
public class KActorsArguments extends Parameters<String> {

    Set<String> metadataKeys = new HashSet<>();

    public KActorsArguments() {
    }

    /*
     * Should admit keys with values - if key is followed by non-key, it's its value. All unnamed
     * parameters must be at the beginning.
     */
    public KActorsArguments(ParameterList parameters) {

        // first element is a key, second a value. If first is null, it's an unnamed
        // parameter
        List<Pair<String, Object>> pairs = new ArrayList<>();
        boolean key = false;
        for (KeyValuePair pair : parameters.getPairs()) {
            if (pair.getTag() != null) {
                pairs.add(new Pair<>("tag", pair.getTag().substring(1)));
            } else if (pair.getKey() != null) {

                // add new key; leave open for next value if it's a :key form
                key = !pair.getKey().startsWith("!");
                pairs.add(new Pair<>(pair.getKey().substring(1), new KActorsValue(!pair.getKey().startsWith("!"), null)));
                metadataKeys.add(pair.getKey().substring(1));

            } else if (pair.getName() == null) {
                if (key) {
                    // had key before:
                    pairs.get(pairs.size() - 1).setSecond(new KActorsValue(pair.getValue(), null));
                    key = false;
                } else {
                    pairs.add(new Pair<>(null, new KActorsValue(pair.getValue(), null)));
                }
            } else {
                put(pair.getName(), new KActorsValue(pair.getValue(), null));
            }
        }

        for (Pair<String, Object> pair : pairs) {
            if (pair.getFirst() == null) {
                putUnnamed(pair.getSecond());
            } else {
                put(pair.getFirst(), pair.getSecond());
            }
        }

    }

    public Set<String> getMetadataKeys() {
        return metadataKeys;
    }

    public void visit(IKActorsAction action, IKActorsStatement statement, Visitor visitor) {
        // TODO Auto-generated method stub
        for (Object value : values()) {
            if (value instanceof KActorsValue) {
                KActorsStatement.visitValue(visitor, (KActorsValue)value, statement, action);
            }
        }
    }

}
