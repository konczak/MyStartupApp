package pl.konczak.mystartupapp.domain.employee.finder;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import pl.konczak.mystartupapp.domain.employee.dto.EmployeeSnapshot;

/**
 *
 * @author Mateusz.Glabicki
 */
public interface IEmployeeSnapshotFinder {

   EmployeeSnapshot findById(Long id);

   EmployeeSnapshot findByUuid(UUID guid);

   List<EmployeeSnapshot> findAll();

   EmployeeSnapshot findByUsername(String username);

   List<EmployeeSnapshot> findAll(Set<Long> ids);

   List<EmployeeSnapshot> findActive();

   Set<String> findGroupedTitles();
   
   Map<Long, EmployeeSnapshot> findAllAsMap(Set<Long> ids);
   
   List<EmployeeSnapshot> findSubordinatesOfLineManager(Long lineManagerId);

}
