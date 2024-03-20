package org.integratedmodelling.klab.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.LOCAL_VARIABLE, ElementType.PARAMETER})
public @interface WorkInProgress {
    String reason() default "Work in progress";
    Status status() default Status.IN_PROGRESS;

    public enum Status {
        IN_PROGRESS,
        ON_HOLD,
        BLOCKED,
    }
}

