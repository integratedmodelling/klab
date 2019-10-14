package org.integratedmodelling.controlcenter.utils;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

public class TimerService extends ScheduledService<Integer> {

	private IntegerProperty count = new SimpleIntegerProperty();

	public final Integer getCount() {
		return count.get();
	}

	protected Task<Integer> createTask() {
		return new Task<Integer>() {
			protected Integer call() {
				// Adds 1 to the count
				count.set(getCount() + 1);
				return getCount();
			}
		};
	}

	public void setCount(int n) {
		count.set(n);
	}
}