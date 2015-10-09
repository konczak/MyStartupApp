package pl.konczak.mystartupapp.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EmployeeController {

   @PreAuthorize("hasAnyRole('ROLE_HR', 'ROLE_MANAGER')")
   @RequestMapping("/employee/list")
   public String list() {
      return "employee/list";
   }

   @PreAuthorize("hasAnyRole('ROLE_HR', 'ROLE_MANAGER', 'ROLE_LINE_MANAGER')")
   @RequestMapping("/employee/common")
   public String common() {
      return "employee/common";
   }

   @PreAuthorize("hasAnyRole('ROLE_HR', 'ROLE_MANAGER', 'ROLE_LINE_MANAGER')")
   @RequestMapping("/employee/details")
   public String detail() {
      return "employee/details";
   }

}
