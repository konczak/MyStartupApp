package pl.konczak.mystartupapp.email.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

/**
 * This class converts Date classes to their formatted String equivalents.
 *
 * @author Piotr.Konczak
 */
@Component
public class LocalDateTimeFormattingUtil {

   private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
   private static final DateTimeFormatter FORMATTER_SHORT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

   /**
    * Formats LocalDateTime to String
    *
    * @param date value
    * @return when date is null returns "-" in other case creates formatted String "yyyy-MM-dd HH:mm"
    */
   public String formatTimestamp(LocalDateTime date) {
      if (date == null) {
         return "-";
      }

      return date.format(FORMATTER);
   }

   /**
    * Formats LocalDateTime to String
    *
    * @param date value
    * @return when date is null returns "-" in other case creates formatted String "yyyy-MM-dd"
    */
   public String formatDate(LocalDateTime date) {
      if (date == null) {
         return "-";
      }

      return date.format(FORMATTER_SHORT);
   }

}
