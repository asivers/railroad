package railroad.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Properties;

@Configuration
@EnableWebMvc
@Import(MessagingConfiguration.class)
@ComponentScan(basePackages = "railroad")
public class WebConfig implements WebMvcConfigurer {

    /**
     * Resource folder.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/res/**").addResourceLocations("/res/");
    }

    /**
     * Path and suffix for jsp.
     */
    @Bean
    ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    /**
     * Resource not found exception.
     */
    @Bean
    HandlerExceptionResolver customExceptionResolver () {
        SimpleMappingExceptionResolver s = new SimpleMappingExceptionResolver();
        Properties p = new Properties();
        p.setProperty(NoHandlerFoundException.class.getName(), "/404");
        s.setExceptionMappings(p);
        s.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return s;
    }

}
