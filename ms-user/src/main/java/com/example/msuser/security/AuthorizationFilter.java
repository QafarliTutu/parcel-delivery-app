package com.example.msuser.security;

import com.example.msuser.exception.InvalidTokenException;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final MUserDetailsService mUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        var authorization = Optional.ofNullable(
                request.getHeader("Authorization"));

        authorization.ifPresent(auth -> {
            try {
                var isTokenValid = jwtService.validateToken(auth);
                if (isTokenValid) {
                    var jwtPayload = jwtService.getPayloadFromJWT(auth);
                    var userDetails = mUserDetailsService
                            .loadUserById(Long.valueOf(jwtPayload.getUserId()));

                    var authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request));

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);
                }
            } catch (Exception ex) {
                log.error("Could not set user authentication in security context: ", ex);
                throw new InvalidTokenException();
            }
        });
        filterChain.doFilter(request, response);
    }
}
