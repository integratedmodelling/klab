package org.integratedmodelling.klab.cli.commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import org.h2.tools.Console;
import org.h2.tools.Server;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.persistence.h2.H2Database;

public class Db implements ICommand {

    @Override
    public Object execute(IServiceCall call, ISession session) {

        String ret = "";

        if (((List<?>) call.getParameters().get("arguments")).size() > 0) {

            String arg = ((List<?>) call.getParameters().get("arguments")).get(0).toString();
            String kbox = null;
            if (((List<?>) call.getParameters().get("arguments")).size() > 1) {
                kbox = ((List<?>) call.getParameters().get("arguments")).get(1).toString();
            }

            switch(arg) {
            case "console":
                if (kbox != null) {
                    try {
                        open(H2Database.getDatastores().get(kbox));
                    } catch (Exception e) {
                        throw new KlabInternalErrorException(e);
                    }

                }
                break;
            case "query":
                break;
            case "count":
                break;
            }

        } else {
            for (String kbox : H2Database.getDatastores().keySet()) {
                ret += kbox + ": " + H2Database.getDatastores().get(kbox) + "\n";
            }
        }

        return ret;
    }

    public void open(H2Database database) throws Exception {

        // open the in-memory database within a VM
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection(database.getUrl(), "sa", "sa");

        // start a TCP server
        // (either before or after opening the database)
        Server server = Server.createTcpServer().start();

        // now start the H2 Console here or in another process using
        Console.main("-url", database.getUrl(), "-user", "sa", "-password", "sa");

        System.out.println("Press [Enter] to stop.");
        System.in.read();

        System.out.println("Stopping server and closing the connection");
        server.stop();
        conn.close();
    }

}
