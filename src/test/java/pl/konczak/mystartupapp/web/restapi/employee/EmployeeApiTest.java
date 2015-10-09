package pl.konczak.mystartupapp.web.restapi.employee;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import pl.konczak.mystartupapp.Application;
import pl.konczak.mystartupapp.domain.employee.bo.IEmployeeBO;
import pl.konczak.mystartupapp.domain.employee.dto.EmployeeSnapshot;
import pl.konczak.mystartupapp.domain.employee.finder.IEmployeeSnapshotFinder;
import pl.konczak.mystartupapp.domain.employee.repo.IEmployeeRepository;
import pl.konczak.mystartupapp.util.IsISODateTime;
import pl.konczak.mystartupapp.util.IsUUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class EmployeeApiTest {

   private static final String CLAZZ = EmployeeApiTest.class.getSimpleName();
   @Autowired
   private WebApplicationContext wac;
   @Autowired
   private IEmployeeBO employeeBO;
   @Autowired
   private IEmployeeSnapshotFinder employeeSnapshotFinder;

   private EmployeeSnapshot employeeSnapshotOne;
   private EmployeeSnapshot employeeSnapshotTwo;

   @Autowired
   private IEmployeeRepository employeeRepository;

   private MockMvc mockMvc;

   @Before
   public void setUp() {
      this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
         .apply(springSecurity())
         .build();

      employeeSnapshotTwo = addEmployee(CLAZZ);
      employeeSnapshotOne = addEmployee(CLAZZ + "2");
      employeeBO.delete(employeeSnapshotOne.getId());
   }

   @After
   public void tearDown() {
      employeeRepository.delete(employeeSnapshotOne.getId());
      employeeRepository.delete(employeeSnapshotTwo.getId());
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

   @WithMockUser(roles = "HR")
   @Test
   public void shouldReturnListOfEmployees()
      throws Exception {
      //given
      List<EmployeeSnapshot> employeeSnapshots = employeeSnapshotFinder.findAll();

      MockHttpServletRequestBuilder request
         = get("/api/employee")
         .accept(MediaType.parseMediaType("application/json;charset=UTF-8"));

      //when
      ResultActions result = this.mockMvc.perform(request);

      //then
      result
         .andExpect(status().isOk())
         .andExpect(content().contentType("application/json;charset=UTF-8"))
         .andExpect(jsonPath("$").isArray())
         .andExpect(jsonPath("$", hasSize(equalTo(employeeSnapshots.size()))))
         .andExpect(jsonPath("$", hasSize((greaterThan(0)))))
         .andExpect(jsonPath("$[*].employeeId", hasItems(isA(Integer.class))))
         .andExpect(jsonPath("$[*].guid", hasItems(IsUUID.isUUID())))
         .andExpect(jsonPath("$[*].title").exists())
         .andExpect(jsonPath("$[*].firstname").exists())
         .andExpect(jsonPath("$[*].lastname").exists())
         .andExpect(jsonPath("$[*].username").exists())
         .andExpect(jsonPath("$[*].createdAt", hasItems(IsISODateTime.isISODateTime())))
         .andExpect(jsonPath("$[*].manager").doesNotExist())
         .andExpect(jsonPath("$[*].mail").exists())
         .andExpect(jsonPath("$[*].deleted", hasItems(isA(Boolean.class))))
         .andExpect(jsonPath("$[*].deletedAt").exists())
         .andExpect(jsonPath("$[*].updatedAt").exists())
         .andExpect(jsonPath("$[*].updatesCount", hasItems(isA(Integer.class))));
   }

   @WithMockUser(roles = "HR")
   @Test
   public void shouldReturnListOfActiveEmployees()
      throws Exception {
      //given
      List<EmployeeSnapshot> employeeSnapshots = employeeSnapshotFinder.findActive();

      MockHttpServletRequestBuilder request
         = get("/api/employee/active")
         .accept(MediaType.parseMediaType("application/json;charset=UTF-8"));

      //when
      ResultActions result = this.mockMvc.perform(request);

      //then
      result
         .andExpect(status().isOk())
         .andExpect(content().contentType("application/json;charset=UTF-8"))
         .andExpect(jsonPath("$").isArray())
         .andExpect(jsonPath("$", hasSize(equalTo(employeeSnapshots.size()))))
         .andExpect(jsonPath("$", hasSize((greaterThan(0)))))
         .andExpect(jsonPath("$[*].employeeId", hasItems(isA(Integer.class))))
         .andExpect(jsonPath("$[*].guid", hasItems(IsUUID.isUUID())))
         .andExpect(jsonPath("$[*].title").exists())
         .andExpect(jsonPath("$[*].firstname").exists())
         .andExpect(jsonPath("$[*].lastname").exists())
         .andExpect(jsonPath("$[*].username").exists())
         .andExpect(jsonPath("$[*].createdAt", hasItems(IsISODateTime.isISODateTime())))
         .andExpect(jsonPath("$[*].manager").doesNotExist())
         .andExpect(jsonPath("$[*].mail").exists())
         .andExpect(jsonPath("$[*].deleted", hasItems(isA(Boolean.class))))
         .andExpect(jsonPath("$[*].deletedAt").exists())
         .andExpect(jsonPath("$[*].updatedAt").exists())
         .andExpect(jsonPath("$[*].updatesCount").exists());
   }

   @WithMockUser(username = "piko.piko")
   @Test
   public void shouldReturnLoggedEmployeeWithManagerWhenManagerIdIsNotNull()
      throws Exception {
      //given
      String piko = "piko.piko";
      EmployeeSnapshot employeeSnapshot = addEmployee(piko);
      EmployeeSnapshot managerSnapshot = addEmployee("manager");
      employeeBO.changeManager(employeeSnapshot.getId(), managerSnapshot.getId());
      MockHttpServletRequestBuilder request
         = get("/api/employee/logged")
         .accept(MediaType.parseMediaType("application/json;charset=UTF-8"));

      //when
      ResultActions result = this.mockMvc.perform(request);

      //then
      result
         .andExpect(status().isOk())
         .andExpect(content().contentType("application/json;charset=UTF-8"))
         .andExpect(jsonPath("$.manager").exists())
         .andExpect(jsonPath("$.employeeId").value(employeeSnapshot.getId().intValue()))
         .andExpect(jsonPath("$.guid").value(IsUUID.isUUID()))
         .andExpect(jsonPath("$.title").value(is(piko)))
         .andExpect(jsonPath("$.firstname").value(is(piko)))
         .andExpect(jsonPath("$.lastname").value(is(piko)))
         .andExpect(jsonPath("$.username").value(is(piko)))
         .andExpect(jsonPath("$.manager.firstname").value(is(managerSnapshot.getFirstname())))
         .andExpect(jsonPath("$.manager.lastname").value(is(managerSnapshot.getLastname())))
         .andExpect(jsonPath("$.manager.fullname").value(is(managerSnapshot.getFullname())))
         .andExpect(jsonPath("$.manager.mail").value(is(managerSnapshot.getMail())))
         .andExpect(jsonPath("$.createdAt", IsISODateTime.isISODateTime()))
         .andExpect(jsonPath("$.mail").value(is(piko + "@konczak.pl")))
         .andExpect(jsonPath("$.deleted", is(false)))
         .andExpect(jsonPath("$.deletedAt").value(nullValue()))
         .andExpect(jsonPath("$.updatedAt").value(is(IsISODateTime.isISODateTime())))
         .andExpect(jsonPath("$.updatesCount").value(1))
         .andExpect(jsonPath("$.links", hasSize(greaterThan(0))))
         .andExpect(jsonPath("$.links[?(@.rel == 'self')]").exists());

      employeeRepository.delete(employeeSnapshot.getId());
      employeeRepository.delete(managerSnapshot.getId());
   }

   @WithMockUser(username = "pika.pika")
   @Test
   public void shouldReturnLoggedEmployeeWithoutManagerWhenmanagerIdIsNull()
      throws Exception {
      //given
      String piko = "pika.pika";
      EmployeeSnapshot employeeSnapshot = addEmployee(piko);
      employeeBO.clearManager(employeeSnapshot.getId());
      employeeSnapshot = employeeSnapshotFinder.findById(employeeSnapshot.getId());
      MockHttpServletRequestBuilder request
         = get("/api/employee/logged")
         .accept(MediaType.parseMediaType("application/json;charset=UTF-8"));

      //when
      ResultActions result = this.mockMvc.perform(request);

      //then
      result
         .andExpect(status().isOk())
         .andExpect(content().contentType("application/json;charset=UTF-8"))
         .andExpect(jsonPath("$.employeeId").value(employeeSnapshot.getId().intValue()))
         .andExpect(jsonPath("$.guid").value(IsUUID.isUUID()))
         .andExpect(jsonPath("$.title").value(is(piko)))
         .andExpect(jsonPath("$.firstname").value(is(piko)))
         .andExpect(jsonPath("$.lastname").value(is(piko)))
         .andExpect(jsonPath("$.username").value(is(piko)))
         .andExpect(jsonPath("$.createdAt", IsISODateTime.isISODateTime()))
         .andExpect(jsonPath("$.manager").value(nullValue()))
         .andExpect(jsonPath("$.mail").value(is(piko + "@konczak.pl")))
         .andExpect(jsonPath("$.deleted", is(false)))
         .andExpect(jsonPath("$.deletedAt").value(nullValue()))
         .andExpect(jsonPath("$.updatedAt").value(is(anyOf(IsISODateTime.isISODateTime(), nullValue(String.class)))))
         .andExpect(jsonPath("$.updatesCount").value(((Long)employeeSnapshot.getVersion()).intValue()))
         .andExpect(jsonPath("$.links", hasSize(greaterThan(0))))
         .andExpect(jsonPath("$.links[?(@.rel == 'self')]").exists());

      employeeRepository.delete(employeeSnapshot.getId());
   }

   @WithMockUser(roles = "HR")
   @Test
   public void shouldReturnOkOnRefresh()
      throws Exception {
      //given
      MockHttpServletRequestBuilder request
         = post("/api/employee/refresh")
         .accept(MediaType.parseMediaType("application/json;charset=UTF-8"));

      //when
      ResultActions result = this.mockMvc.perform(request);

      //then
      result
         .andExpect(status().isNoContent());
   }

   @Test
   public void shouldReturnListOfEmployeeTitles()
      throws Exception {
      //given
      Set<String> titles = employeeSnapshotFinder.findGroupedTitles();

      MockHttpServletRequestBuilder request
         = get("/api/employee/title")
         .accept(MediaType.parseMediaType("application/json;charset=UTF-8"));

      //when
      ResultActions result = this.mockMvc.perform(request);

      //then
      result
         .andExpect(status().isOk())
         .andExpect(content().contentType("application/json;charset=UTF-8"))
         .andExpect(jsonPath("$").isArray())
         .andExpect(jsonPath("$", hasSize(equalTo(titles.size()))))
         .andExpect(jsonPath("$", hasSize((greaterThan(0)))))
         .andExpect(jsonPath("$[*]").value(containsInAnyOrder(titles.toArray())));
   }

   @WithMockUser(roles = "HR")
   @Test
   public void shouldReturnEmployeeByIdWithoutManagerWhenManagerIdIsNull()
      throws Exception {
      //given
      List<EmployeeSnapshot> employeeSnapshots = employeeSnapshotFinder.findAll();
      EmployeeSnapshot employeeSnapshot = employeeSnapshots.get(0);
      employeeBO.clearManager(employeeSnapshot.getId());
      employeeSnapshot = employeeSnapshotFinder.findById(employeeSnapshot.getId());
      MockHttpServletRequestBuilder request
         = get("/api/employee/{id}", employeeSnapshot.getId())
         .accept(MediaType.parseMediaType("application/json;charset=UTF-8"));

      //when
      ResultActions result = this.mockMvc.perform(request);

      //then
      result
         .andExpect(status().isOk())
         .andExpect(content().contentType("application/json;charset=UTF-8"))
         .andExpect(jsonPath("$.employeeId").value(employeeSnapshot.getId().intValue()))
         .andExpect(jsonPath("$.guid").value(is(IsUUID.isUUID())))
         .andExpect(jsonPath("$.title").value(is(employeeSnapshot.getTitle())))
         .andExpect(jsonPath("$.firstname").value(is(employeeSnapshot.getFirstname())))
         .andExpect(jsonPath("$.lastname").value(is(employeeSnapshot.getLastname())))
         .andExpect(jsonPath("$.username").value(is(employeeSnapshot.getUsername())))
         .andExpect(jsonPath("$.createdAt", is(IsISODateTime.isISODateTime())))
         .andExpect(jsonPath("$.manager").value(nullValue()))
         .andExpect(jsonPath("$.mail").value(is(employeeSnapshot.getMail())))
         .andExpect(jsonPath("$.deleted").value(is(employeeSnapshot.isDeleted())))
         .andExpect(jsonPath("$.deletedAt").value(is(anyOf(IsISODateTime.isISODateTime(), nullValue(String.class)))))
         .andExpect(jsonPath("$.updatedAt").value(is(anyOf(IsISODateTime.isISODateTime(), nullValue(String.class)))))
         .andExpect(jsonPath("$.updatesCount").value(is((int) employeeSnapshot.getVersion())))
         .andExpect(jsonPath("$.links", hasSize(greaterThan(0))))
         .andExpect(jsonPath("$.links[?(@.rel == 'self')]").exists());

      employeeRepository.delete(employeeSnapshot.getId());
   }

   @WithMockUser(roles = "HR")
   @Test
   public void shouldReturnEmployeeByIdWithManagerWhenManagerIdIsNotNull()
      throws Exception {
      //given
      List<EmployeeSnapshot> employeeSnapshots = employeeSnapshotFinder.findAll();
      EmployeeSnapshot employeeSnapshot = employeeSnapshots.get(0);
      EmployeeSnapshot managerSnapshot = addEmployee("manager");
      employeeBO.changeManager(employeeSnapshot.getId(), managerSnapshot.getId());
      employeeSnapshot = employeeSnapshotFinder.findById(employeeSnapshot.getId());
      MockHttpServletRequestBuilder request
         = get("/api/employee/{id}", employeeSnapshot.getId())
         .accept(MediaType.parseMediaType("application/json;charset=UTF-8"));

      //when
      ResultActions result = this.mockMvc.perform(request);

      //then
      result
         .andExpect(status().isOk())
         .andExpect(content().contentType("application/json;charset=UTF-8"))
         .andExpect(jsonPath("$.employeeId").value(employeeSnapshot.getId().intValue()))
         .andExpect(jsonPath("$.guid").value(is(IsUUID.isUUID())))
         .andExpect(jsonPath("$.title").value(is(employeeSnapshot.getTitle())))
         .andExpect(jsonPath("$.firstname").value(is(employeeSnapshot.getFirstname())))
         .andExpect(jsonPath("$.lastname").value(is(employeeSnapshot.getLastname())))
         .andExpect(jsonPath("$.username").value(is(employeeSnapshot.getUsername())))
         .andExpect(jsonPath("$.createdAt", is(IsISODateTime.isISODateTime())))
         .andExpect(jsonPath("$.manager").exists())
         .andExpect(jsonPath("$.manager.firstname").value(is(managerSnapshot.getFirstname())))
         .andExpect(jsonPath("$.manager.lastname").value(is(managerSnapshot.getLastname())))
         .andExpect(jsonPath("$.manager.fullname").value(is(managerSnapshot.getFullname())))
         .andExpect(jsonPath("$.manager.mail").value(is(managerSnapshot.getMail())))
         .andExpect(jsonPath("$.mail").value(is(employeeSnapshot.getMail())))
         .andExpect(jsonPath("$.deleted").value(is(employeeSnapshot.isDeleted())))
         .andExpect(jsonPath("$.deletedAt").value(is(anyOf(IsISODateTime.isISODateTime(), nullValue(String.class)))))
         .andExpect(jsonPath("$.updatedAt").value(is(anyOf(IsISODateTime.isISODateTime(), nullValue(String.class)))))
         .andExpect(jsonPath("$.updatesCount").value(is((int) employeeSnapshot.getVersion())))
         .andExpect(jsonPath("$.links", hasSize(greaterThan(0))))
         .andExpect(jsonPath("$.links[?(@.rel == 'self')]").exists());

      employeeRepository.delete(employeeSnapshot.getId());
      employeeRepository.delete(managerSnapshot.getId());
   }

   @WithMockUser(roles = "HR")
   @Test
   public void shouldReturnNotFoundInGetEmployeeById()
      throws Exception {
      //given
      MockHttpServletRequestBuilder request
         = get("/api/employee/{id}", 123123123)
         .accept(MediaType.parseMediaType("application/json;charset=UTF-8"));

      //when
      ResultActions result = this.mockMvc.perform(request);

      //then
      result
         .andExpect(status().isNotFound());
   }

}
