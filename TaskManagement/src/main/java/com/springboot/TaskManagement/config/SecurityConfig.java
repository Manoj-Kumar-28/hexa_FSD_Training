package com.springboot.TaskManagement.config;



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
                        .requestMatchers(HttpMethod.POST,"/api/manager/sign-up")
                        .permitAll()
                        //token api
                        .requestMatchers(HttpMethod.GET,"/api/auth/login")
                        .authenticated()


                        .requestMatchers(HttpMethod.POST,"/api/task/add")
                        .hasAnyAuthority("MANAGER")
                        .requestMatchers(HttpMethod.GET,"/api/task/get-all")
                        .hasAnyAuthority("MANAGER")
                        .requestMatchers(HttpMethod.GET,"/api/task/get/{id}")
                        .hasAnyAuthority("MANAGER")
                        .requestMatchers(HttpMethod.PUT,"/api/task/update/{id}")
                        .hasAnyAuthority("MANAGER")
                        .requestMatchers(HttpMethod.DELETE,"/api/task/delete/{id}")
                        .hasAnyAuthority("MANAGER")



                );
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.httpBasic(Customizer.withDefaults());  //Spring understand that I am using this technique
        return http.build();
    }



}

