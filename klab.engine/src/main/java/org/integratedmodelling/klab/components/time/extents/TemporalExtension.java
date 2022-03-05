package org.integratedmodelling.klab.components.time.extents;

import java.util.Iterator;
import java.util.NavigableSet;
import java.util.TreeSet;

import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.utils.Pair;

/**
 * A time line that can be broken in as many segments as needed based on
 * incoming transitions, without ever breaking the continuity of the overall
 * interval.
 * 
 * TODO this can be useful in different context and its generalized form belongs
 * in the API package.
 * 
 * @author Ferd
 *
 */
public class TemporalExtension {

	private final long start;
	private final long end;
	private NavigableSet<Long> extension = new TreeSet<>();

	public TemporalExtension(ITime overallTime) {
		this.start = overallTime.getStart().getMilliseconds();
		this.end = overallTime.getEnd().getMilliseconds();
		extension.add(this.start);
		extension.add(this.end);
	}

	/**
	 * Add a transition and redefine the size. Return true if the transition has
	 * made any difference.
	 * 
	 * @param transition
	 * @return
	 */
	public boolean add(ITime transition) {

		if (transition.getStart().getMilliseconds() < this.start) {
			throw new KlabIllegalArgumentException("cannot add time extension before overall start time");
		}
		if (transition.getEnd().getMilliseconds() > this.end) {
			throw new KlabIllegalArgumentException("cannot add time extension after overall end time");
		}

		boolean as = extension.add(transition.getStart().getMilliseconds());
		boolean ae = extension.add(transition.getEnd().getMilliseconds());
		
		return as || ae;
	}

	public int size() {
		return extension.size() - 1;
	}

	public Pair<Long, Long> getExtension(int n) {
		Iterator<Long> it = extension.iterator();
		long start = it.next();
		long end = it.next();
		for (int i = 0; i < n; i++) {
			start = end;
			end = it.next();
		}
		return new Pair<>(start, end);
	}

	public static void main(String[] args) {

		TemporalExtension te = new TemporalExtension(Time.create(0l, 1000l));

		boolean shouldBeTrue = te.add(Time.create(100l, 150l));
		boolean shouldBeFalse = te.add(Time.create(100l, 150l));
		shouldBeTrue = te.add(Time.create(150l, 200l));

		for (int i = 0; i < te.size(); i++) {
			System.out.println(i + ": " + te.getExtension(i));
		}

		System.out.println(te.at(175));

	}

	public Time at(long milliseconds) {
//        if (milliseconds == this.end) {
//            milliseconds --;
//        }
		long start = extension.floor(milliseconds);
		long end = extension.ceiling(start + 1);
		int i = 0;
		Iterator<Long> it = extension.iterator();
		while (it.hasNext()) {
			if (it.next() == start) {
				break;
			}
			i++;
		}
		return Time.create(start, end).withLocatedTimeslice(i);
	}

	public long[] getTimestamps() {
		long[] ret = new long[extension.size()];
		int i = 0;
		for (Long l : extension) {
			ret[i++] = l;
		}
		return ret;
	}

}
