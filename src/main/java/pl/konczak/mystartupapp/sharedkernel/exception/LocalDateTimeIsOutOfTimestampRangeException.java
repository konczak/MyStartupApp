package pl.konczak.mystartupapp.sharedkernel.exception;

/**
 *
 * @author Mateusz.Glabicki
 */
public class LocalDateTimeIsOutOfTimestampRangeException
   extends RuntimeException {

   private static final long serialVersionUID = 8288277651777709068L;

   public LocalDateTimeIsOutOfTimestampRangeException() {
      super();
   }

   public LocalDateTimeIsOutOfTimestampRangeException(String message) {
      super(message);
   }

}
