package de.neuefische.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.io.IOException;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Value("${okta.oauth2.issuer}")
	private String issuer;
	@Value("${okta.oauth2.client-id}")
	private String clientId;

	private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler = new CustomAuthenticationSuccessHandler();

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(c -> c
						.anyRequest().authenticated()
				)
				.oauth2Login(c -> c.successHandler(customAuthenticationSuccessHandler))
				.logout(logout -> logout
						.addLogoutHandler(logoutHandler()));
		return http.build();
	}

	private LogoutHandler logoutHandler() {
		return (request, response, authentication) -> {
			try {
				String baseUrl = "http://localhost:3000";
				response.sendRedirect(issuer + "v2/logout?client_id=" + clientId + "&returnTo=" + baseUrl);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		};
	}

}
