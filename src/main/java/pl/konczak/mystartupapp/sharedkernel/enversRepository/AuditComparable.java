package pl.konczak.mystartupapp.sharedkernel.enversRepository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to mark audited field to be compared with previous revisions. 
 * If field value hasn't changed since last revision, then it's value 
 * is set to null.
 * 
 * @author Krzysztof Pszczółkowski
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AuditComparable {

}