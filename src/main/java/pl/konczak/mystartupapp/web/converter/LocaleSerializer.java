package pl.konczak.mystartupapp.web.converter;

import java.io.IOException;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LocaleSerializer 
   extends JsonSerializer<Locale> {

   @Override
   public void serialize(Locale locale, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException,
         JsonProcessingException {
      if (jsonGenerator != null) {
         jsonGenerator.writeString(locale.toLanguageTag());
      }
   }

}
