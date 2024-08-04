package Test.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Keep CSRF disabled for now
            .authorizeHttpRequests(authz ->
                authz
                    .requestMatchers("/", "/css/**", "/js/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/notes")
                    .authenticated()
                    .requestMatchers("/notes/**")
                    .authenticated()
                    .anyRequest()
                    .authenticated()
            )
            .oauth2Login(oauth2 ->
                oauth2.loginPage("/").defaultSuccessUrl("/notes", true)
            )
            .logout(logout ->
                logout
                    .logoutSuccessHandler(oidcLogoutSuccessHandler())
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
            );
        return http.build();
    }

    @Bean
    public LogoutSuccessHandler oidcLogoutSuccessHandler() {
        SimpleUrlLogoutSuccessHandler successHandler =
            new SimpleUrlLogoutSuccessHandler();
        successHandler.setDefaultTargetUrl(
            "https://dev-esfjsl3sz0115t5s.us.auth0.com/v2/logout?client_id=2x04udSdM6QZ8BP9kIdduTtQaQ62mGcC&returnTo=https:https://javanoteapp-9795.onrender.com"
        );
        return successHandler;
    }
}
