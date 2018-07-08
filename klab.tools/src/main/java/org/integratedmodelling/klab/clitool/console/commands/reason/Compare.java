package org.integratedmodelling.klab.clitool.console.commands.reason;

import java.util.ArrayList;
import java.util.List;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.clitool.api.ICommand;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class Compare implements ICommand {

  @Override
  public Object execute(IServiceCall call, ISession session) throws KlabValidationException {

    String ret = "";
    List<IConcept> concepts = new ArrayList<>();

    for (Object p : (List<?>) call.getParameters().get("arguments")) {
      concepts.add(Concepts.c(p.toString()));
    }

    for (int i = 0; i < concepts.size(); i++) {
      for (int j = 0; j < concepts.size(); j++) {
        if (j != i) {
          ret += (ret.isEmpty() ? "" : "\n") + compare(concepts.get(i), concepts.get(j));
        }
      }
    }

    return ret;
  }

  private String compare(IConcept c1, IConcept c2) {

    String ret = c1 + " vs. " + c2 + ":\n";

    if (c1.is(Type.OBSERVABLE) && c2.is(Type.OBSERVABLE)) {
      ret += "   Observable compatibility: "
          + (Observables.INSTANCE.isCompatible(c1, c2) ? "compatible" : "not compatible") + "\n";
    } else {
      ret += "   Observable compatibility: not observables\n";
    }

    IConcept common = Concepts.INSTANCE.getLeastGeneralCommonConcept(c1, c2);

    ret +=
        "   Least general common parent: " + (common == null ? "none" : common.toString()) + "\n";

    if (common != null) {
      ret += "      " + c1 + ": asserted distance from common = " + Concepts.INSTANCE.getAssertedDistance(c1, common)
          + "\n";
      ret += "      " + c2 + ": asserted distance from common = " + Concepts.INSTANCE.getAssertedDistance(c2, common)
          + "\n";
    }

    int specificity =
        Concepts.INSTANCE.compareSpecificity(c1, c2, c1.is(Type.TRAIT) && c2.is(Type.TRAIT));

    ret +=
        "   Specificity: " + c1
            + (specificity == Integer.MIN_VALUE ? " has nothing in common with "
                : (specificity > 0 ? " is more specific than "
                    : (specificity == 0 ? " is at the same level of " : " is less specific than ")))
            + c2 + "\n";

    return ret;
  }

}
