package Test.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * EnvConfig
 */
@Configuration
@PropertySources(
    {
        @PropertySource("classpath:application.properties"),
        @PropertySource(value = "file:.env", ignoreResourceNotFound = true),
    }
)
public class EnvConfig {}
