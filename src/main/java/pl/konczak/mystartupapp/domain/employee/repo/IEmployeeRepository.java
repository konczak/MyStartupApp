package pl.konczak.mystartupapp.domain.employee.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.konczak.mystartupapp.domain.employee.entity.Employee;

/**
 *
 * @author Mateusz.Glabicki
 */
public interface IEmployeeRepository
   extends JpaRepository<Employee, Long> {

   List<Employee> findByUsernameIgnoreCase(String username);

   @Query("SELECT e.title FROM Employee AS e GROUP BY e.title")
   List<String> findByTitleGrouped();

   List<Employee> findByGuid(UUID guid);

   List<Employee> findByDeletedFalse();

   List<Employee> findByLineManagerId(Long lineManagerId);

}
