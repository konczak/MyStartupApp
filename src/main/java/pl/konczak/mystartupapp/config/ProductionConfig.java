package pl.konczak.mystartupapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import pl.konczak.mystartupapp.sharedkernel.constant.Profiles;

/**
 * This configuration file is responsible for loading external configuration. It is especially useful in overriding
 * default properties by production one. Also it is skipped in TEST profile so it could be run on Continuous Integration
 * without external file.
 *
 * @author piotr.konczak
 */
@Configuration
@PropertySource("classpath:/defaultConfig.properties")
@Profile({Profiles.PRODUCTION, Profiles.DEVELOPMENT})
public class ProductionConfig {

}
