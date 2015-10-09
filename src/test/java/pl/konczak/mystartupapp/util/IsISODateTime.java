package pl.konczak.mystartupapp.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsISODateTime<T>
   extends BaseMatcher<T> {

   private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

   private IsISODateTime() {
   }

   @Override
   public boolean matches(Object item) {
      boolean isISODateTime = true;
      if (item == null
         || !(item instanceof String)
         || ((String) item).isEmpty()) {
         isISODateTime = false;
      } else {
         try {
            LocalDateTime.parse((String) item, FORMATTER);
         } catch (DateTimeParseException e) {
            isISODateTime = false;
         }
      }
      return isISODateTime;
   }

   @Override
   public void describeTo(Description description) {
      description.appendText("ISODateTime");
   }

   @Factory
   public static <T> Matcher<T> isISODateTime() {
      return new IsISODateTime<>();
   }
}
