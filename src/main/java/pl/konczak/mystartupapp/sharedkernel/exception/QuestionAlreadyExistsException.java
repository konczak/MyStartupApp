package pl.konczak.mystartupapp.sharedkernel.exception;

public class QuestionAlreadyExistsException
   extends RuntimeException {
   private static final long serialVersionUID = 4682208089023717584L;

   public QuestionAlreadyExistsException() {
      super("Question already exists");
   }

}
