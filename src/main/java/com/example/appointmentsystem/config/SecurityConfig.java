package com.example.appointmentsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.appointmentsystem.repositories.UserRepository;
import com.example.appointmentsystem.services.CustomUserDetailsServices;

@Configuration
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService(UserRepository userRepository) {
    return new CustomUserDetailsServices(userRepository);
  
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(requests -> requests// Lambda DSL Domain Specific Language/authorizeRequst is deprcated
            // Public static resources and landing pages
            .requestMatchers("/", "/index", "/index.html", "/style.css", "/style2.css").permitAll()

            // Public API endpoints
            .requestMatchers("/users/**").permitAll()
            .requestMatchers("/services/**").permitAll()
            .requestMatchers("/user/appointment/**").permitAll()
            .requestMatchers("/availability/**").permitAll()
            .requestMatchers("/previous/**").permitAll()

           
           /* .requestMatchers("/availability/list", "/", "/availableServices", "/availableServices.html").permitAll()
           

            .requestMatchers("/booking", "/booking.html", "/templates/booking.html").permitAll() */

            // Admin-only access
            .requestMatchers("/admin", "/createService").hasRole("ADMIN")

            // User-only access
            .requestMatchers("/booking","/user").hasRole("USER")
           // .requestMatchers("/**").permitAll()

            .anyRequest().permitAll()
            )

        .formLogin(form -> form.disable())
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/users/login?logout=true")
            .permitAll());

    return http.build();

  }

}
