package pl.konczak.mystartupapp.web.restapi.questionnairetemplate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import org.springframework.hateoas.ResourceSupport;

import pl.konczak.mystartupapp.domain.employee.dto.EmployeeSnapshot;

import com.fasterxml.jackson.annotation.JsonView;

@ApiModel
public class Employee extends ResourceSupport {

   @JsonView(View.List.class)
   private final Long employeeId;
   @JsonView(View.List.class)
   private final String firstname;
   @JsonView(View.List.class)
   private final String lastname;
   @JsonView(View.List.class)
   private final String mail;

   public Employee(EmployeeSnapshot employeeSnapshot) {
      this.employeeId = employeeSnapshot.getId();
      this.firstname = employeeSnapshot.getFirstname();
      this.lastname = employeeSnapshot.getLastname();
      this.mail = employeeSnapshot.getMail();
   }

   @ApiModelProperty(
         value = "Unique identifier of employee", 
         required = true, 
         position = 1)
   public Long getEmployeeId() {
      return employeeId;
   }

   @ApiModelProperty(
         value = "First name of Employee", 
         required = true, 
         position = 2)
   public String getFirstname() {
      return firstname;
   }

   @ApiModelProperty(
         value = "Last name of Employee", 
         required = true, 
         position = 3)
   public String getLastname() {
      return lastname;
   }

   @ApiModelProperty(
         value = "Email address of employee", 
         required = true, 
         position = 4)
   public String getMail() {
      return mail;
   }

}
