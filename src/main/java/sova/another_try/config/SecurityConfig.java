package sova.another_try.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("Admin")
                .password("$2y$12$I2dgAW9Y1h9bq8S0/5WyvOrfsfyEBqbBvv1BELCMCayXE7DHEDrUS")
                .roles("ADMIN")
                .and()
                .withUser("Student")
                .password("$2y$12$5AmL8P688c0XKnuI2S5KV.0ERVQWrRn4fNtN584tAnL7w3QsCLMTG")
                .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "static/css", "static/js").permitAll()
                .antMatchers("student/**", "/student/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("admin/**", "/admin/**").hasRole("ADMIN")
                .and().formLogin();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
