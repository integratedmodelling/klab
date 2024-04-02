package org.integratedmodelling.klab.node.controllers;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.engine.extensions.Component;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.rest.ObservationResultStatistics;
import org.integratedmodelling.klab.utils.StringUtil;
import org.integratedmodelling.stats.StatsComponent;
import org.integratedmodelling.stats.reporting.StatsReport;
import org.integratedmodelling.stats.reporting.StatsReport.Frequency;
import org.integratedmodelling.stats.reporting.StatsReport.Target;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Stats ingestion and reporting endpoints. Add security back when testing is done.
 * 
 * @author Ferd
 *
 */
@RestController
@Secured(Role.ENGINE)
public class StatsController {

    /*
     * Only needed endpoint is PUT to add activities. All the rest is handled through the adapter or
     * the operations.
     */
    @PutMapping(API.STATS.STATS_ADD)
    public void addActivity(@RequestBody ObservationResultStatistics[] activities, Principal principal) {
        IUserIdentity user = Authentication.INSTANCE.getUserIdentity(principal);
        Component stc = Extensions.INSTANCE.getComponent(StatsComponent.ID);
        if (stc != null && stc.isActive() && activities.length > 0) {
            Logging.INSTANCE.info(
                    "received " + activities.length + " new activities from " + user.getUsername() + " at "
                            + activities[0].getEngineName());
            for (ObservationResultStatistics activity : activities) {
                stc.getImplementation(StatsComponent.class).submit(activity, user.getUsername(),
                        StringUtil.join(user.getGroups().stream().map((d) -> d.getName()).toList(), ","));
            }
        }
    }

    /*
     * TODO provide an endpoint to obtain GeoJSON data from the database, for splash pages and the
     * like.
     */

    /**
     * Report generation endpoint. All data are also available through k.LAB resources. Non-admins
     * can only report on their own usage.
     * <p>
     * Query parameters:
     * <p>
     * <dl>
     * <dt>targets</dt>
     * <dd>Comma-separated list of targets in order of aggregation. Optional. Use one of
     * {<b>hourly</b>|<b>daily</b>|<b>weekly</b>|<b>monthly</b>|<b>yearly</b>} for temporal
     * aggregation. The <b>contexts</b>, <b>observations</b>, <b>apps</b> and <b>engines</b> tags
     * refer to each context, top-level user query, application (including "k.Explorer", "k.Modeler"
     * and "API") and engine name (using "local" for a local engine). The <b>users</b> tag can be
     * used by administrators and auditors only. Use one of
     * {<b>observables</b>|<b>models</b>|<b>resources</b>|<b>operations</b>|<b>downloads</b>} to
     * report only a specific type of asset.</dd>
     * <dt>span</dt>
     * <dd>Two comma-separated long integers referring to the beginning and end of the reporting
     * period, specified in milliseconds since epoch (UTC). Default is the entire span of the
     * available statistics, only available to administrators. If only one number is given, it is
     * interpreted as the starting time. Alternatively, the span can be given as one of
     * {<b>hour(s)</b>|<b>day(s)</b>|<b>week(s)</b>|<b>month(s)</b>|<b>year(s)</b>}, optionally
     * preceded by an integer multiplier such as in "2,week" to select events in the given current
     * span (multiplier = 1 or default) or more spans before the one including the current
     * date.</dd>
     * <dt>errors</dt>
     * <dd>Set to true to report on errors and to false (default) to report on successful
     * observations.</dd>
     * <dt>cost</dt>
     * <dd>Set to true to compute costs for contexts. Must be administrator or auditor, and the
     * targets must include "contexts".</dd>
     * </dl>
     * The following filters can be used. All will match names that may contain wildcards.
     * <p>
     * <dl>
     * <dt>users</dt>
     * <dd>Comma-separated list of users to include or exclude. Use !xxx to exclude xxx. For use by
     * administrators and auditors only.</dd>
     * <dt>groups</dt>
     * <dd>Comma-separated list of groups to include or exclude. Use !xxx to exclude xxx. For use by
     * administrators and auditors only.</dd>
     * <dt>apps</dt>
     * <dd>Comma-separated list of applications to include or exclude. Use !xxx to exclude xxx.</dd>
     * <dt>engines</dt>
     * <dd>Comma-separated list of engines to include or exclude. Use !xxx to exclude xxx.</dd>
     * <dt>resources</dt>
     * <dd>Comma-separated list of resources to include or exclude. Use !xxx to exclude xxx.</dd>
     * <dt>models</dt>
     * <dd>Comma-separated list of models to include or exclude. Use !xxx to
     * <dt>observables</dt>
     * <dd>Comma-separated list of observables to include or exclude. Use !xxx to exclude xxx.</dd>
     * </dl>
     * 
     * @param principal
     * @return the report
     * @throws UnsupportedEncodingException
     */
    @GetMapping(value = API.STATS.STATS_REPORT, produces = {"text/html", "text/plain", "text/markdown"})
    public String createReport(Principal principal, @RequestParam String targets,
            @RequestParam(required = false) String span,
            @RequestParam(required = false) boolean errors, @RequestParam(required = false) String users,
            @RequestParam(required = false) String groups, @RequestParam(required = false) String apps,
            @RequestParam(required = false) String engines, @RequestParam(required = false) String resources,
            @RequestParam(required = false) String models, @RequestParam(required = false) String observables,
            @RequestParam(required = false) String operations, @RequestParam(required = false) boolean cost)
            throws UnsupportedEncodingException {

        IUserIdentity user = Authentication.INSTANCE.getUserIdentity(principal);
        boolean adminOrAuditor = Authentication.INSTANCE.hasRole(principal, Role.ROLE_ADMINISTRATOR);
        Component stc = Extensions.INSTANCE.getComponent(StatsComponent.ID);
        if (stc == null || !stc.isActive()) {
            throw new KlabIllegalStateException("statistics component is not configured or not installed");
        }
        StatsComponent stats = stc.getImplementation(StatsComponent.class);
        List<Object> options = new ArrayList<>();
        for (String target : targets.split(",")) {
            switch(target.toLowerCase()) {
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
                throw new KlabIllegalArgumentException("unrecognized target " + target);
            }
        }

        StatsReport report = stats.createReport(options.toArray());

        if (report == null) {
            throw new KlabInternalErrorException("report generation error");
        }

        report.reportErrors(errors);

        if (cost && !adminOrAuditor) {
            // FIXME use proper auth response and exception handling
            throw new KlabAuthorizationException(
                    "cost computation is only accessible to administrators and auditors");
        }

        report.reportCosts(cost);

        if (span != null) {
            report.setSpan(span.split(","));
        }

        if (operations != null) {
            report.filterFor(Target.Operations, operations.split(","));
        }
        if (!adminOrAuditor) {
            report.filterFor(Target.Users, new String[]{user.getUsername()});
        } else if (users != null) {
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

        return report.compile();
    }

}
