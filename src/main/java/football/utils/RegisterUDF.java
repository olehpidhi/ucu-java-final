package football.utils;

import org.springframework.stereotype.Component;
import football.primitives.Columns;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by king on 25.12.17.
 */
@Retention(RUNTIME)
@Component
public @interface RegisterUDF {
    int arguments() default 1;
    Columns returnType();
}