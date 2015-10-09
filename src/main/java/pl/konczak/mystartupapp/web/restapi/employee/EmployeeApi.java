package pl.konczak.mystartupapp.web.restapi.employee;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.konczak.mystartupapp.domain.employee.dto.EmployeeSnapshot;
import pl.konczak.mystartupapp.domain.employee.finder.IEmployeeSnapshotFinder;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/employee")
public class EmployeeApi {

   private final IEmployeeSnapshotFinder employeeSnapshotFinder;

   @Autowired
   public EmployeeApi(IEmployeeSnapshotFinder employeeSnapshotFinder) {
      this.employeeSnapshotFinder = employeeSnapshotFinder;
   }

   @ApiOperation(value = "Get all Employees [HR, MANAGER]",
      notes = "Returns all available Employees",
      position = 1)
   @ApiResponses(value = {
      @ApiResponse(code = 200,
         message = "Found list of all Employees")})
   @PreAuthorize("hasAnyRole('ROLE_HR', 'ROLE_MANAGER')")
   @RequestMapping(method = RequestMethod.GET)
   @JsonView(View.Summary.class)
   public List<Employee> list() {
      List<EmployeeSnapshot> employeeSnapshots = employeeSnapshotFinder.findAll();

      return employeeSnapshots.stream()
         .map(Employee::new)
         .collect(Collectors.toList());
   }

   @ApiOperation(value = "Get all actually employeed Employees [HR, MANAGER]",
      notes = "Returns actually employeed Employees",
      position = 2)
   @ApiResponses(value = {
      @ApiResponse(code = 200,
         message = "Found list of actually employeed Employees")})
   @PreAuthorize("hasAnyRole('ROLE_HR', 'ROLE_MANAGER')")
   @RequestMapping(value = "/active",
      method = RequestMethod.GET)
   @JsonView(View.Summary.class)
   public List<Employee> active() {
      List<EmployeeSnapshot> employeeSnapshots = employeeSnapshotFinder.findActive();

      return employeeSnapshots.stream()
         .map(Employee::new)
         .collect(Collectors.toList());
   }

   @ApiOperation(value = "Find Employee by ID [HR, MANAGER]",
      notes = "Returns Employee based on ID",
      position = 3)
   @ApiResponses(value = {
      @ApiResponse(code = 200,
         message = "Found Employee by specified ID"),
      @ApiResponse(code = 400,
         message = "Specified ID is invalid"),
      @ApiResponse(code = 404,
         message = "Employee not found")})
   @PreAuthorize("hasAnyRole('ROLE_HR', 'ROLE_MANAGER', 'ROLE_LINE_MANAGER')")
   @RequestMapping(value = "/{id}",
      method = RequestMethod.GET)
   public HttpEntity<Employee> get(@PathVariable("id") long id) {
      EmployeeSnapshot employeeSnapshot = employeeSnapshotFinder.findById(id);

      if (employeeSnapshot == null) {
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } else {
         EmployeeSnapshot managerSnapshot = null;
         if (employeeSnapshot.getManagerId() != null) {
            managerSnapshot = employeeSnapshotFinder.findById(employeeSnapshot.getManagerId());
         }
         if (managerSnapshot != null) {
            return new ResponseEntity<>(new Employee(employeeSnapshot, managerSnapshot), HttpStatus.OK);
         } else {
            return new ResponseEntity<>(new Employee(employeeSnapshot), HttpStatus.OK);
         }
      }
   }

   @ApiOperation(value = "Get distinct list of Employee titles",
      notes = "Returns list of Strings",
      position = 8)
   @ApiResponses(value = {
      @ApiResponse(code = 200,
         message = "Found list of Employee titles")})
   @RequestMapping(value = "/title",
      method = RequestMethod.GET)
   public Set<String> getTitles() {
      return employeeSnapshotFinder.findGroupedTitles();
   }

   @ApiOperation(value = "Get actually logged Employee [internal]",
      notes = "Returns actually logged Employee",
      position = 9)
   @ApiResponses(value = {
      @ApiResponse(code = 200,
         message = "Found actually logged Employee")})
   @RequestMapping(value = "/logged",
      method = RequestMethod.GET)
   public Employee getLoggedEmployee(Principal principal) {
      EmployeeSnapshot employeeSnapshot = employeeSnapshotFinder.findByUsername(principal.getName());
      EmployeeSnapshot managerSnapshot = null;
      if (employeeSnapshot.getManagerId() != null) {
         managerSnapshot = employeeSnapshotFinder.findById(employeeSnapshot.getManagerId());
      }
      if (managerSnapshot != null) {
         return new Employee(employeeSnapshot, managerSnapshot);
      } else {
         return new Employee(employeeSnapshot);
      }
   }

}
