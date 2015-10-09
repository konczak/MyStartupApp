package pl.konczak.mystartupapp.web.restapi.employee;

import pl.konczak.mystartupapp.domain.employee.dto.EmployeeSnapshot;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class Manager {

   private final Long id;
   private final String firstname;
   private final String lastname;
   private final String fullname;
   private final String mail;

   public Manager(EmployeeSnapshot employeeSnapshot) {
      this.id = employeeSnapshot.getId();
      this.firstname = employeeSnapshot.getFirstname();
      this.lastname = employeeSnapshot.getLastname();
      this.fullname = employeeSnapshot.getFullname();
      this.mail = employeeSnapshot.getMail();
   }

   @ApiModelProperty(value = "unique identifier of Manager",
      required = true)
   public Long getId() {
      return id;
   }

   @ApiModelProperty(value = "Manager firstname",
      required = true)
   public String getFirstname() {
      return firstname;
   }

   @ApiModelProperty(value = "Manager lastname",
      required = true)
   public String getLastname() {
      return lastname;
   }

   @ApiModelProperty(value = "Manager firstname and lastname",
      required = true)
   public String getFullname() {
      return fullname;
   }

   @ApiModelProperty(value = "Manager mail",
      required = true)
   public String getMail() {
      return mail;
   }

}
