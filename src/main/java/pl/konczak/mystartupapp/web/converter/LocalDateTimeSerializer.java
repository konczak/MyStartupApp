package pl.konczak.mystartupapp.web.converter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 *
 * @author Sebastian.Lisiecki
 */
public class LocalDateTimeSerializer
   extends JsonSerializer<LocalDateTime> {

   private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

   @Override
   public void serialize(LocalDateTime value, JsonGenerator jgen,
      SerializerProvider provider)
      throws IOException {
      if (jgen != null) {
         jgen.writeString(value.format(FORMATTER));
      }
   }

}
