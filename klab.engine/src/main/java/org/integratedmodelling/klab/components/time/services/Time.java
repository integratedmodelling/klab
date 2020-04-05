package org.integratedmodelling.klab.components.time.services;

import org.integratedmodelling.kim.api.IKimQuantity;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.model.KimDate;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeDuration;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.common.mediation.Quantity;
import org.integratedmodelling.klab.components.time.extents.TimeInstant;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.joda.time.DateTime;
import org.joda.time.Years;

public class Time implements IExpression {

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {

		ITimeInstant start = null;
		ITimeInstant end = null;
		ITimeDuration step = null;
		Resolution resolution = null;
		ITime.Type type = ITime.Type.PHYSICAL;

		if (parameters.contains("focus")) {

			if (parameters.get("focus") instanceof IKimQuantity) {
				resolution = org.integratedmodelling.klab.components.time.extents.Time
						.resolution(parameters.get("focus", IKimQuantity.class));
			} else {
				try {
					Resolution.Type focus = Resolution.Type.parse(parameters.get("focus").toString());
					resolution = org.integratedmodelling.klab.components.time.extents.Time.resolution(1.0, focus);
				} catch (Throwable t) {
					// later
				}
			}

			if (resolution == null) {
				throw new KlabValidationException("wrong specification of temporal focus in time function: expecting "
						+ "a quantity with temporal unit (e.g. 1.year) or a"
						+ " span description (e.g. 'year', 'month', 'century'...)");
			}
		}

		if (parameters.contains("year")) {
			DateTime begin = new DateTime(parameters.get("year", Number.class).intValue(), 1, 1, 0, 0, 0, 0);
			start = new TimeInstant(begin);
			end = new TimeInstant(begin.plus(Years.ONE));
		} else {
			if (parameters.contains("start")) {
				if (parameters.get("start") instanceof KimDate) {
					start = org.integratedmodelling.klab.components.time.extents.Time
							.instant(parameters.get("start", KimDate.class));
				} else if (parameters.get("start") instanceof Number) {
					start = org.integratedmodelling.klab.components.time.extents.Time
							.instant(parameters.get("start", Number.class).intValue());
				} else {
					throw new KlabValidationException(
							"wrong specification of start time in time function: expecting a date literal or an integer year.");
				}
			}
			if (parameters.contains("end")) {
				if (parameters.get("end") instanceof KimDate) {
					end = org.integratedmodelling.klab.components.time.extents.Time
							.instant(parameters.get("end", KimDate.class));
				} else if (parameters.get("end") instanceof Number) {
					end = org.integratedmodelling.klab.components.time.extents.Time
							.instant(parameters.get("end", Number.class).intValue());
				} else {
					throw new KlabValidationException(
							"wrong specification of end time in time function: expecting a date literal or an integer year.");
				}
			}
		}
		if (parameters.contains("step")) {

			if (start == null || end == null) {
//				throw new KlabValidationException("a step can only be specified along with both start and end");
			}

			if (parameters.get("step") instanceof IKimQuantity) {
				IKimQuantity sq = parameters.get("step", IKimQuantity.class);
				step = org.integratedmodelling.klab.components.time.extents.Time.duration(sq);
				if (resolution == null) {
					resolution = org.integratedmodelling.klab.components.time.extents.Time.resolution(sq);
				}
			} else if (parameters.get("step") instanceof Quantity) {
				Quantity sq = parameters.get("step", Quantity.class);
				step = org.integratedmodelling.klab.components.time.extents.Time.duration(sq);
				if (resolution == null) {
					resolution = org.integratedmodelling.klab.components.time.extents.Time.resolution(sq);
				}
			} else if (parameters.get("step") instanceof String) {
				step = org.integratedmodelling.klab.components.time.extents.Time
						.duration(parameters.get("step", String.class));
				if (resolution == null) {
					resolution = org.integratedmodelling.klab.components.time.extents.Time
							.resolution(parameters.get("step", String.class));
				}
			} else if (parameters.get("step") instanceof Number && resolution != null) {
				step = org.integratedmodelling.klab.components.time.extents.Time
						.duration(parameters.get("step", Number.class), resolution.getType());
			} else {
				throw new KlabValidationException(
						"wrong specification of step in time function: expecting number with units. "
								+ "A number is only allowed if focus is specified");
			}

			type = ITime.Type.GRID;
		}

		if (resolution == null) {
			if (parameters.contains("year")) {
				resolution = org.integratedmodelling.klab.components.time.extents.Time.resolution(1,
						Resolution.Type.YEAR);
			} else if (start != null && end != null) {
				// should attribute based on start and end
				resolution = org.integratedmodelling.klab.components.time.extents.Time.resolution(start, end);
			}
		}

		if (parameters.contains("realtime") && parameters.get("realtime", Boolean.FALSE)) {
			type = ITime.Type.REAL;
			if (step == null) {
				throw new KlabValidationException("real time requires specification of start, end and step");
			}
		} else if (parameters.contains("generic") && parameters.get("generic", Boolean.FALSE)) {
			type = ITime.Type.LOGICAL;
			if (step != null) {
				throw new KlabValidationException("generic time must have a focus and cannot have a step");
			}
		}

		if (resolution == null) {
			throw new KlabValidationException("ambiguous time specification: cannot establish resolution");
		}

		return org.integratedmodelling.klab.components.time.extents.Time.create(type, resolution.getType(),
				resolution.getMultiplier(), start, end, step);
	}
}
