package pl.konczak.mystartupapp.domain.employee.bo;

import java.time.LocalDateTime;
import java.util.UUID;

import pl.konczak.mystartupapp.domain.employee.dto.EmployeeSnapshot;

/**
 *
 * @author Mateusz.Glabicki
 */
public interface IEmployeeBO {

   EmployeeSnapshot add(UUID guid, String firstname, String lastname,
      String title, String username,
      LocalDateTime createdAt, String mail);

   void delete(Long employeeId);

   EmployeeSnapshot edit(long id, UUID guid, String name, String lastname, String title, String username, String mail);

   EmployeeSnapshot unlock(Long employeeId);

   void changeAvatarId(Long id, Long fileId);

   void clearAvatar(Long id);

   EmployeeSnapshot changePhoto(Long employeeId, String photo);

   EmployeeSnapshot changeManager(Long employeeId, Long LineManagerId);

   void clearManager(Long employeeId);

   void clearPhoto(Long employeeId);
}
