package pl.konczak.mystartupapp.service.employee;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import pl.konczak.mystartupapp.domain.employee.dto.EmployeeSnapshot;
import pl.konczak.mystartupapp.domain.employee.finder.IEmployeeSnapshotFinder;

@Component
public class LoggedEmployeeService
   implements ApplicationContextAware {

   private static IEmployeeSnapshotFinder employeeSnapshotFinder;

   @Override
   public void setApplicationContext(ApplicationContext applicationContext)
      throws BeansException {
      employeeSnapshotFinder = (IEmployeeSnapshotFinder) applicationContext.getBean("employeeSnapshotFinder");
   }

   public static EmployeeSnapshot getSnapshot() {
      if (employeeSnapshotFinder == null) {
         throw new IllegalStateException();
      }
      String username = SecurityContextHolder.getContext().getAuthentication().getName();
      return employeeSnapshotFinder.findByUsername(username);
   }

   public static List<String> getRoles() {
      return findUserRoles();
   }

   public static boolean hasAnyRole(String... roles) {
      List<String> hasRoles = findUserRoles();
      boolean result = false;
      for (String role : roles) {
         if (hasRoles.contains(role)) {
            result = true;
            break;
         }
      }
      return result;
   }

   private static List<String> findUserRoles() {
      return SecurityContextHolder.getContext()
         .getAuthentication()
         .getAuthorities()
         .stream()
         .map(Object::toString)
         .collect(Collectors.toList());
   }
   
   public static boolean isManagerOf(EmployeeSnapshot employeeSnapshot) {
      Long loggedLineManagerId = getSnapshot().getId();
      return loggedLineManagerId.equals(employeeSnapshot.getManagerId());
   }

}
