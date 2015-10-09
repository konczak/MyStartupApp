package pl.konczak.mystartupapp.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LocalDateTimeTestSerializer extends JsonSerializer<LocalDateTime> {

   @Override
   public void serialize(LocalDateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
         JsonProcessingException {
      if (jgen != null) {
         jgen.writeString(value.format(DateTimeFormatter.ISO_DATE_TIME));
      }
   }

}