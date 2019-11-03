package railroad.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = "railroad.service")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        AccessDeniedHandlerImpl accessDeniedHandler = new AccessDeniedHandlerImpl();
        accessDeniedHandler.setErrorPage("/accessDenied.htm");
        return accessDeniedHandler;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/adminmain", "/addstation", "/finishaddstation", "/addstationfortrain", "/finishaddstationfortrain", "/addtrain", "finishaddtrain", "/alltrains", "/choosetrain", "/passengersbytrain")
                .access("hasRole('ROLE_ADMIN')")
                .and()

                .authorizeRequests()
                .antMatchers("/usermain", "/mytickets", "/buyticket", "/finishbuyticket", "/findtrain", "/trainsbysearch", "/choosestation", "/trainsbystation",  "/stationsbytrain", "/notrainsfound", "/nostationsfound")
                .access("hasRole('ROLE_USER')")
                .and()

                .authorizeRequests()
                .antMatchers("/", "/signin", "/signup", "/res/**")
                .permitAll()
                .and()

                .formLogin()
                .loginPage("/signin")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .loginProcessingUrl("/dologin")
                .successForwardUrl("/postlogin")
                .failureUrl("/wrongloginpassword")
                .and()

                .logout()
                .logoutUrl("/dologout")
                .logoutSuccessUrl("/logout")
                .permitAll()
                .and()

                .csrf().disable();
    }

}