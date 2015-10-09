package pl.konczak.mystartupapp.sharedkernel.enums;

import java.util.Arrays;
import java.util.Locale;

public enum ValidLanguages {
   POLISH(Locale.forLanguageTag("pl-PL")), ENGLISH(Locale.forLanguageTag("en-GB"));

   private final Locale language;

   private ValidLanguages(Locale language) {
      this.language = language;
   }

   public Locale getLanguage() {
      return language;
   }
   
   public static boolean isValid(Locale language) {
      return Arrays.asList(values()).stream()
            .map(ValidLanguages::getLanguage)
            .anyMatch(l -> l.equals(language));
   }

}
