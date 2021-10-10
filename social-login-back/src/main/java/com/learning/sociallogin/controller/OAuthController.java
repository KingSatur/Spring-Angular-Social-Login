package com.learning.sociallogin.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.learning.sociallogin.dto.AuthRequestDto;
import com.learning.sociallogin.dto.AuthStateDto;
import com.learning.sociallogin.entity.Role;
import com.learning.sociallogin.entity.UserSocialLogin;
import com.learning.sociallogin.enums.RoleName;
import com.learning.sociallogin.security.jwt.JwtProvider;
import com.learning.sociallogin.service.RoleService;
import com.learning.sociallogin.service.UsersocialService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class OAuthController {

	@Value("${google.clientId}")
	private String googleId;

	@Value("${secretPsw}")
	private String secretPassword;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private UsersocialService userSocialService;

	@Autowired
	private RoleService roleService;

	@PostMapping("/google")
	public ResponseEntity<AuthStateDto> google(
			@RequestBody AuthRequestDto tokenDto) throws IOException {
		final NetHttpTransport netHttpTransport = new NetHttpTransport();
		final GsonFactory gsonFactory = GsonFactory.getDefaultInstance();
		GoogleIdTokenVerifier.Builder verifier = new GoogleIdTokenVerifier.Builder(
				netHttpTransport, gsonFactory)
						.setAudience(Collections.singleton(this.googleId));
		final GoogleIdToken googleIdToken = GoogleIdToken
				.parse(verifier.getJsonFactory(), tokenDto.getValue());
		final GoogleIdToken.Payload payload = googleIdToken.getPayload();
		System.out.println(payload.toPrettyString());
		UserSocialLogin user = new UserSocialLogin();
		if (this.userSocialService.exists(payload.getEmail())) {
			user = this.userSocialService.getByEmail(payload.getEmail()).get();
		} else {
			user = saveUser(payload.getEmail());
		}
		AuthStateDto dto = this.login(user);
		dto.setId(user.getId());
		dto.setFirst_name((String) payload.get("name"));
		dto.setPhoto_url((String) payload.get("picture"));
		return new ResponseEntity(dto, HttpStatus.OK);
	}

	private AuthStateDto login(UserSocialLogin user) {
		Authentication authentication = this.authManager.authenticate(
				new UsernamePasswordAuthenticationToken(user.getEmail(),
						this.secretPassword));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = this.jwtProvider.generateToken(authentication);
		AuthStateDto authDto = new AuthStateDto();
		authDto.setToken(jwt);
		return authDto;
	}

	private UserSocialLogin saveUser(String email) {
		UserSocialLogin user = new UserSocialLogin(email,
				passwordEncoder.encode(secretPassword));
		Role role = this.roleService.getByRoleName(RoleName.ROLE_USER).get();
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRoles(roles);
		return this.userSocialService.save(user);
	}

}
