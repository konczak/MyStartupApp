package pl.konczak.mystartupapp.config.web;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import pl.konczak.mystartupapp.web.converter.LocalDateTimeDeserializer;
import pl.konczak.mystartupapp.web.converter.LocalDateTimeSerializer;
import pl.konczak.mystartupapp.web.converter.LocaleDeserializer;
import pl.konczak.mystartupapp.web.converter.LocaleSerializer;

/**
 * This is Spring MVC configuration. It is registering static resources, i18n support, file upload support and other.
 *
 * @author piotr.konczak
 */
@Configuration
@EnableWebMvc
public class WebMvcConfigurator
   extends WebMvcConfigurerAdapter {

   @Value("${spring.messages.cacheSeconds}")
   private int cacheSeconds;

   @Value("${server.contextPath}")
   private String contextPath;

   private static final int maxUploadSize = 5000000;
   private static final int cookieMaxAge = 31536000;

   /**
    * Register static resources to increase production performance
    *
    * @param registry
    */
   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
      if (!registry.hasMappingForPattern("/css/**")) {
         registry.addResourceHandler("/css/**")
            .addResourceLocations("classpath:/static/css/");
      }

      if (!registry.hasMappingForPattern("/js/**")) {
         registry.addResourceHandler("/js/**")
            .addResourceLocations("classpath:/static/js/");
      }

      if (!registry.hasMappingForPattern("/fonts/**")) {
         registry.addResourceHandler("/fonts/**")
            .addResourceLocations("classpath:/static/fonts/");
      }

      if (!registry.hasMappingForPattern("/img/**")) {
         registry.addResourceHandler("/img/**")
            .addResourceLocations("classpath:/static/img/");
      }

      if (!registry.hasMappingForPattern("/webjars/**")) {
         registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
      }
      if (!registry.hasMappingForPattern("/i18n/**")) {
         registry.addResourceHandler("/i18n/**")
            .addResourceLocations("classpath:/static/i18n/");
      }
   }

   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(localeChangeInterceptor());
   }

   @Bean
   public LocaleChangeInterceptor localeChangeInterceptor() {
      LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
      localeChangeInterceptor.setParamName("language");
      return localeChangeInterceptor;
   }

   @Bean(name = "localeResolver")
   public LocaleResolver getLocaleResolver() {
      CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
      cookieLocaleResolver.setCookiePath(contextPath + "/");
      cookieLocaleResolver.setCookieName("language");
      cookieLocaleResolver.setDefaultLocale(new Locale("pl", "PL"));
      cookieLocaleResolver.setCookieMaxAge(cookieMaxAge);
      return cookieLocaleResolver;
   }

   @Bean
   public ResourceBundleMessageSource messageSource() {
      ResourceBundleMessageSource source = new ResourceBundleMessageSource();
      source.setBasenames("i18n/messages", "i18n/application");
      source.setUseCodeAsDefaultMessage(true);
      source.setDefaultEncoding("UTF-8");
      source.setFallbackToSystemLocale(false);
      source.setCacheSeconds(cacheSeconds);
      return source;
   }

   /**
    * Supports FileUploads.
    */
   @Bean
   public MultipartResolver multipartResolver() {
      CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
      multipartResolver.setMaxUploadSize(maxUploadSize);
      return multipartResolver;
   }

   @Override
   public void addViewControllers(ViewControllerRegistry registry) {
      registry.addViewController("/access").setViewName("access");
      registry.addViewController("/jasmine").setViewName("jasmine/SpecRunner");
      registry.addViewController("/403error").setViewName("error/403");
      registry.addViewController("/404error").setViewName("error/404");
      registry.addViewController("/500error").setViewName("error/500");
      registry.addViewController("/error/403").setViewName("error/403");
      registry.addViewController("/error/404").setViewName("error/404");
      registry.addViewController("/error/500").setViewName("error/500");
   }

   @Override
   public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
      Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();

      builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer());
      builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer());
      builder.serializerByType(Locale.class, new LocaleSerializer());
      builder.deserializerByType(Locale.class, new LocaleDeserializer());

      converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
      converters.add(new ByteArrayHttpMessageConverter());
   }
}
