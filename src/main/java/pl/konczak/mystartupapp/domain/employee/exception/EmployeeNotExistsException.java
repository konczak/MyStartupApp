package pl.konczak.mystartupapp.domain.employee.exception;

public class EmployeeNotExistsException
   extends RuntimeException {

   private static final long serialVersionUID = -6409333454995020070L;

   /**
    * Constructs an instance of <code>EmployeeNotExists</code> with the specified detail message.
    *
    * @param message the detail message.
    */
   public EmployeeNotExistsException(String message) {
      super(message);
   }

}
