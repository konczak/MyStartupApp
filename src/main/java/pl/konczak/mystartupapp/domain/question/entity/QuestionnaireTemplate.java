package pl.konczak.mystartupapp.domain.question.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import pl.konczak.mystartupapp.config.persistance.converter.LocalDateTimePersistenceConverter;
import pl.konczak.mystartupapp.config.persistance.converter.LocalePersistanceConventer;
import pl.konczak.mystartupapp.domain.question.dto.QuestionnaireTemplateSnapshot;
import pl.konczak.mystartupapp.sharedkernel.exception.EntityInStateNewException;

@Entity
public class QuestionnaireTemplate {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   @NotNull
   @Size(min = 3,
      max = 250)
   private String name;

   @NotNull
   private Long authorId;

   @NotNull
   private Long updatedBy;

   @NotNull
   private Timestamp createdAt;

   @NotNull
   private Timestamp updatedAt;

   @NotNull
   private String language;

   @Version
   private long version;

   protected QuestionnaireTemplate() {
   }

   public QuestionnaireTemplate(String name, Long authorId, Locale language) {
      this.name = name;
      this.authorId = authorId;
      this.updatedBy = authorId;
      this.createdAt = LocalDateTimePersistenceConverter.convertToDatabaseColumnValue(LocalDateTime.now());
      this.updatedAt = this.createdAt;
      this.language = LocalePersistanceConventer.convertToDatabaseColumnValue(language);
   }

   public QuestionnaireTemplateSnapshot toSnapshot() {
      if (this.id == null) {
         throw new EntityInStateNewException();
      }

      Locale languageExport = LocalePersistanceConventer.convertToEntityAttributeValue(language);
      LocalDateTime createdAtExport = LocalDateTimePersistenceConverter.convertToEntityAttributeValue(createdAt);
      LocalDateTime updatedAtExport = LocalDateTimePersistenceConverter.convertToEntityAttributeValue(updatedAt);

      return new QuestionnaireTemplateSnapshot(id, name, authorId, updatedBy, createdAtExport, updatedAtExport,
         languageExport);
   }

   public void edit(Long updatedBy, String name, Locale language) {
      this.name = name;
      this.language = LocalePersistanceConventer.convertToDatabaseColumnValue(language);
      this.updatedAt = LocalDateTimePersistenceConverter.convertToDatabaseColumnValue(LocalDateTime.now());
      this.updatedBy = updatedBy;
   }

   Long getId() {
      return id;
   }

}
