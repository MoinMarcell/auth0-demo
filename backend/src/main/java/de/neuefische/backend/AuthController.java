package de.neuefische.backend;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@GetMapping("/me")
	public CustomOAuth2User home(@AuthenticationPrincipal OidcUser principal) {
		if (principal != null) {
			return CustomOAuth2User.builder()
					.username(principal.getNickName())
					.avatarUrl(principal.getPicture())
					.build();
		}
		throw new RuntimeException("No principal found");
	}

}
