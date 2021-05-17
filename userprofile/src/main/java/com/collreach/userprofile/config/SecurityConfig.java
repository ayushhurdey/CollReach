package com.collreach.userprofile.config;


import com.collreach.userprofile.service.CustomUserDetailsService;
import com.collreach.userprofile.service.UserLoginService;
import com.collreach.userprofile.service.impl.UserLoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors()
                .disable()
                .authorizeRequests()
                .antMatchers("/token","/*.html","/*.html/*","/user/signup","/hello","/response/*").permitAll()
                .antMatchers("/*.css","/*.css/*","/*.js","/*.js/*","/*.map").permitAll()
                .antMatchers("/*.jpg","/*.jpeg","/*.png","/*.PNG").permitAll()
                .antMatchers("/ftp/upload","/ftp/*").permitAll()
                .antMatchers("/user/check-username","/user/check-email","/user/get-image",
                              "/user/check-phone-no","/user/check-linkedin-link","/user/profile/*",
                             "/user/get-user-details/*").permitAll()             // this one to be removed later.
                .antMatchers("/login","/signup","/profile","/profile/*","/profile-update").permitAll()
                .antMatchers("/chat/*").permitAll()
                .antMatchers("/user/get-users-from-username","/user/get-profile-img-by-username*").permitAll()  // to be removed later
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    // added to ignore swagger-ui from spring-security
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/scripts/**",
                "/css/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance() ;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
