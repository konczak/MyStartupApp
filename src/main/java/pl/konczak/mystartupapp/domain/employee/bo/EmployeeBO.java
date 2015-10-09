package pl.konczak.mystartupapp.domain.employee.bo;

import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pl.konczak.mystartupapp.domain.employee.dto.EmployeeSnapshot;
import pl.konczak.mystartupapp.domain.employee.entity.Employee;
import pl.konczak.mystartupapp.domain.employee.exception.EmployeeNotExistsException;
import pl.konczak.mystartupapp.domain.employee.repo.IEmployeeRepository;
import pl.konczak.mystartupapp.sharedkernel.annotations.BussinesObject;

/**
 *
 * @author Mateusz.Glabicki
 */
@BussinesObject
public class EmployeeBO
   implements IEmployeeBO {

   private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeBO.class);
   private final IEmployeeRepository employeeRepository;

   @Autowired
   public EmployeeBO(IEmployeeRepository employeeRepository) {
      this.employeeRepository = employeeRepository;
   }

   @Override
   public EmployeeSnapshot add(UUID guid, String name, String lastname, String title, String username,
      LocalDateTime createdAt, String mail) {
      Employee employee = new Employee(guid, title, name, lastname, username, createdAt, mail);

      employee = employeeRepository.save(employee);

      EmployeeSnapshot employeeSnapshot = employee.toSnapshot();

      LOGGER.info("Add Employee <{}> <{}> <{} {}> <{}> <{}> <{}>",
         employeeSnapshot.getId(),
         employeeSnapshot.getGuid(), employeeSnapshot.getLastname(), employeeSnapshot.getFirstname(),
         employeeSnapshot.getTitle(), employeeSnapshot.getUsername(), employeeSnapshot.getMail());

      return employeeSnapshot;
   }

   @Override
   public EmployeeSnapshot edit(long id, UUID guid, String name, String lastname, String title, String username,
      String mail) {

      Employee employee = employeeRepository.findOne(id);

      employee.editEmployee(guid, name, lastname, title, username, mail);

      employeeRepository.save(employee);

      EmployeeSnapshot employeeSnapshot = employee.toSnapshot();

      LOGGER.info("Edit Employee <{}> <{}> <{}> <{}> <{}> <{}>",
         employeeSnapshot.getId(), employeeSnapshot.getLastname(), employeeSnapshot.getFirstname(),
         employeeSnapshot.getTitle(), employeeSnapshot.getUsername(), employeeSnapshot.getMail());
      return employeeSnapshot;
   }

   @Override
   public void delete(Long employeeId) {
      Employee employee = employeeRepository.findOne(employeeId);
      employee.delete();
      employeeRepository.save(employee);

      EmployeeSnapshot employeeSnapshot = employee.toSnapshot();

      LOGGER.info("Employee <{}> <{}> marked as deleted",
         employeeId, employeeSnapshot.getUsername());
   }

   @Override
   public EmployeeSnapshot unlock(Long employeeId) {
      Employee employee = employeeRepository.findOne(employeeId);
      employee.unlock();
      employee = employeeRepository.save(employee);

      LOGGER.info("Employee <{}> signed as employeed", employeeId);

      return employee.toSnapshot();
   }

   @Override
   public void changeAvatarId(Long employeeId, Long avatarId) {
      Employee employee = employeeRepository.findOne(employeeId);
      employee.changeAvatarId(avatarId);
      employee = employeeRepository.save(employee);

      LOGGER.info("Employee <{}> avatarId set to <{}>", employeeId, avatarId);
   }

   @Override
   public void clearAvatar(Long employeeId) {
      Employee employee = employeeRepository.findOne(employeeId);
      employee.clearAvatar();
      employee = employeeRepository.save(employee);

      LOGGER.info("Employee <{}> avatar is clear", employeeId);
   }

   @Override
   public EmployeeSnapshot changePhoto(Long employeeId, String photo) {
      Employee employee = employeeRepository.findOne(employeeId);
      employee.changePhoto(photo);
      employee = employeeRepository.save(employee);

      LOGGER.info("Employee <{}> rpgPhoto set to <{}>", employeeId, photo);

      return employee.toSnapshot();
   }

   @Override
   public void clearPhoto(Long employeeId) {
      Employee employee = employeeRepository.findOne(employeeId);
      employee.clearPhoto();
      employeeRepository.save(employee);
      LOGGER.info("Employee <{}> photo is clear", employeeId);
   }
   
   @Override
   public EmployeeSnapshot changeManager(Long employeeId, Long managerId) {
      Employee employee = employeeRepository.findOne(employeeId);
      Employee manager = employeeRepository.findOne(managerId);

      if (manager == null) {
         throw new EmployeeNotExistsException("Manger with ID" + managerId + " not exists");
      }

      employee.changeManagerId(managerId);
      employee = employeeRepository.save(employee);

      LOGGER.info("Employee <{}> managerId changed to <{}>", employeeId, managerId);

      return employee.toSnapshot();
   }

   @Override
   public void clearManager(Long employeeId) {
      Employee employee = employeeRepository.findOne(employeeId);
      employee.clearManager();
      employee = employeeRepository.save(employee);

      LOGGER.info("Employee <{}> managerId is clear", employeeId);
   }

}
