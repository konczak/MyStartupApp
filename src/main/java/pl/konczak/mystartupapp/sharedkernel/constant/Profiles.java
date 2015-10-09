package pl.konczak.mystartupapp.sharedkernel.constant;

/**
 * Those constants represents Spring profiles and are used among the project to inform Spring which class should be used
 * or not.
 *
 * @author piotr.konczak
 */
public final class Profiles {

   public static final String PRODUCTION = "production";
   public static final String TEST = "test";
   public static final String DEVELOPMENT = "dev";
   public static final String BASIC_AUTHENTICATION = "auth_basic";

   private Profiles() {
   }
}
