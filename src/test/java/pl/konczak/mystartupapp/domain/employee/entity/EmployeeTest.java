package pl.konczak.mystartupapp.domain.employee.entity;

import pl.konczak.mystartupapp.domain.employee.entity.Employee;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import pl.konczak.mystartupapp.Application;
import pl.konczak.mystartupapp.domain.employee.dto.EmployeeSnapshot;
import pl.konczak.mystartupapp.domain.employee.repo.IEmployeeRepository;
import pl.konczak.mystartupapp.sharedkernel.exception.EntityInStateNewException;

import static org.junit.Assert.*;

/**
 *
 * @author Mateusz.Glabicki
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class EmployeeTest {

   private static final String CLAZZ = EmployeeTest.class.getSimpleName();
   private Employee employee;
   @Autowired
   private IEmployeeRepository employeeRepository;
   private UUID uuid;

   @Before
   public void setUp() {
      uuid = UUID.randomUUID();
      employee = new Employee(uuid, CLAZZ, CLAZZ, CLAZZ, CLAZZ, LocalDateTime.now(), CLAZZ);
      employeeRepository.save(employee);
   }

   @After
   public void tearDown() {
      employeeRepository.deleteAll();
      employee = null;
   }

   @Test(expected = EntityInStateNewException.class)
   public void shouldThrowEntityInStateNewExceptionInToSnapshotWhenEntityIsNotPersisted() {
      //given
      Employee employee = new Employee(uuid, CLAZZ, CLAZZ, CLAZZ, CLAZZ, LocalDateTime.now(), CLAZZ);

      //when
      employee.toSnapshot();
      //then

   }

   @Test
   public void shouldEditEmployeeInEdit() {
      //given
      String test = "Test";
      //when
      employee.editEmployee(uuid, CLAZZ, CLAZZ, CLAZZ, test, CLAZZ);

      //then
      assertNotNull(employeeRepository.findByUsernameIgnoreCase(test));
   }

   @Test
   public void shouldUnlockEmployee() {
      //given
      employee.delete();
      employee = employeeRepository.findOne(employee.toSnapshot().getId());

      //when
      employee.unlock();
      //then
      assertNull(employeeRepository.findOne(employee.toSnapshot().getId()).toSnapshot().getDeletedAt());
   }

   @Test
   public void shouldSetEmployeeAvatarId(){
      //given
      Long avatarId = 3L;
      //when
      employee.changeAvatarId(avatarId);
      //then
      EmployeeSnapshot employeeSnapshot = employee.toSnapshot();
      assertEquals(avatarId, employeeSnapshot.getAvatarId());
      assertNotEquals(employeeSnapshot.getUpdatedAt(), employeeSnapshot.getCreatedAt());
   }

   @Test
   public void shouldSetEmployeeAvatarIdToNull(){
      //given
      Long avatarId = null;
      //when
      employee.changeAvatarId(avatarId);
      //then
      EmployeeSnapshot employeeSnapshot = employee.toSnapshot();
      assertNull(employeeSnapshot.getAvatarId());
      assertNotEquals(employeeSnapshot.getUpdatedAt(), employeeSnapshot.getCreatedAt());
   }

   @Test
   public void shouldSetEmployeePhoto(){
      //given
      String string = "pika";
      //when
      employee.changePhoto(string);
      //then
      EmployeeSnapshot employeeSnapshot = employee.toSnapshot();
      assertEquals(string, employeeSnapshot.getPhoto());
      assertNotEquals(employeeSnapshot.getUpdatedAt(), employeeSnapshot.getCreatedAt());
   }

   @Test
   public void shouldClearEmployeePhoto(){
      //given
      //when
      employee.clearPhoto();
      //then
      EmployeeSnapshot employeeSnapshot = employee.toSnapshot();
      assertNull(employeeSnapshot.getPhoto());
      assertNotEquals(employeeSnapshot.getUpdatedAt(), employeeSnapshot.getCreatedAt());
   }
}
