package org.integratedmodelling.kactors.utils;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Visitor;
import org.integratedmodelling.kactors.api.IKActorsCodeStatement;
import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.api.IKActorsValue.Type;
import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.StringUtil;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * Helper class that will extract all non-localized strings from a behavior and produce a map with a
 * suggested variable name and the original value. Also collects the localized keys in the code and
 * returns them.
 * 
 * @author Ferd
 *
 */
public class KActorsLocalizer {

    IKActorsBehavior behavior;
    Set<String> localized = new LinkedHashSet<>();
    BiMap<String, String> unlocalized = HashBiMap.create();

    public KActorsLocalizer(IKActorsBehavior behavior) {
        this.behavior = behavior;
        process();
    }

    /**
     * Return all the localized keys found in the source code (without the leading #)
     * 
     * @return
     */
    public Collection<String> getLocalizedKeys() {
        return localized;
    }

    /**
     * Return all the unlocalized strings found in the source code. Proposed keys for each string
     * have been generated and can be retrieved using {@link #getProposedKey(String)}.
     * 
     * @return
     */
    public Collection<String> getUnlocalizedStrings() {
        return unlocalized.values();
    }

    /**
     * Return the proposed key generated for the passed unlocalized string in the code.
     * 
     * @param unlocalizedString
     * @return
     */
    public String getProposedKey(String unlocalizedString) {
        return unlocalized.inverse().get(unlocalizedString);
    }

    /**
     * Return all the proposed keys generated for the unlocalized strings in the code.
     * 
     * @return
     */
    public Collection<String> getProposedKeys() {
        return unlocalized.keySet();
    }

    /**
     * Return the string correspondent to the passed proposed key.
     * 
     * @param proposedKey
     * @return
     */
    public String getString(String proposedKey) {
        return unlocalized.get(proposedKey);
    }

    public void process() {

        behavior.visit(new Visitor(){

            @Override
            public void visitValue(IKActorsValue value, IKActorsStatement statement, IKActorsAction action) {
                if (value.getType() == Type.LOCALIZED_KEY) {
                    localized.add(value.getStatedValue().toString());
                } else if (value.getType() == Type.STRING) {
                    if (!unlocalized.containsValue(value.getStatedValue().toString())) {
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
                            while(unlocalized.containsKey(id) || localized.contains(id)) {
                                if (!unlocalized.containsKey(id + "_" + cnt) && !localized.contains(id + "_" + cnt)) {
                                    id = id + "_" + cnt;
                                    break;
                                }
                                cnt++;
                            }
                            unlocalized.put(id, value.getStatedValue().toString());
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
                if (value instanceof String) {
                    checkKey((String) value);
                }
            }

            @Override
            public void visitAction(IKActorsAction action) {
                for (IKimAnnotation annotation : action.getAnnotations()) {
                    for (Object o : annotation.getParameters().values()) {
                        if (o instanceof IKActorsValue) {
                            visitValue((IKActorsValue)o, action.getCode(), action);
                        }
                    }
                }
            }

            @Override
            public void visitMetadata(IKActorsCodeStatement kActorCodeStatement, String key, Object value) {
                if (value instanceof String) {
                    checkKey((String) value);
                }
            }
        });
    }

    private void checkKey(String string) {
        if (string != null && string.startsWith("#") && StringUtil.isUppercase(string.substring(1))) {
            localized.add(string.substring(1));
        }
    }

}
