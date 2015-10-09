package pl.konczak.mystartupapp.sharedkernel.exception;

public class EntityInStateNewException
   extends RuntimeException {

   private static final long serialVersionUID = 8615255199251864095L;

   public EntityInStateNewException() {
      super("Entity is not persisted yet and cannot be used");
   }

}
