package org.integratedmodelling.klab.node.controllers;

import java.security.Principal;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.engine.extensions.Component;
import org.integratedmodelling.klab.rest.ObservationResultStatistics;
import org.integratedmodelling.klab.utils.StringUtil;
import org.integratedmodelling.stats.StatsComponent;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Stats ingestion and reporting endpoints. Add security back when testing is
 * done.
 * 
 * @author Ferd
 *
 */
@RestController
@Secured(Role.ENGINE)
public class StatsController {

	/*
	 * Only needed endpoint is PUT to add activities. All the rest is handled
	 * through the adapter or the operations.
	 */
	@PutMapping(API.STATS.STATS_ADD)
	public void addActivity(@RequestBody ObservationResultStatistics[] activities, Principal principal) {
		IUserIdentity user = Authentication.INSTANCE.getUserIdentity(principal);
		Component stc = Extensions.INSTANCE.getComponent(StatsComponent.ID);
		if (stc != null && stc.isActive()) {
			for (ObservationResultStatistics activity : activities) {
				stc.getImplementation(StatsComponent.class).submit(activity, user.getUsername(),
						StringUtil.join(user.getGroups().stream().map((d) -> d.getId()).toList(), ","));
			}
		}
	}

	/**
	 * Report generation endpoint. All data are also available through k.LAB
	 * resources.
	 * <p>
	 * Query parameters:
	 * <p>
	 * <dl>
	 * <dt>targets</dt>
	 * <dd>Comma-separated list of targets in order of aggregation. Optional. Use
	 * one of
	 * {<b>hourly</b>|<b>daily</b>|<b>weekly</b>|<b>monthly</b>|<b>yearly</b>} for
	 * temporal aggregation. The <b>contexts</b>, <b>observations</b>, <b>apps</b>
	 * and <b>engines</b> tags refer to each context, top-level user query,
	 * application (including "k.Explorer", "k.Modeler" and "API") and engine name
	 * (using "local" for a local engine). The <b>users</b> tag can be used by
	 * administrators and auditors only. Use one of
	 * {<b>observables</b>|<b>models</b>|<b>resources</b>|<b>operations</b>|<b>downloads</b>}
	 * to report only a specific type of asset.</dd>
	 * <dt>span</dt>
	 * <dd>Two comma-separated long integers referring to the beginning and end of
	 * the reporting period, specified in milliseconds since epoch (UTC). Default is
	 * the entire span of the available statistics, only available to
	 * administrators. If only one number is given, it is interpreted as the
	 * starting time.</dd>
	 * <dt>errors</dt>
	 * <dd>Set to true to report on errors and to false (default) to report on
	 * successful observations.</dd>
	 * </dl>
	 * The following filters can be used. All will match names that may contain
	 * wildcards.
	 * <p>
	 * <dl>
	 * <dt>users</dt>
	 * <dd>Comma-separated list of users to include or exclude. Use !xxx to exclude
	 * xxx. For use by administrators and auditors only.</dd>
	 * <dt>groups</dt>
	 * <dd>Comma-separated list of groups to include or exclude. Use !xxx to exclude
	 * xxx. For use by administrators and auditors only.</dd>
	 * <dt>apps</dt>
	 * <dd>Comma-separated list of applications to include or exclude. Use !xxx to
	 * exclude xxx.</dd>
	 * <dt>engines</dt>
	 * <dd>Comma-separated list of engines to include or exclude. Use !xxx to
	 * exclude xxx.</dd>
	 * <dt>resources</dt>
	 * <dd>Comma-separated list of resources to include or exclude. Use !xxx to
	 * exclude xxx.</dd>
	 * <dt>models</dt>
	 * <dd>Comma-separated list of models to include or exclude. Use !xxx to
	 * <dt>observables</dt>
	 * <dd>Comma-separated list of observables to include or exclude. Use !xxx to
	 * exclude xxx.</dd>
	 * </dl>
	 * 
	 * @param principal
	 * @return
	 */
	@GetMapping(value = API.STATS.STATS_REPORT, produces = { "text/html", "text/plain" })
	public String createReport(Principal principal, @PathVariable String targets,
			@PathVariable(required = false) String span, @PathVariable(required = false) boolean errors,
			@PathVariable(required = false) String users, @PathVariable(required = false) String groups,
			@PathVariable(required = false) String apps, @PathVariable(required = false) String engines,
			@PathVariable(required = false) String resources, @PathVariable(required = false) String models,
			@PathVariable(required = false) String observables) {

		IUserIdentity user = Authentication.INSTANCE.getUserIdentity(principal);
		
		
//		for (Object option : (List<?>) call.getParameters().get("arguments")) {
//
//			String o = option.toString().toLowerCase();
//
//			switch (o) {
//			case "observations":
//				options.add(Target.Observations);
//				break;
//			case "models":
//				options.add(Target.Models);
//				break;
//			case "contexts":
//				options.add(Target.Contexts);
//				break;
//			case "engines":
//				options.add(Target.Engines);
//				break;
//			case "observables":
//				options.add(Target.Observables);
//				break;
//			case "operations":
//				options.add(Target.Operations);
//				break;
//			case "resources":
//				options.add(Target.Resources);
//				break;
//			case "downloads":
//				options.add(Target.Downloads);
//				break;
//			case "users":
//				options.add(Target.Users);
//				break;
//			case "apps":
//				options.add(Target.Applications);
//				break;
//			case "yearly":
//				options.add(Frequency.Yearly);
//				break;
//			case "monthly":
//				options.add(Frequency.Monthly);
//				break;
//			case "weekly":
//				options.add(Frequency.Weekly);
//				break;
//			case "daily":
//				options.add(Frequency.Daily);
//				break;
//			case "hourly":
//				options.add(Frequency.Hourly);
//				break;
//			case "year":
//				lag = Time.resolution(1, Type.YEAR);
//				break;
//			case "day":
//				lag = Time.resolution(1, Type.DAY);
//				break;
//			case "month":
//				lag = Time.resolution(1, Type.MONTH);
//				break;
//			case "week":
//				lag = Time.resolution(1, Type.WEEK);
//				break;
//			case "hour":
//				lag = Time.resolution(1, Type.HOUR);
//				break;
//
//			default:
//
//				// include/exclude user, URN, observable or group - all should be recognizable
//				// based on target
//				if (o.startsWith("+") /* or not - just mention something that's not the above */) {
//					whitelist += (whitelist.isEmpty() ? "" : ",") + o.substring(1);
//				} else if (o.startsWith("!")) {
//					blacklist += (blacklist.isEmpty() ? "" : ",") + o.substring(1);
//				} else {
//					whitelist += (whitelist.isEmpty() ? "" : ",") + o;
//				}
//				break;
//			}
//		}
//
//		if (!whitelist.isEmpty()) {
//			options.add("whitelist");
//			options.add(whitelist);
//		}
//		if (!blacklist.isEmpty()) {
//			options.add("blacklist");
//			options.add(blacklist);
//		}
//
//		if (lag != null) {
//			ITimeInstant end = TimeInstant.create();
//			ITimeInstant start = end.minus(1, lag);
//			options.add("start");
//			options.add(start.getMilliseconds());
//			options.add("end");
//			options.add(end.getMilliseconds());
//		}
//
//		StatsReport report = stats.createReport(options.toArray());
//
//		if (errors) {
//			report.reportErrors(true);
//		}
//		if (html) {
//			report.setFormat(Format.Html);
//		}

		return "<p>Unimplemented</p>";
	}

}
