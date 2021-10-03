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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

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
//        String localUser = extractCookieFor(request.getCookies(), "username");
//        System.out.println("User from cookie: " + localUser) ;

        String username = null;
        String jwtToken;
        UserDetails userDetails = null;

        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")){
            jwtToken = requestTokenHeader.substring(7);

            try{
                username = this.jwtUtil.getUsernameFromToken(jwtToken);
                System.out.println(username);
                if(username == null)         // TODO: A bug: username here is to checked if this username exists in the current token as well as database.
                    throw new Exception("\n\n---------Token Expired !!!!!----------\n\n");
                userDetails = this.customerUserDetailsService.loadUserByUsername(username);
            }catch(Exception e){
                e.printStackTrace();
            }


            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                //assert userDetails != null;
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


    // causing NullPointerException, TODO: Debugging to be done
    private String extractCookieFor(Cookie[] cookies, String key){
        String localUser = null;
        if(cookies.length > 0) {
            Optional<Cookie> cookieOptional = Arrays.stream(cookies)
                    .filter(c -> c.getName().equalsIgnoreCase(key))
                    .findFirst();

            if (cookieOptional.isPresent())
                localUser = cookieOptional.get().getValue();
        }
        return localUser;
    }
}
