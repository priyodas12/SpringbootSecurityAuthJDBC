package io.springlab.SpringbootSecurityAuthJDBC.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    //query for fetching user data from mysql
    private static String userDetailsQuery="SELECT USER_NAME,USER_PASSWORD,IS_ENABLED FROM userdata WHERE USER_NAME=?";
    private static String userRolesQuery="SELECT USER_NAME,USER_ROLE FROM userdata WHERE USER_NAME=?";


    @Autowired
    private DataSource dataSourceFromProperty;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                //will fetch all data source configuration from application.properties
                .dataSource(dataSourceFromProperty)
                //fetch user data with query
                .usersByUsernameQuery(userDetailsQuery)
                //fetch role details with query
                .authoritiesByUsernameQuery(userRolesQuery)
                //use singleton password encoder
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //URL and Access type
        http.authorizeRequests()
                .antMatchers("/home").permitAll()
                .antMatchers("/welcome").authenticated()
                .antMatchers("/admin").hasAuthority("ADMIN")
                .antMatchers("/emp").hasAuthority("EMPLOYEE")
                .antMatchers("/clerk").hasAuthority("CLERK")
                //LoginForm Details
                .and()
                .formLogin()
                .defaultSuccessUrl("/welcome",true)
                //LogoutForm Details
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                //Exception Details
                .and()
                .exceptionHandling()
                .accessDeniedPage("/no-access");
    }
}
