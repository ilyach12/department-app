package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configuration class. Enable MVC. Configure resource handler
 * and view resolver for application.
 */
@Configuration
@EnableWebMvc
@ComponentScan("controller")
public class WebAppConfig extends WebMvcConfigurerAdapter {
}