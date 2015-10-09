package pl.konczak.mystartupapp.domain.employee.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author Mateusz.Glabicki
 */
public class EmployeeSnapshot {

   private final Long id;

   private final UUID guid;

   private final String title;

   private final String firstname;

   private final String lastname;

   private final String username;

   private final LocalDateTime createdAt;

   private final String mail;

   private final boolean deleted;

   private final LocalDateTime deletedAt;

   private final LocalDateTime updatedAt;

   private final long version;

   private final Long avatarId;

   private final String photo;

   private final Long managerId;

   public EmployeeSnapshot(Long id, UUID guid, String title, String name, String lastname, String username,
      LocalDateTime createdAt,
      String mail,
      boolean deleted, LocalDateTime deletedAt,
      LocalDateTime updatedAt,
      long version,
      Long avatarId, String photo, Long managerId) {
      this.id = id;
      this.guid = guid;
      this.title = title;
      this.firstname = name;
      this.lastname = lastname;
      this.username = username;
      this.createdAt = createdAt;
      this.mail = mail;
      this.deleted = deleted;
      this.deletedAt = deletedAt;
      this.updatedAt = updatedAt;
      this.version = version;
      this.avatarId = avatarId;
      this.photo = photo;
      this.managerId = managerId;
   }

   public String getUsername() {
      return username;
   }

   public Long getId() {
      return id;
   }

   public UUID getGuid() {
      return guid;
   }

   public String getTitle() {
      return title;
   }

   public String getFirstname() {
      return firstname;
   }

   public String getLastname() {
      return lastname;
   }

   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   public String getMail() {
      return mail;
   }

   public String getFullname() {
      return firstname + " " + lastname;
   }

   public boolean isDeleted() {
      return deleted;
   }

   public LocalDateTime getDeletedAt() {
      return deletedAt;
   }

   public LocalDateTime getUpdatedAt() {
      return updatedAt;
   }

   public long getVersion() {
      return version;
   }

   public Long getAvatarId() {
      return avatarId;
   }

   public String getPhoto() {
      return photo;
   }

   public Long getManagerId() {
      return managerId;
   }

   @Override
   public boolean equals(Object obj) {
      if (!(obj instanceof EmployeeSnapshot)) {
         return false;
      }
      EmployeeSnapshot emp = (EmployeeSnapshot) obj;
      return this.getId().equals(emp.getId());
   }

   @Override
   public int hashCode() {
      int hash = 7;
      hash = 37 * hash + Objects.hashCode(this.id);
      return hash;
   }

}
