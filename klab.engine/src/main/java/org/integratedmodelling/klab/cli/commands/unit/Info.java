package org.integratedmodelling.klab.cli.commands.unit;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * Print information about a unit, or an error if the unit is unrecognized.
 * 
 * @author Ferd
 *
 */
public class Info implements ICommand {

    @Override
    public Object execute(IServiceCall call, ISession session) {
        String ret = "";
        for (Object urn : call.getParameters().get("arguments", java.util.List.class)) {
            try {
                ret += unitInfo(urn.toString()) + "\n";
            } catch (UnsupportedEncodingException e) {
                throw new KlabException(e);
            }
        }
        return ret;
    }

    private String unitInfo(String unitInfo) throws UnsupportedEncodingException {

        Unit unit = Unit.create(unitInfo);
        
        if (unit == null) {
            return "Invalid unit: " + unitInfo;
        }
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (PrintStream ps = new PrintStream(baos, true, "UTF-8")) {
            Units.INSTANCE.dump(unit, ps);
        }
        
        return baos.toString();
        
        
    }
}
