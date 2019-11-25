package railroad.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Web socket configuration.
 */
@Configuration
public class WebSocketConfigurator {

    @Bean
    public CustomSpringConfigurator customSpringConfigurator() {
        return new CustomSpringConfigurator(); // This is just to get context
    }
}