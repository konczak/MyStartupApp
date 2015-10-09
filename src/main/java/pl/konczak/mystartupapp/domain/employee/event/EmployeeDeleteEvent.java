package pl.konczak.mystartupapp.domain.employee.event;

import org.springframework.context.ApplicationEvent;

import pl.konczak.mystartupapp.domain.employee.dto.EmployeeSnapshot;

public class EmployeeDeleteEvent
   extends ApplicationEvent {

   private static final long serialVersionUID = -2864077050151556923L;

   private final EmployeeSnapshot employeeSnapshot;

   public EmployeeDeleteEvent(EmployeeSnapshot employeeSnapshot) {
      super(employeeSnapshot);
      this.employeeSnapshot = employeeSnapshot;
   }

   public EmployeeSnapshot getEmployeeSnapshot() {
      return employeeSnapshot;
   }
}
