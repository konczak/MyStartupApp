package pl.konczak.mystartupapp.web.converter;

import java.io.IOException;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


public class LocaleDeserializer
   extends JsonDeserializer<Locale>{

   @Override
   public Locale deserialize(JsonParser parser, DeserializationContext context) throws IOException,
         JsonProcessingException {
      if (parser != null) {
         return Locale.forLanguageTag(parser.getText());
      } else {
         return null;
      }
   }

}
