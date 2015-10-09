package pl.konczak.mystartupapp.config.persistance.converter;

import java.util.UUID;

import javax.persistence.AttributeConverter;

/**
 * This class is used by JPA and Hibernate to convert java.util.UUID to value which is understood by database engine.
 *
 * @author piotr.konczak
 */
public class UUIDPersistenceConverter
   implements AttributeConverter<UUID, String> {

   @Override
   public String convertToDatabaseColumn(UUID attribute) {
      return attribute == null ? null : attribute.toString();
   }

   @Override
   public UUID convertToEntityAttribute(String dbData) {
      return dbData == null ? null : UUID.fromString(dbData);
   }
}
