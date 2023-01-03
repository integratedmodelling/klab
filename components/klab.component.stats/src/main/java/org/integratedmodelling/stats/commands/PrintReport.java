package org.integratedmodelling.stats.commands;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.integratedmodelling.stats.reporting.StatsReport.Frequency;
import org.integratedmodelling.stats.reporting.StatsReport.Target;

public class PrintReport implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {

		Target target = Target.Users;
		Frequency frequency = null;
		Resolution lag = null;
		String whitelist = "";
		String blacklist = "";
		Set<Target> aggregation = new HashSet<>();
		boolean html = call.getParameters().get("html", false);
		
		Component stc = Extensions.INSTANCE.getComponent(StatsComponent.ID);
		if (stc != null) {
			StatsComponent stats = stc.getImplementation(StatsComponent.class);
			if (stats != null) {
				
				for (Object option : (List<?>) call.getParameters().get("arguments")) {
					
					String o = option.toString().toLowerCase();
					
					switch (o) {
					case "observations":
						target = Target.Observations;
						break;
					case "models":
						target = Target.Models;
						break;
					case "observables":
						target = Target.Observables;
						break;
					case "resources":
						target = Target.Resources;
						break;
					case "downloads":
						target = Target.Downloads;
						break;
					case "users":
						target = Target.Users;
						break;
					case "groups":
						target = Target.Groups;
						break;
					case "yearly":
						frequency = Frequency.Yearly;
						break;
					case "monthly":
						frequency = Frequency.Monthly;
						break;
					case "weekly":
						frequency = Frequency.Weekly;
						break;
					case "daily":
						frequency = Frequency.Daily;
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
					case "user":
						aggregation.add(Target.Users);
						break;
					case "resource":
						aggregation.add(Target.Resources);
						break;
					case "model":
						aggregation.add(Target.Models);
						break;
					case "observable":
						aggregation.add(Target.Observables);
						break;
					case "observation":
						aggregation.add(Target.Observations);
						break;
					default:
						
						// include/exclude user, URN, observable or group - all should be recognizable based on target
						if (o.startsWith("+") /* or not - just mention something that's not the above*/) {
							whitelist += (whitelist.isEmpty() ? "" : ",") + o.substring(1);
						} else if (o.startsWith("!")) {
							blacklist += (blacklist.isEmpty() ? "" : ",") +o.substring(1);
						} else {
							whitelist += (whitelist.isEmpty() ? "" : ",") +o;
						}
						break;
					}
				}
				
				List<Object> options = new ArrayList<>(); 
				
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
				
				if (frequency != null) {
					options.add("frequency");
					options.add(frequency.name());
				}
				
				StatsReport report = stats.createReport(target, options.toArray());
				
				if (report != null) {
					String ret = report.extract();
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
