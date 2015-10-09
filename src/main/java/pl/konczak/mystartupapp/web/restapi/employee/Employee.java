package pl.konczak.mystartupapp.web.restapi.employee;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.hateoas.ResourceSupport;

import pl.konczak.mystartupapp.domain.employee.dto.EmployeeSnapshot;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 *
 * @author Mateusz.Glabicki
 */
@ApiModel
public class Employee
   extends ResourceSupport
   implements Serializable {

   private static final long serialVersionUID = 123364444898230931L;
@JsonView(View.Summary.class)
   private final Long employeeId;
@JsonView(View.Summary.class)
   private final UUID guid;
@JsonView(View.Summary.class)
   private final String title;
@JsonView(View.Summary.class)
   private final String firstname;
@JsonView(View.Summary.class)
   private final String lastname;
@JsonView(View.Summary.class)
   private final String username;
@JsonView(View.Summary.class)
   private final LocalDateTime createdAt;
@JsonView(View.Summary.class)
   private final String mail;
@JsonView(View.Summary.class)
   private final boolean deleted;
@JsonView(View.Summary.class)
   private final LocalDateTime deletedAt;
@JsonView(View.Summary.class)
   private final LocalDateTime updatedAt;
@JsonView(View.Summary.class)
   private final long updatesCount;
@JsonView(View.Summary.class)
   private final Long avatarId;
@JsonView(View.Summary.class)
   private final String photo;
   private Manager manager;

   public Employee(EmployeeSnapshot employeeSnapshot) {
      this.employeeId = employeeSnapshot.getId();
      this.guid = employeeSnapshot.getGuid();
      this.title = employeeSnapshot.getTitle();
      this.firstname = employeeSnapshot.getFirstname();
      this.lastname = employeeSnapshot.getLastname();
      this.username = employeeSnapshot.getUsername();
      this.createdAt = employeeSnapshot.getCreatedAt();
      this.mail = employeeSnapshot.getMail();
      this.deleted = employeeSnapshot.isDeleted();
      this.deletedAt = employeeSnapshot.getDeletedAt();
      this.updatedAt = employeeSnapshot.getUpdatedAt();
      this.updatesCount = employeeSnapshot.getVersion();
      this.avatarId = employeeSnapshot.getAvatarId();
      this.photo = employeeSnapshot.getPhoto();
      
      this.add(linkTo(EmployeeApi.class).slash(this.employeeId).withSelfRel());
   }
   
   public Employee(EmployeeSnapshot employeeSnapshot, EmployeeSnapshot managerSnapshot){
      this(employeeSnapshot);
      this.manager = new Manager(managerSnapshot);
   }

   @ApiModelProperty(value = "unique identifier of Employee",
      required = true,
      position = 1)
   public Long getEmployeeId() {
      return employeeId;
   }

   @ApiModelProperty(value = "guid of Employee",
      required = true,
      position = 2)
   public UUID getGuid() {
      return guid;
   }

   @ApiModelProperty(value = "title of the Employee",
      required = true,
      position = 3)
   public String getTitle() {
      return title;
   }

   @ApiModelProperty(value = "firstname of the Employee",
      required = true,
      position = 4)
   public String getFirstname() {
      return firstname;
   }

   @ApiModelProperty(value = "lastname of the Employee",
      required = true,
      position = 5)
   public String getLastname() {
      return lastname;
   }

   @ApiModelProperty(value = "username of the Employee",
      required = true,
      position = 6)
   public String getUsername() {
      return username;
   }

   @ApiModelProperty(value = "date when Employee account was created",
      required = true,
      position = 7)
   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   @ApiModelProperty(value = "email of Employee",
      required = true,
      position = 8)
   public String getMail() {
      return mail;
   }

   @ApiModelProperty(value = "flag that Employee is deleted",
      required = true,
      position = 9)
   public boolean isDeleted() {
      return deleted;
   }

   @ApiModelProperty(value = "date when Employee was deleted",
      required = false,
      position = 10)
   public LocalDateTime getDeletedAt() {
      return deletedAt;
   }

   @ApiModelProperty(value = "Date when Employee was updated",
      required = false,
      position = 11)
   public LocalDateTime getUpdatedAt() {
      return updatedAt;
   }

   @ApiModelProperty(value = "count how many times Employee was updated",
      required = true,
      position = 12)
   public long getUpdatesCount() {
      return updatesCount;
   }

   @ApiModelProperty(value = "epmloyee avatar id in database",
      position = 13)
   public Long getAvatarId() {
      return avatarId;
   }

   @ApiModelProperty(value = "url to employee photo",
      position = 14)
   public String getPhoto() {
      return photo;
   }
   
   @ApiModelProperty(value = "employee Manager data",
      position = 15)
   public Manager getManager() {
      return manager;
   }
   
}
