package org.integratedmodelling.stats.commands;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution.Type;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.components.time.extents.TimeInstant;
import org.integratedmodelling.klab.engine.extensions.Component;
import org.integratedmodelling.stats.StatsComponent;
import org.integratedmodelling.stats.reporting.StatsReport;
import org.integratedmodelling.stats.reporting.StatsReport.Format;
import org.integratedmodelling.stats.reporting.StatsReport.Frequency;
import org.integratedmodelling.stats.reporting.StatsReport.Target;

public class PrintReport implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {

		Resolution lag = null;
		String whitelist = "";
		String blacklist = "";
		boolean html = call.getParameters().get("html", false);
		boolean errors = call.getParameters().get("errors", false);

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
					case "year":
						lag = Time.resolution(1, Type.YEAR);
						break;
					case "day":
						lag = Time.resolution(1, Type.DAY);
						break;
					case "month":
						lag = Time.resolution(1, Type.MONTH);
						break;
					case "week":
						lag = Time.resolution(1, Type.WEEK);
						break;
					case "hour":
						lag = Time.resolution(1, Type.HOUR);
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

				if (lag != null) {
					ITimeInstant end = TimeInstant.create();
					ITimeInstant start = end.minus(1, lag);
					options.add("start");
					options.add(start.getMilliseconds());
					options.add("end");
					options.add(end.getMilliseconds());
				}

				StatsReport report = stats.createReport(options.toArray());

				if (errors) {
					report.reportErrors(true);
				}
				if (html) {
					report.setFormat(Format.Html);
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
