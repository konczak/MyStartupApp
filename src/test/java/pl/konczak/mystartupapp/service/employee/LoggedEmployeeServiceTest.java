package pl.konczak.mystartupapp.service.employee;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import pl.konczak.mystartupapp.Application;
import pl.konczak.mystartupapp.domain.employee.bo.IEmployeeBO;
import pl.konczak.mystartupapp.domain.employee.dto.EmployeeSnapshot;
import pl.konczak.mystartupapp.domain.employee.repo.IEmployeeRepository;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class LoggedEmployeeServiceTest {

   private static final String USERNAME = "logedEmployee";

   @Autowired
   private IEmployeeBO employeeBO;
   @Autowired
   private IEmployeeRepository employeeRepository;

   private EmployeeSnapshot employeeSnapshot;

   @Before
   public void setUp() {
      employeeSnapshot = addEmployee(USERNAME);
   }

   private EmployeeSnapshot addEmployee(String string) {
      return employeeBO.add(
         UUID.randomUUID(),
         string,
         string,
         string,
         string,
         LocalDateTime.now(),
         string + "@konczak.pl");
   }

   @After
   public void tearDown() {
      employeeRepository.deleteAll();
      employeeSnapshot = null;
   }

   @WithMockUser(username = USERNAME)
   @Test
   public void shouldReturnEmployeeSnapshot_whenGetSnapshotInvoked() {
      //given
      //when
      EmployeeSnapshot loggedEmployeeSnapshot = LoggedEmployeeService.getSnapshot();
      //then
      assertThat(loggedEmployeeSnapshot.getId(), is(equalTo(employeeSnapshot.getId())));
   }

   @WithMockUser
   @Test
   public void shouldReturnNull_whenGetSnapshotInvokedWithAnnonymusUser() {
      //given

      //when
      EmployeeSnapshot loggedEmployeeSnapshot = LoggedEmployeeService.getSnapshot();
      //then
      assertThat(loggedEmployeeSnapshot, is(nullValue()));
   }

   @WithMockUser(username = USERNAME,
      roles = "HR")
   @Test
   public void shouldReturnListOfSizeOneWithStringRoles_whenGetRolesInvokedByUserWithOneRole() {
      //given
      int expectedListSize = 1;
      //when
      List<String> roles = LoggedEmployeeService.getRoles();
      //then
      assertThat(roles.size(), is(equalTo(expectedListSize)));
      assertThat(roles.get(0).getClass(), is(equalTo(String.class)));
   }

   @WithMockUser
   @Test
   public void shouldReturnListOfSizeOneWithString_ROLE_USER_whenGetRolesInvokedByAnonymusUser() {
      //given
      //when
      List<String> roles = LoggedEmployeeService.getRoles();
      //then
      assertThat(roles.get(0), is(equalTo("ROLE_USER")));
   }

   @WithMockUser(roles = {"USER", "ADMIN", "HR"})
   @Test
   public void shouldReturnListWithRoles_whenGetRolesInoked() {
      //given
      //when
      List<String> roles = LoggedEmployeeService.getRoles();
      //then
      assertThat(roles.contains("ROLE_USER"), is(true));
      assertThat(roles.contains("ROLE_ADMIN"), is(true));
      assertThat(roles.contains("ROLE_HR"), is(true));
   }

}
