package com.example.msuser.security;

import static com.example.msuser.util.response.ResponseCode.INVALID_CREDENTIALS;

import com.example.msuser.exception.InvalidModelException;
import com.example.msuser.model.request.LoginReq;
import com.example.msuser.repository.entity.Role;
import com.example.msuser.util.response.ServiceResponse;
import com.example.msuser.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AuthenticationFilter(UserService userService,
                                AuthenticationManager authenticationManager,
                                JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
        super.setAuthenticationManager(authenticationManager);
        super.setFilterProcessesUrl("/v1/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            var login = objectMapper.readValue(request.getInputStream(), LoginReq.class);

            if (StringUtils.isAnyBlank(login.getUsername(), login.getPassword())) {
                generateResponse(
                        response,
                        HttpStatus.UNAUTHORIZED,
                        ServiceResponse.error(INVALID_CREDENTIALS, INVALID_CREDENTIALS.getDescription()));
            }
            return getAuthenticationManager()
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            login.getUsername(),
                            login.getPassword()));
        } catch (IOException exception) {
            logger.error(exception);
            throw new InvalidModelException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication auth) throws IOException {
        var user = userService.findUserEntityByUsername(((UserDetails) auth.getPrincipal()).getUsername());
        var roles = user
                .getRoles()
                .stream()
                .map(Role::getType)
                .collect(Collectors.toSet());
        var tokenResp = jwtService.createToken(user.getId(), roles);

        generateResponse(
                response,
                HttpStatus.OK,
                tokenResp);
        logger.info(String.format("JWT token generated for %s", user.getUsername()));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) {
        generateResponse(
                response,
                HttpStatus.UNAUTHORIZED,
                ServiceResponse.error(INVALID_CREDENTIALS, INVALID_CREDENTIALS.getDescription()));

        logger.error("Invalid credentials");
    }

    private void generateResponse(HttpServletResponse res, HttpStatus status, Object body) {
        res.setStatus(status.value());
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try {
            objectMapper.writeValue(res.getOutputStream(), body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
