package pl.konczak.mystartupapp.domain.question.dto;

import java.time.LocalDateTime;
import java.util.Locale;

public class QuestionnaireTemplateSnapshot {

   private final Long id;
   private final String name;
   private final Long authorId;
   private final LocalDateTime createdAt;
   private final Locale language;
   private Long updatedBy;
   private LocalDateTime updatedAt;

   public QuestionnaireTemplateSnapshot(Long id, String name, Long authorId, Long updatedBy, LocalDateTime createdAt,
      LocalDateTime updatedAt, Locale language) {
      this.id = id;
      this.name = name;
      this.authorId = authorId;
      this.updatedBy = updatedBy;
      this.createdAt = createdAt;
      this.updatedAt = updatedAt;
      this.language = language;
   }

   public Long getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public Long getAuthorId() {
      return authorId;
   }

   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   public Locale getLanguage() {
      return language;
   }

   public Long getUpdatedBy() {
      return updatedBy;
   }

   public LocalDateTime getUpdatedAt() {
      return updatedAt;
   }

}
