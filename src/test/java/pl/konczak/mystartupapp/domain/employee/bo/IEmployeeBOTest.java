package pl.konczak.mystartupapp.domain.employee.bo;


import pl.konczak.mystartupapp.domain.employee.bo.IEmployeeBO;

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
import pl.konczak.mystartupapp.domain.employee.finder.IEmployeeSnapshotFinder;
import pl.konczak.mystartupapp.domain.employee.repo.IEmployeeRepository;

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
public class IEmployeeBOTest {

   private static final String CLAZZ = IEmployeeBOTest.class.getSimpleName();

   @Autowired
   private IEmployeeBO employeeBO;
   @Autowired
   private IEmployeeSnapshotFinder employeeSnapshotFinder;
   @Autowired
   private IEmployeeRepository employeeRepository;

   private EmployeeSnapshot employeeSnapshot;

   @Before
   public void setUp() {
      UUID uuid = UUID.randomUUID();
      LocalDateTime date = LocalDateTime.now().minusYears(1).withDayOfYear(1);

      employeeSnapshot = employeeBO.add(uuid, CLAZZ, CLAZZ, CLAZZ, CLAZZ, date, CLAZZ + "@cybercom.test");
   }
   
   @After
   public void tearDown() {
      employeeRepository.deleteAll();
   }

   @Test
   public void shouldAddEmployee() {
      //given
      UUID uuid = UUID.randomUUID();

      //when
      EmployeeSnapshot result = employeeBO.add(uuid, CLAZZ, CLAZZ, CLAZZ, CLAZZ,
         LocalDateTime.now().minusYears(1).withDayOfYear(1), CLAZZ + "@cybercom.test");

      //then
      assertNotNull(result);
      assertEquals(uuid.toString(), result.getGuid().toString());
      assertEquals(CLAZZ, result.getFirstname());
      assertEquals(CLAZZ, result.getLastname());
      assertEquals(CLAZZ, result.getTitle());
      assertEquals(CLAZZ, result.getUsername());
      assertEquals(CLAZZ + "@cybercom.test", result.getMail());
   }

   @Test
   public void shouldDeleteEmployee() {
      //given

      //when
      employeeBO.delete(employeeSnapshot.getId());

      //then
      assertNotNull(employeeSnapshotFinder.findById(employeeSnapshot.getId()).getDeletedAt());
      assertTrue(employeeSnapshotFinder.findById(employeeSnapshot.getId()).isDeleted());
   }

   @Test
   public void shouldUnlockEmployee() {
      //given
      employeeBO.delete(employeeSnapshot.getId());
      //when
      employeeBO.unlock(employeeSnapshot.getId());
      //then
      assertFalse(employeeSnapshotFinder.findById(employeeSnapshot.getId()).isDeleted());
   }

   @Test
   public void shouldSetAvatarIdOnSetAvatarIdCall() throws InterruptedException {
      //given
      Long avatarId = 21l;
      //when
      employeeBO.changeAvatarId(employeeSnapshot.getId(), avatarId);
      //then
      EmployeeSnapshot updated = employeeSnapshotFinder.findById(employeeSnapshot.getId());
      assertNotNull(updated);
      assertEquals(avatarId, updated.getAvatarId());
      assertNotEquals(updated.getCreatedAt(), updated.getUpdatedAt());
   }

   @Test
   public void shouldSetPhotoOnSetPhotoCall() throws InterruptedException {
      //given
      String string = "pika";
      //when
      EmployeeSnapshot updated = employeeBO.changePhoto(employeeSnapshot.getId(), string);
      //then
      assertNotNull(updated);
      assertEquals(string, updated.getPhoto());
      assertNotEquals(updated.getCreatedAt(), updated.getUpdatedAt());
   }

   @Test
   public void shouldSetPhotoToNullOnSetPhotoCallWithNullParameter() throws InterruptedException {
      //given
      String string = null;
      //when
      EmployeeSnapshot updated = employeeBO.changePhoto(employeeSnapshot.getId(), string);
      //then
      assertNotNull(updated);
      assertNull(updated.getPhoto());
      assertNotEquals(updated.getCreatedAt(), updated.getUpdatedAt());
   }

   @Test
   public void shouldEditEmployeeOnEditCall() {
      //given
      UUID uuid = UUID.randomUUID();
      String newStringValue = "Test";
      //when
      EmployeeSnapshot updated = employeeBO.edit(
         employeeSnapshot.getId(),
         uuid,
         newStringValue,
         newStringValue,
         newStringValue,
         newStringValue,
         newStringValue
      );
      //then
      assertNotNull(updated);
      assertEquals(uuid, updated.getGuid());
      assertEquals(newStringValue, updated.getFirstname());
      assertEquals(newStringValue, updated.getLastname());
      assertEquals(newStringValue, updated.getTitle());
      assertEquals(newStringValue, updated.getUsername());
      assertEquals(newStringValue, updated.getMail());
   }

}
