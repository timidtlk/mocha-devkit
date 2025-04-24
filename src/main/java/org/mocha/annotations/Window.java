package org.mocha.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Window {
    int width() default 0;
    int height() default 0;
    boolean blackBars() default true;
    boolean resizable() default true;
    String title() default "Powered By MochaDevKit";
}
