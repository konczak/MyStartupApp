package pl.konczak.mystartupapp.sharedkernel.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Useful shortcut annotation to mark domain classes searching for database entity snapshots as Spring component with
 * all public methods transactional in read only mode if it database support it.
 *
 * @author piotr.konczak
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Transactional(readOnly = true,
   propagation = Propagation.SUPPORTS)
@Target(ElementType.TYPE)
public @interface Finder {

   String value() default "";
   
}
