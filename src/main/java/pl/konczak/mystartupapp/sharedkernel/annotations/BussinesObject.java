package pl.konczak.mystartupapp.sharedkernel.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Useful shortcut annotation to mark domain business domain classes as Spring service with all public methods
 * transactional.
 *
 * @author piotr.konczak
 */
@Service
@Retention(RetentionPolicy.RUNTIME)
@Transactional
@Target(ElementType.TYPE)
public @interface BussinesObject {

   String value() default "";
   
}
