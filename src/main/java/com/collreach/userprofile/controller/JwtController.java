package com.collreach.userprofile.controller;


import com.collreach.userprofile.model.request.UserLoginRequest;
import com.collreach.userprofile.model.response.JwtResponse;
import com.collreach.userprofile.service.CustomUserDetailsService;
import com.collreach.userprofile.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/token")
    public ResponseEntity<?> generateToken(@RequestBody UserLoginRequest userLoginRequest) throws Exception {
        System.out.println(userLoginRequest);
        try {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginRequest.getUserName(),
                            userLoginRequest.getPassword()));

        }catch(UsernameNotFoundException e){
            e.printStackTrace();
            throw new Exception("Bad credentials");
        }catch(BadCredentialsException e){
            e.printStackTrace();
            throw new Exception("Bad Credentials");
        }

        UserDetails userDetails =  this.customUserDetailsService.loadUserByUsername(userLoginRequest.getUserName());
        String token = this.jwtUtil.generateToken(userDetails);
        System.out.println("JWT " + token);

        return ResponseEntity.ok(new JwtResponse(token));

    }
}
