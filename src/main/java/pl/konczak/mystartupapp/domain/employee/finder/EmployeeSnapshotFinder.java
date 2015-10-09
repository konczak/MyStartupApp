package pl.konczak.mystartupapp.domain.employee.finder;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import pl.konczak.mystartupapp.domain.employee.dto.EmployeeSnapshot;
import pl.konczak.mystartupapp.domain.employee.entity.Employee;
import pl.konczak.mystartupapp.domain.employee.repo.IEmployeeRepository;
import pl.konczak.mystartupapp.sharedkernel.annotations.Finder;

/**
 *
 * @author Mateusz.Glabicki
 */
@Finder
public class EmployeeSnapshotFinder
   implements IEmployeeSnapshotFinder {

   private final IEmployeeRepository employeeRepository;

   private static final Sort DEFAULT_SORT = new Sort("lastname", "firstname");

   @Autowired
   public EmployeeSnapshotFinder(IEmployeeRepository employeeRepository) {
      this.employeeRepository = employeeRepository;
   }

   private List<EmployeeSnapshot> convert(List<Employee> employees) {
      return employees.stream()
         .map(Employee::toSnapshot)
         .collect(Collectors.toList());
   }

   @Override
   public EmployeeSnapshot findById(Long id) {
      Employee employee = employeeRepository.findOne(id);
      return employee == null ? null : employee.toSnapshot();
   }

   @Override
   public List<EmployeeSnapshot> findAll() {
      List<Employee> employees = employeeRepository.findAll(DEFAULT_SORT);

      return convert(employees);
   }

   @Override
   public EmployeeSnapshot findByUuid(UUID guid) {
      List<Employee> employeeAll = employeeRepository.findAll();
      List<Employee> employees = employeeAll.stream()
         .filter(employee -> employee.toSnapshot().getGuid().toString().equals(guid.toString()))
         .collect(Collectors.toList());

      return employees.isEmpty() ? null : employees.get(0).toSnapshot();
   }

   @Override
   public EmployeeSnapshot findByUsername(String username) {
      List<Employee> employees = employeeRepository.findByUsernameIgnoreCase(username);

      return employees.isEmpty() ? null : employees.get(0).toSnapshot();

   }

   @Override
   public List<EmployeeSnapshot> findAll(Set<Long> ids) {
      List<Employee> employees = employeeRepository.findAll(ids);

      return convert(employees);
   }

   @Override
   public Set<String> findGroupedTitles() {
      List<String> strings = employeeRepository.findByTitleGrouped();
      return new HashSet<>(strings);
   }

   @Override
   public List<EmployeeSnapshot> findActive() {
      return convert(employeeRepository.findByDeletedFalse());
   }
   
   @Override
   public List<EmployeeSnapshot> findSubordinatesOfLineManager(Long lineManagerId){
      List<Employee> subordinates = employeeRepository.findByLineManagerId(lineManagerId);
      return convert(subordinates);
   }

   @Override
   public Map<Long, EmployeeSnapshot> findAllAsMap(Set<Long> ids) {
      List<Employee> employees = employeeRepository.findAll(ids);

      return employees
            .stream()
            .map(Employee::toSnapshot)
            .collect(Collectors.toMap(EmployeeSnapshot::getId, s ->s));
   }

}
