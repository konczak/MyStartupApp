package pl.konczak.mystartupapp.util;

import java.util.UUID;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsUUID<T>
   extends BaseMatcher<T> {

   private IsUUID() {
   }

   @Override
   public boolean matches(Object item) {
      boolean isUUID = true;
      if (item == null
         || !(item instanceof String)
         || ((String) item).isEmpty()) {
         isUUID = false;
      } else {
         try {
            UUID.fromString((String) item);
         } catch (IllegalArgumentException e) {
            isUUID = false;
         }
      }
      return isUUID;
   }

   @Override
   public void describeTo(Description description) {
      description.appendText("UUID");
   }

   @Factory
   public static <T> Matcher<T> isUUID() {
      return new IsUUID<>();
   }
}
