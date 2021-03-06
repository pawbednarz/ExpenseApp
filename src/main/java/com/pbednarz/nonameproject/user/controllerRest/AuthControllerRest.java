package com.pbednarz.nonameproject.user.controllerRest;

import com.pbednarz.nonameproject.config.CustomUserDetailsService;
import com.pbednarz.nonameproject.config.jwt.JwtUtil;
import com.pbednarz.nonameproject.model.jwt.JwtRequest;
import com.pbednarz.nonameproject.model.jwt.JwtResponse;
import com.pbednarz.nonameproject.user.model.User;
import com.pbednarz.nonameproject.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("${com.pbednarz.apiPath}")
public class AuthControllerRest {

    private JwtUtil jwtUtil;
    private CustomUserDetailsService userDetailsService;
    private AuthenticationManager authenticationManager;
    private UserService userService;

    @Autowired
    public AuthControllerRest(JwtUtil jwtUtil,
                              CustomUserDetailsService userDetailsService,
                              AuthenticationManager authenticationManager,
                              UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> getToken(@RequestBody JwtRequest request) throws Exception{
        authenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid User user, BindingResult bindingResult) {
        String error = userService.checkForErrors(bindingResult);
        if (error.isEmpty()) {
            userService.addWithDefaultRole(user);
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
        }
    }

    private void authenticate(String username, String password) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS");
        }
    }
}
