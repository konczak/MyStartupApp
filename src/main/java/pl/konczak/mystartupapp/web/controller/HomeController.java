package pl.konczak.mystartupapp.web.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pl.konczak.mystartupapp.domain.employee.dto.EmployeeSnapshot;
import pl.konczak.mystartupapp.domain.employee.finder.IEmployeeSnapshotFinder;
import pl.konczak.mystartupapp.sharedkernel.constant.Profiles;

@Controller
public class HomeController {

   private final Environment environment;
   private final IEmployeeSnapshotFinder employeeSnapshotFinder;

   @Autowired
   public HomeController(Environment environment,
      IEmployeeSnapshotFinder employeeSnapshotFinder) {
      this.environment = environment;
      this.employeeSnapshotFinder = employeeSnapshotFinder;
   }

   @RequestMapping("/")
   public ModelAndView root(ModelAndView modelAndView, Principal principal) {
      modelAndView.setViewName("index");

      List<String> activeProfiles = Arrays.asList(environment.getActiveProfiles());

      addIfContains(modelAndView, activeProfiles, Profiles.BASIC_AUTHENTICATION);
      addIfContains(modelAndView, activeProfiles, Profiles.DEVELOPMENT);

      EmployeeSnapshot employeeSnapshot = employeeSnapshotFinder.findByUsername(principal.getName());
      modelAndView.addObject("GUID", employeeSnapshot.getGuid().toString());

      return modelAndView;
   }

   @RequestMapping("/login")
   public ModelAndView login(ModelAndView modelAndView) {
      modelAndView.setViewName("login");

      List<String> activeProfiles = Arrays.asList(environment.getActiveProfiles());

      addIfContains(modelAndView, activeProfiles, Profiles.BASIC_AUTHENTICATION);
      addIfContains(modelAndView, activeProfiles, Profiles.DEVELOPMENT);

      return modelAndView;
   }

   private void addIfContains(ModelAndView modelAndView, List<String> profiles, String profile) {
      if (profiles.contains(profile)) {
         modelAndView.addObject(profile, true);
      }
   }

}
