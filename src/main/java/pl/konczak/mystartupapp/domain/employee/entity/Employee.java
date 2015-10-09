package pl.konczak.mystartupapp.domain.employee.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import pl.konczak.mystartupapp.config.persistance.converter.LocalDateTimePersistenceConverter;
import pl.konczak.mystartupapp.domain.employee.dto.EmployeeSnapshot;
import pl.konczak.mystartupapp.sharedkernel.exception.EntityInStateNewException;

/**
 *
 * @author Mateusz.Glabicki
 */
@Entity
public class Employee
   implements Serializable {

   private static final long serialVersionUID = 7972265007575707207L;

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   @NotNull
   @Column(unique = true,
      updatable = false)
   private UUID guid;

   @NotEmpty
   private String title;

   @NotEmpty
   private String firstname;

   @NotEmpty
   private String lastname;

   @NotNull
   @Convert(converter = LocalDateTimePersistenceConverter.class)
   private LocalDateTime createdAt;

   @Version
   private long version;

   @NotEmpty
   private String username;

   @NotEmpty
   private String mail;

   private boolean deleted;

   @Convert(converter = LocalDateTimePersistenceConverter.class)
   private LocalDateTime deletedAt;

   @Convert(converter = LocalDateTimePersistenceConverter.class)
   private LocalDateTime updatedAt;

   private Long avatarId;

   private String photo;

   private Long lineManagerId;

   protected Employee() {
   }

   public Employee(UUID guid, String title, String name, String lastname, String username,
      LocalDateTime createdAt, String mail) {
      this.guid = guid;
      this.title = title;
      this.firstname = name;
      this.lastname = lastname;
      this.username = username;
      this.createdAt = createdAt;
      this.mail = mail;
   }

   public void delete() {
      this.deleted = true;
      this.deletedAt = LocalDateTime.now();
   }

   public EmployeeSnapshot toSnapshot() {
      if (id == null) {
         throw new EntityInStateNewException();
      }

      return new EmployeeSnapshot(id, guid, title, firstname, lastname, username,
         createdAt, mail, deleted, deletedAt, updatedAt, version, avatarId, photo, lineManagerId);
   }

   public void editEmployee(UUID guid, String name, String lastname, String title, String username, String mail) {
      this.guid = guid;
      this.firstname = name;
      this.lastname = lastname;
      this.title = title;
      this.username = username;
      this.mail = mail;
      this.updatedAt = LocalDateTime.now();
   }

   public void unlock() {
      this.deleted = false;
      this.deletedAt = null;
   }

   Long getId() {
      return id;
   }

   public void changeAvatarId(Long avatarId) {
      this.updatedAt = LocalDateTime.now();
      this.avatarId = avatarId;
   }

   public void clearAvatar() {
      this.updatedAt = LocalDateTime.now();
      this.avatarId = null;
   }

   public void changePhoto(String photo) {
      this.updatedAt = LocalDateTime.now();
      this.photo = photo;
   }

   public void clearPhoto() {
      this.updatedAt = LocalDateTime.now();
      this.photo = null;
   }

   public void changeManagerId(Long lineManagerId) {
      this.updatedAt = LocalDateTime.now();
      this.lineManagerId = lineManagerId;
   }

   public void clearManager() {
      this.updatedAt = LocalDateTime.now();
      this.lineManagerId = null;
   }

}
