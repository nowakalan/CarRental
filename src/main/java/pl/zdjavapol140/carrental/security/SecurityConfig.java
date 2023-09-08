package pl.zdjavapol140.carrental.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;



@Configuration
@EnableWebSecurity
public class SecurityConfig {



    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select email, password, true from user where email = ?");
                //"select username, password, true from users where username = ?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select email, role, true from user where email = ?");
                //"select username, authority, true from authorities where username = ?");
        return jdbcUserDetailsManager;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests( config ->
                        config
                                .requestMatchers(HttpMethod.GET, "/CSS/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/js/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/images/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/").permitAll()
                                .requestMatchers(HttpMethod.GET, "/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/home").permitAll()
                               // .requestMatchers(HttpMethod.GET, "/index").permitAll()
                                .requestMatchers(HttpMethod.GET, "/index").hasAnyRole("CUSTOMER", "EMPLOYEE", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/search").hasAnyRole("CUSTOMER", "EMPLOYEE", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/search").hasAnyRole("CUSTOMER", "EMPLOYEE", "ADMIN")

                                .requestMatchers(HttpMethod.GET, "/cars").permitAll()
                                //      .requestMatchers(HttpMethod.GET, "/home2").hasRole("OWNER")
                                .requestMatchers(HttpMethod.GET, "/owner").permitAll()

//
//                        .requestMatchers(HttpMethod.GET, "/api/owners").hasRole("USER")
//                        .requestMatchers(HttpMethod.POST, "/api/owner").hasRole("USER")
//                        .requestMatchers(HttpMethod.POST, "/api/createVet").hasRole("VET")
//                        .requestMatchers(HttpMethod.POST, "/api/createSpeciality").hasRole("ADMIN")
        //).logout( logout -> logout.permitAll());
        ).logout( logout -> logout.logoutUrl("/logout"));


        http.formLogin(Customizer.withDefaults());
/*                http.formLogin(form -> form
                    .loginPage("/login")
                    .loginProcessingUrl("/authenticateUser")
                    .permitAll());
                http.httpBasic(Customizer.withDefaults());*/

        http.csrf(csrf -> csrf.disable());
        return http.build();
    }
}
