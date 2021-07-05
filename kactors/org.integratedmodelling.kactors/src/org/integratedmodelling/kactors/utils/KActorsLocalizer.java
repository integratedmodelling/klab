package org.integratedmodelling.kactors.utils;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Visitor;
import org.integratedmodelling.kactors.api.IKActorsValue.Type;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.StringUtil;
import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.api.IKActorsValue;

/**
 * Extract all non-localized strings from a behavior and produce a map with a suggested variable
 * name and the original value.
 * 
 * @author Ferd
 *
 */
public class KActorsLocalizer {

    IKActorsBehavior behavior;

    public KActorsLocalizer(IKActorsBehavior behavior) {
        this.behavior = behavior;
    }

    public Map<String, String> getUnlocalizedStrings() {
        Map<String, String> ret = new HashMap<>();
        Map<String, String> ter = new HashMap<>();
        behavior.visit(new Visitor(){

            @Override
            public void visitValue(IKActorsValue value, IKActorsStatement statement, IKActorsAction action) {
                if (value.getType() == Type.STRING) {
                    if (!ter.containsKey(value.getStatedValue().toString())) {
                        String packed = StringUtil.pack(value.getStatedValue().toString());
                        if (!packed.isEmpty()) {
                            /*
                             * get until the 4th space or the end of the string
                             */
                            String[] lines = packed.split("\\r?\\n");
                            packed = lines[0];
                            if (packed.toLowerCase().startsWith("http")) {
                                packed = MiscUtilities
                                        .getURLBaseName(packed.endsWith("/") ? packed.substring(0, packed.length() - 2) : packed);
                            }
                            int i = 0;
                            String id = "";
                            for (String s : packed.split("\\W+")) {
                                if (s.isEmpty()) {
                                    continue;
                                }
                                if (i == 4) {
                                    break;
                                }
                                id += (id.isEmpty() ? "" : "_") + s.toUpperCase();
                                i++;
                            }
                            int cnt = 1;
                            while(ret.containsKey(id)) {
                                if (!ret.containsKey(id + "_" + cnt)) {
                                    id = id + "_" + cnt;
                                    break;
                                }
                                cnt++;
                            }
                            ret.put(id, value.getStatedValue().toString());
                            ter.put(value.getStatedValue().toString(), id);
                        }
                    }
                }
            }

            @Override
            public void visitStatement(IKActorsAction action, IKActorsStatement statement) {
                // TODO Auto-generated method stub

            }

            @Override
            public void visitPreamble(String variable, Object value) {
                // TODO Auto-generated method stub

            }

            @Override
            public void visitAction(IKActorsAction action) {
                // TODO Auto-generated method stub

            }
        });
        return ret;
    }

}
