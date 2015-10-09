package pl.konczak.mystartupapp.web.converter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 *
 * @author Sebastian.Lisiecki
 */
public class LocalDateTimeDeserializer
   extends JsonDeserializer<LocalDateTime> {

   @Override
   public LocalDateTime deserialize(JsonParser jp,
      DeserializationContext context)
      throws IOException {
      if (jp != null) {
         return LocalDateTime.parse(jp.getText(), DateTimeFormatter.ISO_DATE_TIME);
      } else {
         return null;
      }
   }

}
