package com.collreach.userprofile.config;


import com.collreach.userprofile.service.CustomUserDetailsService;
import com.collreach.userprofile.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService customerUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken;
        UserDetails userDetails = null;

        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")){
            jwtToken = requestTokenHeader.substring(7);

            try{
                username = this.jwtUtil.getUsernameFromToken(jwtToken);
                System.out.println(username);
                if(username == null)
                    throw new Exception("\n\n---------Token Expired !!!!!----------\n\n");
                userDetails = this.customerUserDetailsService.loadUserByUsername(username);
            }catch(Exception e){
                e.printStackTrace();
            }


            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
               UsernamePasswordAuthenticationToken userPasswordAuthenticationToken =
                       new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

               userPasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

               SecurityContextHolder.getContext().setAuthentication(userPasswordAuthenticationToken);
            }
            else{
                System.out.println("Token is not validated.");
            }
        }

        filterChain.doFilter(request,response);
    }
}
