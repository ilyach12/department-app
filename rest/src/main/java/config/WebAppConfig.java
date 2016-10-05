package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Enable WebMVC for application.
 */
@Configuration
@EnableWebMvc
@ComponentScan("controller")
public class WebAppConfig extends WebMvcConfigurerAdapter {

}
