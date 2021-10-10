package com.learning.sociallogin.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

	private final static Logger logger = LoggerFactory
			.getLogger(JwtProvider.class);

	private final JwtProvider jwtProvider;
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String token = this.getToken(request);
			String email = this.jwtProvider.getEmailFromToken(token);
			UserDetails userDetails = this.userDetailsService
					.loadUserByUsername(email);
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
					email, null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);
		} catch (Exception e) {
			logger.error("Failed on method doFilter with message: "
					+ e.getMessage());
		}
		filterChain.doFilter(request, response);

	}

	private String getToken(HttpServletRequest request) {
		String authReq = request.getHeader("Authorization");
		System.out.println(authReq);
		if (authReq != null && authReq.startsWith("Bearer ")) {
			return authReq.replace("Bearer ", "");
		}
		return null;
	}

}
