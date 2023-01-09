package org.integratedmodelling.stats.commands;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.services.IConfigurationService;
import org.integratedmodelling.klab.engine.extensions.Component;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.stats.StatsComponent;
import org.integratedmodelling.stats.reporting.StatsReport;
import org.integratedmodelling.stats.reporting.StatsReport.Format;
import org.integratedmodelling.stats.reporting.StatsReport.Frequency;
import org.integratedmodelling.stats.reporting.StatsReport.Target;
import org.springframework.web.bind.annotation.PathVariable;

public class PrintReport implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {

		String whitelist = "";
		String blacklist = "";
		boolean html = call.getParameters().get("html", false);
		boolean markdown = call.getParameters().get("markdown", false);
		boolean errors = call.getParameters().get("errors", false);
		boolean cost = call.getParameters().get("cost", false);

		String span = call.getParameters().get("span", String.class);
		String users = call.getParameters().get("users", String.class);
		String engines = call.getParameters().get("engines", String.class);
		String observables = call.getParameters().get("observables", String.class);
		String apps = call.getParameters().get("apps", String.class);
		String models = call.getParameters().get("models", String.class);
		String groups = call.getParameters().get("groups", String.class);
		String operations = call.getParameters().get("operations", String.class);
		String resources = call.getParameters().get("resources", String.class);

		INodeIdentity snode = Klab.INSTANCE.getStatisticsServer();

		List<Object> targets = new ArrayList<>();
		for (Object option : (List<?>) call.getParameters().get("arguments")) {

			String o = option.toString().toLowerCase();

			switch (o) {
			case "observations":
				targets.add(Target.Observations);
				break;
			case "models":
				targets.add(Target.Models);
				break;
			case "contexts":
				targets.add(Target.Contexts);
				break;
			case "engines":
				targets.add(Target.Engines);
				break;
			case "observables":
				targets.add(Target.Observables);
				break;
			case "operations":
				targets.add(Target.Operations);
				break;
			case "resources":
				targets.add(Target.Resources);
				break;
			case "downloads":
				targets.add(Target.Downloads);
				break;
			case "users":
				targets.add(Target.Users);
				break;
			case "apps":
				targets.add(Target.Applications);
				break;
			case "yearly":
				targets.add(Frequency.Yearly);
				break;
			case "monthly":
				targets.add(Frequency.Monthly);
				break;
			case "weekly":
				targets.add(Frequency.Weekly);
				break;
			case "daily":
				targets.add(Frequency.Daily);
				break;
			case "hourly":
				targets.add(Frequency.Hourly);
				break;
			default:
				throw new KlabIllegalArgumentException("unknown target " + o);
			}
		}

		if (Klab.INSTANCE.getStatisticsLocalHandler() != null) {
			Component stc = Extensions.INSTANCE.getComponent(StatsComponent.ID);
			if (stc != null) {
				StatsComponent stats = stc.getImplementation(StatsComponent.class);
				if (stats != null) {

					StatsReport report = stats.createReport(targets.toArray());

					if (errors) {
						report.reportErrors(true);
					}
					if (cost) {
						report.reportCosts(true);
					}

					if (html) {
						report.setFormat(Format.Html);
					} else if (markdown) {
						report.setFormat(Format.Markdown);
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
							// TODO launch in browser
							return "Browser launched";
						}
						return ret;
					}

				}
			}
		} else if (snode != null) {

			String targs = "";
			for (Object option : (List<?>) call.getParameters().get("arguments")) {
				targs += (targs.isEmpty() ? "" : ",") + option.toString().toLowerCase();
			}
			
			return snode.getClient().get(API.STATS.STATS_REPORT, String.class, "targets", targs, "span", span, "errors",
					(errors ? "true" : false), "cost", (cost ? "true" : false), "groups", groups, "users", users,
					"engines", engines, "apps", apps, "resources", resources, "models", models, "observables", observables, 
					"operations", operations);
		}

		return "No reporting service available";
	}

}
