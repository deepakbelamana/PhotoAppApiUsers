package com.dpkprojects.app.photoapp.api.users.security;

import com.dpkprojects.app.photoapp.api.users.service.UserService;
import com.dpkprojects.app.photoapp.api.users.shared.UserDto;
import com.dpkprojects.app.photoapp.api.users.ui.model.LoginRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserService userService;
    private Environment environment;
    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                UserService userService,
                                Environment environment) {
        super(authenticationManager);
        this.environment=environment;
        this.userService=userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            LoginRequestModel creds = new ObjectMapper()
                    .readValue(req.getInputStream(), LoginRequestModel.class);
            //this will try to authenticate with the user credentials
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * this method will be automatically invoked by springboot after successfull authentication of
     * user details
     * @param req
     * @param res
     * @param chain
     * @param auth the object returned from the <tt>attemptAuthentication</tt>
     * method.
     * @throws IOException
     * @throws ServletException
     */
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
        //the auth gives the user details
       String userName = ((User)auth.getPrincipal()).getUsername();
        UserDto userDto = userService.getUserDetailsByEmail(userName);
    }
}
