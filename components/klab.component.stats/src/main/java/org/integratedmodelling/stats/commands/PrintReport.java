package org.integratedmodelling.stats.commands;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.engine.extensions.Component;
import org.integratedmodelling.stats.StatsComponent;
import org.integratedmodelling.stats.reporting.StatsReport;
import org.integratedmodelling.stats.reporting.StatsReport.Format;
import org.integratedmodelling.stats.reporting.StatsReport.Frequency;
import org.integratedmodelling.stats.reporting.StatsReport.Target;

public class PrintReport implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {

		String whitelist = "";
		String blacklist = "";
		boolean html = call.getParameters().get("html", false);
		boolean errors = call.getParameters().get("errors", false);
		String span = call.getParameters().get("span", String.class);
		String users = call.getParameters().get("users", String.class);
		String engines = call.getParameters().get("engines", String.class);
		String observables = call.getParameters().get("observables", String.class);
		String apps = call.getParameters().get("apps", String.class);
		String models = call.getParameters().get("models", String.class);
		String groups = call.getParameters().get("groups", String.class);
		String operations = call.getParameters().get("operations", String.class);
		String resources = call.getParameters().get("resources", String.class);

		Component stc = Extensions.INSTANCE.getComponent(StatsComponent.ID);
		if (stc != null) {
			StatsComponent stats = stc.getImplementation(StatsComponent.class);
			if (stats != null) {

				List<Object> options = new ArrayList<>();

				for (Object option : (List<?>) call.getParameters().get("arguments")) {

					String o = option.toString().toLowerCase();

					switch (o) {
					case "observations":
						options.add(Target.Observations);
						break;
					case "models":
						options.add(Target.Models);
						break;
					case "contexts":
						options.add(Target.Contexts);
						break;
					case "engines":
						options.add(Target.Engines);
						break;
					case "observables":
						options.add(Target.Observables);
						break;
					case "operations":
						options.add(Target.Operations);
						break;
					case "resources":
						options.add(Target.Resources);
						break;
					case "downloads":
						options.add(Target.Downloads);
						break;
					case "users":
						options.add(Target.Users);
						break;
					case "apps":
						options.add(Target.Applications);
						break;
					case "yearly":
						options.add(Frequency.Yearly);
						break;
					case "monthly":
						options.add(Frequency.Monthly);
						break;
					case "weekly":
						options.add(Frequency.Weekly);
						break;
					case "daily":
						options.add(Frequency.Daily);
						break;
					case "hourly":
						options.add(Frequency.Hourly);
						break;

					default:

						// include/exclude user, URN, observable or group - all should be recognizable
						// based on target
						if (o.startsWith("+") /* or not - just mention something that's not the above */) {
							whitelist += (whitelist.isEmpty() ? "" : ",") + o.substring(1);
						} else if (o.startsWith("!")) {
							blacklist += (blacklist.isEmpty() ? "" : ",") + o.substring(1);
						} else {
							whitelist += (whitelist.isEmpty() ? "" : ",") + o;
						}
						break;
					}
				}

				if (!whitelist.isEmpty()) {
					options.add("whitelist");
					options.add(whitelist);
				}
				if (!blacklist.isEmpty()) {
					options.add("blacklist");
					options.add(blacklist);
				}

				StatsReport report = stats.createReport(options.toArray());

				if (errors) {
					report.reportErrors(true);
				}
				if (html) {
					report.setFormat(Format.Html);
				}
				if (span != null) {
					report.setSpan(span.split(","));
				}
				
				if (operations != null) {
					report.filterFor(Target.Operations, operations.split(","));
				}
				if (users != null) {
					report.filterFor(Target.Users, users.split(","));
				}
				if (models != null) {
					report.filterFor(Target.Models, models.split(","));
				}
				if (observables != null) {
					report.filterFor(Target.Observables, observables.split(","));
				}
				if (groups != null) {
					report.filterFor(Target.Groups, groups.split(","));
				}
				if (resources != null) {
					report.filterFor(Target.Resources, resources.split(","));
				}
				if (apps != null) {
					report.filterFor(Target.Applications, apps.split(","));
				}
				if (engines != null) {
					report.filterFor(Target.Engines, engines.split(","));
				}

				if (report != null) {
					String ret = report.compile();
					if (html) {
						// TODO translate and launch in browser
						return "Browser launched";
					}
					return ret;
				}

			}
		}

		return "No reports available: statistics not initialized";
	}

}
