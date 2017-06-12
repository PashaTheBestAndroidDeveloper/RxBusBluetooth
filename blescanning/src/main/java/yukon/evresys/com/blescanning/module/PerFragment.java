package yukon.evresys.com.blescanning.module;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * Created by Paul on 05.05.17. For evresys-androidapp
 */


@Scope
@Retention(RUNTIME)
public @interface PerFragment {
}

