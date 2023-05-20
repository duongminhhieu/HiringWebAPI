package com.setqt.Hiring.Security;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.setqt.Hiring.Security.Model.UserDetailServiceImp;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;





@Component
public class JwtFilter extends OncePerRequestFilter {


	@Autowired
	private JwtTokenHelper tokenProvider;

	@Autowired
	private UserDetailServiceImp customUserDetailsService;


	@Autowired
	UserDetailServiceImp userDetailimp;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			// Lấy jwt từ request
			String jwt = getJwtFromRequest(request);

			if (jwt!= null) {
//				System.out.println("-----"+jwt);
			if ( tokenProvider.validateToken(jwt)) {
				// Lấy user từ chuỗi jwt
				String userId = tokenProvider.getUsernameFromToken(jwt);

				// Lấy thông tin người dùng từ id

//				UserDetails userDetails = (UserDetails) customUserDetailsService.findByUsername(userId);
//				UserDetails userDetails = (UserDetails) customUserDetailsService.findUserbyUNAME(userId);
				UserDetails userDetails= userDetailimp.loadUserByUsername(userId);
//				UserDetails userDetails = (UserDetails) customUserDetailsService.findUserbyId(userId);

				if (userDetails != null) {
					// Nếu người dùng hợp lệ, set thông tin cho Seturity Context
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							//**********///
							userDetails, null, userDetails.getAuthorities());
//					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}

			}
		} catch (Exception ex) {
			logger.error("failed on set user authentication", ex);
		}



		filterChain.doFilter(request, response);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = ((jakarta.servlet.http.HttpServletRequest) request).getHeader("Authorization");
		System.out.println(bearerToken);
		// Kiểm tra xem header Authorization có chứa thông tin jwt không
		if (bearerToken!=null && bearerToken.startsWith("Bearer ")) {

			return bearerToken.substring(7);
		}
		return null;
	}


}
