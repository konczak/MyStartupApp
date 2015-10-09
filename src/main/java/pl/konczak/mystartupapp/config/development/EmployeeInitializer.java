package pl.konczak.mystartupapp.config.development;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.konczak.mystartupapp.domain.employee.bo.IEmployeeBO;
import pl.konczak.mystartupapp.sharedkernel.constant.Profiles;

/**
 *
 * @author Rafal.Polak
 *
 */
@Component
@Profile(Profiles.DEVELOPMENT)
public class EmployeeInitializer
   implements IEmployeeInitializer {

   @Autowired
   private IEmployeeBO employeeBO;

   @Transactional
   @Override
   public void initalizer() {

      employeeBO.add(UUID.randomUUID(), "Pi", "Ko",
         "Engineer", "piko",
         LocalDateTime.now(), "piko@konczak.pl");
      
      employeeBO.add(UUID.randomUUID(), "HR", "User",
         "HR", "hruser",
         LocalDateTime.now(), "hruser@konczak.pl");
      
      employeeBO.add(UUID.randomUUID(), "Ad", "Min",
         "Admin", "admin",
         LocalDateTime.now(), "admin@konczak.pl");

   }

}
