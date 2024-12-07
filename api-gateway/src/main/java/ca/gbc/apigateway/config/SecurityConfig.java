package ca.gbc.apigateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final String[] noAuthResourceUris = {
            "/swagger-ui",
            "/swagger-ui/*",
            "/swagger-ui/**",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-resource/**",
            "/api-docs/**",
            "/aggregate/**"
    };



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        log.info("Initializing Security Filter Chain...");

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection (not recommended for production)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(noAuthResourceUris).permitAll() // Swagger
                        .requestMatchers("/api/approval/**")
                        .hasAuthority("Staff") // Require Staff role
                        .anyRequest().authenticated() // require authentication
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .build();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // JWT token path for roles
        grantedAuthoritiesConverter.setAuthoritiesClaimName("realm_access.roles");
        // No prefix
        grantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Map<String, Object> claims = jwt.getClaims();
            log.debug("JWT Claims: {}", claims);

            // Extract roles and convert to GrantedAuthority
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            if (claims.containsKey("realm_access")) {
                Map<String, Object> realmAccess = (Map<String, Object>) claims.get("realm_access");
                List<String> roles = (List<String>) realmAccess.get("roles");
                if (roles != null) {
                    roles.forEach(role -> {
                        log.debug("Extracted Role: {}", role);
                        authorities.add(new SimpleGrantedAuthority(role));
                    });
                }
            }

            log.info("Extracted Authorities: {}", authorities);
            return authorities;
        });

        return authenticationConverter;
    }


}
