package pl.zdjavapol140.carrental.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

                "select email, password, true from users where email = ?");

        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(

                "select email, role, true from users where email = ?");


        return jdbcUserDetailsManager;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
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
                                .requestMatchers(HttpMethod.GET, "/index").permitAll()
                                .requestMatchers(HttpMethod.GET, "/search").permitAll()
                                .requestMatchers(HttpMethod.POST, "/search").permitAll()
                                .requestMatchers(HttpMethod.POST, "/create-customer").permitAll()
                                .requestMatchers(HttpMethod.GET, "/create-customer").permitAll()
                                .requestMatchers(HttpMethod.POST, "/custom-login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/custom-login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/reservations").hasAnyRole("CUSTOMER", "EMPLOYEE", "ADMIN")

                                .requestMatchers(HttpMethod.GET, "/cars").permitAll()
                                .requestMatchers(HttpMethod.GET, "/cars-list").permitAll()
                                .requestMatchers(HttpMethod.GET, "/cars-founded").permitAll()
                                .requestMatchers(HttpMethod.POST, "/deleteCar").hasAnyRole("EMPLOYEE", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/updateCar").hasAnyRole("EMPLOYEE", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/updateCar").hasAnyRole("EMPLOYEE", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/createCar").hasAnyRole("EMPLOYEE", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/create-car").hasAnyRole("EMPLOYEE", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/find-car-by-id").hasAnyRole("EMPLOYEE", "ADMIN")
                                //      .requestMatchers(HttpMethod.GET, "/home2").hasRole("OWNER")
                                .requestMatchers(HttpMethod.GET, "/owner").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/booking/criteria").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/booking/cars").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/booking/selected_car").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/booking/pre_reservation").permitAll()
                                .requestMatchers(HttpMethod.GET, "/preselect-car").hasAnyRole("CUSTOMER", "EMPLOYEE", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/submit-reservation").hasAnyRole("CUSTOMER", "EMPLOYEE", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/submit-reservation").hasAnyRole("CUSTOMER", "EMPLOYEE", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/preselect-car").hasAnyRole("CUSTOMER", "EMPLOYEE", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/confirm-page").hasAnyRole("CUSTOMER", "EMPLOYEE", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/booking/reservation").permitAll()
                                .requestMatchers(HttpMethod.POST, "/deleteReservation").hasAnyRole("CUSTOMER", "EMPLOYEE", "ADMIN")



        ).logout( logout -> logout.logoutUrl("/logout"));


        http.formLogin(Customizer.withDefaults());
//                http.formLogin(form -> form
//                    .loginPage("/login")
//                    .loginProcessingUrl("/authenticateUser")
//                    .permitAll());
                http.httpBasic(Customizer.withDefaults());


        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
