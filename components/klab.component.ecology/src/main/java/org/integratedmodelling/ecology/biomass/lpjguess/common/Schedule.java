//package org.integratedmodelling.ecology.biomass.lpjguess.common;
//
//import org.integratedmodelling.engine.time.literals.TimeValue;
//import org.integratedmodelling.procsim.api.ISchedule;
//
//public class Schedule implements ISchedule {
//    
//    private TimeValue timestamp;
//    private TimeValue initialTimestamp;
//
//    @Override
//    public int julianDay() {
//        // Note: This is the day of the YEAR (ie. Julian day)
//        // This is zero based, so that it fits in an array of length 366
//        return getTimestamp().getJulianDay() - 1;
//    }
//
//    @Override
//    public boolean isLastDayOfMonth() {
//        // Note: This is the last day of the MONTH
//        return (getTimestamp().getDayOfMonth() == getTimestamp().getNDaysInMonth());
//    }
//
//    @Override
//    public boolean isLastMonthOfYear() {
//        return (this.month() == 11);
//    }
//
//    @Override
//    public boolean isFirstYearOfSimulation() {
//        return (this.timestamp.getYear() == this.initialTimestamp.getYear());
//    }
//
//    @Override
//    public boolean isFirstMonthOfSimulation() {
//        return (this.timestamp.getMonth() == this.initialTimestamp.getMonth());
//    }
//
//    @Override
//    public boolean isFirstDayOfSimulation() {
//        return (this.timestamp.getDayOfMonth() == this.initialTimestamp.getDayOfMonth() &&
//                this.timestamp.getMonth() == this.initialTimestamp.getMonth() &&
//                this.timestamp.getYear() == this.initialTimestamp.getYear());
//    }
//
//    @Override
//    public boolean isFirstDayOfYear() {
//        return (this.julianDay() == 0);
//    }
//
//    @Override
//    public boolean isLastDayOfYear() {
//        return (getTimestamp().getJulianDay() == getTimestamp().getNDaysInYear());
//    }
//
//    @Override
//    public int dayofmonth() {
//        // Note: This is the day of the MONTH
//        // This is zero based, so that it fits in an array of length 31
//        return getTimestamp().getDayOfMonth() - 1;
//    }
//
//    @Override
//    public int year() {
//        return getTimestamp().getYear();
//    }
//
//    @Override
//    public int month() {
//        // This is zero based, so that it fits in an array of length 12
//        return getTimestamp().getMonth() - 1;
//    }
//
//    @Override
//    public int ndaymonth() {
//        return getTimestamp().getNDaysInMonth() - 1;
//    }
//
//    public TimeValue getTimestamp() {
//        return timestamp;
//    }
//
//    @Override
//    public void setTimestamp(TimeValue timestamp) {
//        this.timestamp = timestamp;
//    }
//
//    @Override
//    public void setInitialTimestamp(TimeValue tv) {
//        initialTimestamp = tv;
//        setTimestamp(tv);
//
//    }
//
//}
