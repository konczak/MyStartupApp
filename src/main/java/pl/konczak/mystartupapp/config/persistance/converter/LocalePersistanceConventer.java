package pl.konczak.mystartupapp.config.persistance.converter;

import java.util.Locale;

import javax.persistence.AttributeConverter;

/**
 * This class is used by JPA and Hibernate to convert java.util.Locale to value which is understood by database engine.
 *
 * @author piotr.konczak
 */
public class LocalePersistanceConventer
   implements AttributeConverter<Locale, String> {

   private final static LocalePersistanceConventer localePersistanceConventer;
   static{
      localePersistanceConventer = new LocalePersistanceConventer();
   }
   
   @Override
   public String convertToDatabaseColumn(Locale attribute) {
      return attribute == null ? null : attribute.toLanguageTag();
   }

   @Override
   public Locale convertToEntityAttribute(String dbData) {
      return dbData == null ? null : Locale.forLanguageTag(dbData);
   }
   
   public static String convertToDatabaseColumnValue(Locale attribute){     
      return localePersistanceConventer.convertToDatabaseColumn(attribute);
   }
   
   public static Locale convertToEntityAttributeValue(String dbData) {     
      return localePersistanceConventer.convertToEntityAttribute(dbData);
   }

}
