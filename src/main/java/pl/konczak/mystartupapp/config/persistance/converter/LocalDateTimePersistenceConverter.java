package pl.konczak.mystartupapp.config.persistance.converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.konczak.mystartupapp.sharedkernel.exception.LocalDateTimeIsOutOfTimestampRangeException;

/**
 * This class is used by JPA and Hibernate to convert java.time.LocalDateTime to value which is understood by database
 * engine.
 *
 * @author piotr.konczak
 */
@Converter(autoApply = true)
public class LocalDateTimePersistenceConverter
   implements AttributeConverter<LocalDateTime, Timestamp> {

   private final static LocalDateTimePersistenceConverter localDateTimePersistenceConverter;
   static{
      localDateTimePersistenceConverter = new LocalDateTimePersistenceConverter();
   }
   private static final LocalDateTime MIN_SUPPORTED_VALUE = LocalDateTime.parse("1970-01-01T00:00:00");
   private static final LocalDateTime MAX_SUPPORTED_VALUE = LocalDateTime.parse("+292278994-08-17T07:12:55");
   private static final Logger LOGGER = LoggerFactory.getLogger(LocalDateTimePersistenceConverter.class);

   @Override
   public java.sql.Timestamp convertToDatabaseColumn(LocalDateTime entityValue) {

      if (entityValue == null) {
         return null;
      }

      if (entityValue.isBefore(MIN_SUPPORTED_VALUE)) {
         LOGGER.error("<{}> LocalDateTime is out of timestamp range ", entityValue.toString());
         throw new LocalDateTimeIsOutOfTimestampRangeException(entityValue.toString() + " is out of Timestamp range ");
      }
      if (entityValue.isAfter(MAX_SUPPORTED_VALUE)) {
         LOGGER.error("<{}> LocalDateTime is out of timestamp range ", entityValue.toString());
         throw new LocalDateTimeIsOutOfTimestampRangeException(entityValue.toString() + " is out of Timestamp range ");
      }

      return Timestamp.valueOf(entityValue);
   }

   @Override
   public LocalDateTime convertToEntityAttribute(java.sql.Timestamp databaseValue) {
      return databaseValue == null ? null : databaseValue.toLocalDateTime();
   }

   public static java.sql.Timestamp convertToDatabaseColumnValue(LocalDateTime entityValue){    
      return localDateTimePersistenceConverter.convertToDatabaseColumn(entityValue);
   }

   public static LocalDateTime convertToEntityAttributeValue(java.sql.Timestamp databaseValue){
      return localDateTimePersistenceConverter.convertToEntityAttribute(databaseValue);
   }
}
