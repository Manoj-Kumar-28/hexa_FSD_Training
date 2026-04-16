package com.example.car_rental.config;

import com.example.car_rental.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain bankingSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.OPTIONS,"/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/admin/add")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/buyer/sign-up")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/owner/sign-up")
                        .permitAll()
                        //token api
                        .requestMatchers(HttpMethod.GET,"/api/auth/login")
                        .authenticated()
                        //admin control apis

                        .requestMatchers(HttpMethod.GET,"/api/admin/get-all")
                        .hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/admin/user/{id}")
                        .hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/admin/car/{id}")
                        .hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/admin/buyers")
                        .hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/admin/owners")
                        .hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/admin/cars")
                        .hasAnyAuthority("ADMIN")


                        .requestMatchers(HttpMethod.POST,"/api/owner/add")
                        .hasAnyAuthority("OWNER")
                        .requestMatchers(HttpMethod.GET,"/api/owner/my-cars")
                        .hasAnyAuthority("OWNER")
                        .requestMatchers(HttpMethod.GET,"/api/owner/get/{id}")
                        .hasAnyAuthority("OWNER")
                        .requestMatchers(HttpMethod.PUT,"/api/owner/update/{id}")
                        .hasAnyAuthority("OWNER")
                        .requestMatchers(HttpMethod.PUT,"/api/owner/delete/{id}")
                        .hasAnyAuthority("OWNER")


                        .requestMatchers(HttpMethod.GET,"/api/buyer/get-all")
                        .hasAnyAuthority("BUYER")
                        .requestMatchers(HttpMethod.GET,"/api/buyer/get/model")
                        .hasAnyAuthority("BUYER")
                        .requestMatchers(HttpMethod.GET,"/api/buyer/get/brand")
                        .hasAnyAuthority("BUYER")
                        .requestMatchers(HttpMethod.GET,"/api/buyer/get/available")
                        .hasAnyAuthority("BUYER")

                        .requestMatchers(HttpMethod.POST,"/api/booking/add/{carId}")
                        .authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/booking/get-all")
                        .authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/booking/get/{id}")
                        .authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/booking/get/buyer")
                        .authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/booking/car/{carId}")
                        .authenticated()
                        .requestMatchers(HttpMethod.PUT,"/api/booking/cancel/{bookingId}")
                        .authenticated()

                        .requestMatchers(HttpMethod.POST,"/api/review/add/{userId}/{carId}")
                        .authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/review/get/{carId}")
                        .hasAnyAuthority("ADMIN","USER")





                );
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.httpBasic(Customizer.withDefaults());  //Spring understand that I am using this technique
        return http.build();
    }



}
